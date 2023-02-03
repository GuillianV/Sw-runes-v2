package com.example.sw_runes.services.extension

import android.annotation.SuppressLint
import android.content.Context.MEDIA_PROJECTION_SERVICE
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.ImageReader.OnImageAvailableListener
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.*
import android.view.Display
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.work.WorkManager
import com.example.sw_runes.enums.TapStatus
import com.example.sw_runes.services.RuneAnalyzerService
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.Buffer
import java.util.*


class ScreenCapture(_runeAnalyzerService: RuneAnalyzerService) {

    private var runeAnalyzerService : RuneAnalyzerService

    init {
        runeAnalyzerService = _runeAnalyzerService

    }


    private var displayWidth = Resources.getSystem().displayMetrics.widthPixels
    private var displayHeight =  Resources.getSystem().displayMetrics.heightPixels
    private var bitmapSaver : BitmapSaver = BitmapSaver()
    private var baseOrientation : Int = 1;

    private var mMediaProjection: MediaProjection? = null
    private var mImageReader: ImageReader? = null
    private var mHandler: Handler? = null
    private var mDisplay: Display? = null
    private var mVirtualDisplay: VirtualDisplay? = null
    private var mDensity = 0
    private val workManager = WorkManager.getInstance(runeAnalyzerService)





    fun createMediaProjection(resultCode: Int, data: Intent){

        val mpManager = runeAnalyzerService.getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        if (mMediaProjection == null) {

            mMediaProjection = mpManager.getMediaProjection(resultCode,data)
            if (mMediaProjection != null) {
                // display metrics
                mDensity = Resources.getSystem().displayMetrics.densityDpi
                val windowManager = runeAnalyzerService.getSystemService(WINDOW_SERVICE) as WindowManager
                mDisplay = windowManager.defaultDisplay

                // create virtual display depending on device width / height
                createVirtualDisplay()

                // register media projection stop callback
                mMediaProjection!!.registerCallback(MediaProjectionStopCallback(), mHandler)

                listenBubbleTapStatus()

            }
        }


    }

    fun destroyMediaProjection(){

        mMediaProjection?.stop()

    }



