package com.example.sw_runes.services

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
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
import android.util.Log
import android.view.Display
import android.view.OrientationEventListener
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.util.Pair
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.sw_runes.enums.TapStatus
import com.example.sw_runes.models.RecordingViewModel
import com.example.sw_runes.utils.Notifications
import com.example.sw_runes.workers.BubbleWorker
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.Buffer


class ScreenCaptureService : LifecycleService() {

    private var displayWidth = Resources.getSystem().displayMetrics.widthPixels
    private var displayHeight =  Resources.getSystem().displayMetrics.heightPixels
    private var bitmapSaver : BitmapSaver = BitmapSaver()
    private var mOrientationChangeCallback: OrientationChangeCallback? = null


    private var mMediaProjection: MediaProjection? = null
    private var mImageReader: ImageReader? = null
    private var mHandler: Handler? = null
    private var mDisplay: Display? = null
    private var mVirtualDisplay: VirtualDisplay? = null
    private var mDensity = 0


    companion object {

        private val RESULT_CODE = "RESULT_CODE"
        private val DATA = "DATA"
        private val ACTION = "ACTION"
        private val START = "START"
        private val STOP = "STOP"
        private val SCREENCAP_NAME = "screencap"

        fun getStartIntent(context: Context?, resultCode: Int, data: Intent?): Intent? {
            val intent = Intent(context, ScreenCaptureService::class.java)
            intent.putExtra(ACTION, START)
            intent.putExtra(RESULT_CODE, resultCode)
            intent.putExtra(DATA, data)
            return intent
        }


        fun getStopIntent(context: Context?): Intent? {
            val intent = Intent(context, ScreenCaptureService::class.java)
            intent.putExtra(ACTION, STOP)
            return intent
        }

        private fun isStartCommand(intent: Intent): Boolean {
            return (intent.hasExtra(RESULT_CODE) && intent.hasExtra(DATA)
                    && intent.hasExtra(ACTION) && intent.getStringExtra(ACTION) == START)
        }

        private fun isStopCommand(intent: Intent): Boolean {
            return intent.hasExtra(ACTION) && intent.getStringExtra(ACTION) == STOP
        }





    }





    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }


    override fun onCreate() {
        super.onCreate()

        // start capture handling thread
        object : Thread() {
            override fun run() {
                Looper.prepare()
                mHandler = Handler(Looper.getMainLooper())
                Looper.loop()
            }
        }.start()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (intent == null)
            stopSelf()

        if (isStartCommand(intent!!)) {
            // create notification
            val notification: Pair<Int, Notification> = Notifications.getNotification(this)
            startForeground(notification.first!!, notification.second)
            // start projection
            val resultCode = intent.getIntExtra(RESULT_CODE, Activity.RESULT_CANCELED)
            val data = intent.getParcelableExtra<Intent>(DATA)
            if (data != null) {
                startProjection(resultCode, data)
            }

        } else if (isStopCommand(intent)) {
            stopProjection()
            stopSelf()
        } else {
            stopSelf()
        }
        return START_NOT_STICKY
    }

    private val workManager = WorkManager.getInstance(this)


    private fun startProjection(resultCode: Int, data: Intent) {
        val mpManager = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        if (mMediaProjection == null) {

            mMediaProjection = mpManager.getMediaProjection(resultCode,data)
            if (mMediaProjection != null) {
                // display metrics
                mDensity = Resources.getSystem().displayMetrics.densityDpi
                val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
                mDisplay = windowManager.defaultDisplay

                // create virtual display depending on device width / height
                createVirtualDisplay()

                // register orientation change callback
                mOrientationChangeCallback = OrientationChangeCallback(this)
                if (mOrientationChangeCallback!!.canDetectOrientation()) {
                    mOrientationChangeCallback!!.enable()
                }

                // register media projection stop callback
                mMediaProjection!!.registerCallback(MediaProjectionStopCallback(), mHandler)

                listenBubbleWorker()

            }
        }
    }

    override fun onDestroy() {
        stopProjection()
        stopSelf()
        super.onDestroy()
    }


    private fun stopProjection() {
        if (mHandler != null) {
            mHandler!!.post {
                mMediaProjection?.stop()
            }
        }
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
        mVirtualDisplay = mMediaProjection!!.createVirtualDisplay(
            SCREENCAP_NAME, displayWidth, displayHeight,
            mDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY or DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC, mImageReader!!.getSurface(), null, mHandler
        )
        mImageReader!!.setOnImageAvailableListener(bitmapSaver.ImageAvailableListener(), mHandler)
    }

    private fun listenBubbleWorker(){
        val request = OneTimeWorkRequestBuilder<BubbleWorker>()
            .addTag(BubbleWorker.tag)
            .build()

        workManager.enqueue(request)
        workManager.getWorkInfosByTagLiveData(BubbleWorker.tag)
            .observe(this, Observer { workInfos ->
                if (workInfos != null) {
                    for (workInfo in workInfos) {
                        when (workInfo.state) {
                            WorkInfo.State.SUCCEEDED -> {

                                var tapStatus = workInfo.outputData.getString(BubbleWorker.key)
                                if (tapStatus == TapStatus.Tap){
                                    var actualOrientation = getResources().getConfiguration().orientation
                                    if (baseOrientation != actualOrientation){
                                        baseOrientation = actualOrientation
                                        changeOrientation(actualOrientation)
                                    }


                                    bitmapSaver.takePick()
                                }

                            }
                            else -> {
                                print("enque")
                            }
                        }
                    }
                    workManager.pruneWork()
                }})

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


        private var takepick = false;


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

                } catch (e: Exception) {
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


            val externalFilesDir = getExternalFilesDir(null)
            if (externalFilesDir != null) {
                mStoreDir =
                    Environment.getExternalStorageDirectory().absolutePath.toString() + folderDir
                val storeDirectory: File = File(mStoreDir)
                if (!storeDirectory.exists()) {
                    val success = storeDirectory.mkdirs()
                    if (!success) {
                        stopSelf()
                        throw Exception("failed to create file storage directory.")
                    }
                }

            }


            val bitmap = bitmapByteArray.let { BitmapFactory.decodeByteArray(bitmapByteArray, 0, it!!.size) }
            val size : Int =  dirSize( File(mStoreDir))
            var fileOutputStream: FileOutputStream =   FileOutputStream(mStoreDir +"/"+ preBitmapText + size + ".png")
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
    var baseOrientation = Configuration.ORIENTATION_PORTRAIT

    inner class OrientationChangeCallback internal constructor(context: Context?) :
        OrientationEventListener(context) {


        override fun onOrientationChanged(orientation: Int) {

            var phoneOrientation =getResources().getConfiguration().orientation
            if(phoneOrientation== Configuration.ORIENTATION_LANDSCAPE && baseOrientation != Configuration.ORIENTATION_LANDSCAPE ){
                baseOrientation = Configuration.ORIENTATION_LANDSCAPE
                changeOrientation(Configuration.ORIENTATION_LANDSCAPE)

            }else if (phoneOrientation== Configuration.ORIENTATION_PORTRAIT && baseOrientation != Configuration.ORIENTATION_PORTRAIT){
                baseOrientation = Configuration.ORIENTATION_PORTRAIT
                changeOrientation(Configuration.ORIENTATION_PORTRAIT)

            }


        }
    }

    inner class MediaProjectionStopCallback : MediaProjection.Callback() {
        override fun onStop() {
            mHandler!!.post {
                mVirtualDisplay?.release()
                mImageReader?.setOnImageAvailableListener(null, null)
                mOrientationChangeCallback?.disable()
                mMediaProjection!!.unregisterCallback(this@MediaProjectionStopCallback)
                mMediaProjection = null
            }
            super.onStop()

        }
    }



}