    private  fun changeOrientation(orientation: Int){

       var baseOrientation = orientation

        try {
            // clean up
            mVirtualDisplay?.release()
            mImageReader?.setOnImageAvailableListener(null, null)

            // re-create virtual display depending on device width / height
            createVirtualDisplay()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @SuppressLint("WrongConstant")
    private fun createVirtualDisplay() {
        // start capture reader
        displayWidth = Resources.getSystem().displayMetrics.widthPixels
        displayHeight =  Resources.getSystem().displayMetrics.heightPixels

        mImageReader = ImageReader.newInstance(displayWidth, displayHeight, PixelFormat.RGBA_8888, 2)
        mVirtualDisplay?.release()
        mVirtualDisplay = mMediaProjection!!.createVirtualDisplay(
            "screencap", displayWidth, displayHeight,
            mDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY or DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC, mImageReader!!.getSurface(), null, mHandler
        )
        mImageReader!!.setOnImageAvailableListener(bitmapSaver.ImageAvailableListener(), mHandler)
    }

    private fun listenBubbleTapStatus(){

        runeAnalyzerService.mutableBubbleStatus.observe(runeAnalyzerService, Observer {

            if (it == TapStatus.Tap){
                var actualOrientation = runeAnalyzerService.getResources().getConfiguration().orientation
                if (baseOrientation != actualOrientation){
                    baseOrientation = actualOrientation
                    changeOrientation(actualOrientation)
                }


                bitmapSaver.pickedHandler = {
                    runeAnalyzerService.setRune(it)
                }

                bitmapSaver.takePick()
            }
        })


    }




    //inner class
    inner class BitmapSaver {

        private   lateinit var buffer : Buffer
        private  var pixelStride : Int = 0
        private  var rowPadding: Int = 0
        private  var bitmapByteArray : ByteArray? = null

        private  var folderDir : String = "/DCIM/SWrunesStorage/"
        private var preBitmapText : String = "sw_"
        private var mStoreDir: String? = null

        private val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        private val random = Random()


        private var takepick = false;

        var pickedHandler : (ByteArray) -> Unit = {}


        inner class ImageAvailableListener : OnImageAvailableListener {


            var planes = arrayOf<Image.Plane>()

            override fun onImageAvailable(reader: ImageReader) {
                try {


                    mImageReader!!.acquireLatestImage().use { image ->
                        if (image != null) {
                            planes = image.planes
                            buffer = planes[0].buffer
                            pixelStride = planes[0].pixelStride
                            rowPadding = planes[0].rowStride - pixelStride * displayWidth
                        }
                    }


                    if (!takepick)
                        return

                    captureBitmap()
                    saveBitmap()
                    takepick = false
                    getBitmapByteArray()?.let(pickedHandler)

                } catch (e: Exception) {
                    takepick = false
                    e.printStackTrace()
                }

            }
        }

        private fun captureBitmap(){
            if (rowPadding != 0 && pixelStride != 0 && displayWidth != 0 && displayHeight != 0){
                try {


                    var bitmap: Bitmap = Bitmap.createBitmap(
                        displayWidth + rowPadding / pixelStride,
                        displayHeight,
                        Bitmap.Config.ARGB_8888
                    )
                    bitmap.copyPixelsFromBuffer(buffer)

                    bitmapByteArray = bitmap.toByteArray()
                    bitmap.recycle()
                } catch (ioe: IOException) {
                    ioe.printStackTrace()
                }
            }

        }

        private fun saveBitmap(){

            if (bitmapByteArray == null)
                return


            val externalFilesDir = runeAnalyzerService.getExternalFilesDir(null)
            if (externalFilesDir != null) {
                mStoreDir =
                    Environment.getExternalStorageDirectory().absolutePath.toString() + folderDir
                val storeDirectory: File = File(mStoreDir)
                if (!storeDirectory.exists()) {
                    val success = storeDirectory.mkdirs()
                    if (!success) {
                        throw Exception("failed to create file storage directory.")
                    }
                }

            }



            val bitmap = bitmapByteArray.let { BitmapFactory.decodeByteArray(bitmapByteArray, 0, it!!.size) }
            val size : Int =  dirSize( File(mStoreDir))
            var fileOutputStream: FileOutputStream =   FileOutputStream(mStoreDir +"/"+ preBitmapText + size +"_"+randomId()+ ".png")
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream)
            fileOutputStream.close()
            bitmap?.recycle()

        }

        fun takePick(){
            if (!takepick)
                takepick = true;

        }

        fun getBitmap():Bitmap?{
           return bitmapByteArray.let { BitmapFactory.decodeByteArray(bitmapByteArray, 0, it!!.size) }

        }

        fun getBitmapByteArray(): ByteArray? {
            return bitmapByteArray;
        }


        private fun Bitmap.toByteArray():ByteArray{
            ByteArrayOutputStream().apply {
                compress(Bitmap.CompressFormat.JPEG,50,this)
                return toByteArray()
            }
        }

        private fun randomId() :String{

            return (1..5).map { characters[random.nextInt(characters.length)] }.joinToString("")

        }

        private fun dirSize(dir: File): Int {
            if (dir.exists()) {
                var result: Int = 0
                val fileList = dir.listFiles()
                if (fileList != null) {
                    result = fileList.size
                }
                return result // return the file size
            }
            return 0
        }

    }

    inner class MediaProjectionStopCallback : MediaProjection.Callback() {
        override fun onStop() {

            mVirtualDisplay?.release()
            mImageReader?.setOnImageAvailableListener(null, null)
            mMediaProjection!!.unregisterCallback(this@MediaProjectionStopCallback)
            mMediaProjection = null

            super.onStop()

        }
    }



}