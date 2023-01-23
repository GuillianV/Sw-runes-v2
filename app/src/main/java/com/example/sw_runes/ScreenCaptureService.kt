package com.example.sw_runes
/*
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import androidx.annotation.RequiresApi
import androidx.core.util.Pair
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.sw_runes.utils.Notifications
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.Buffer
import java.text.SimpleDateFormat
import java.util.*


class ScreenCaptureService : Service() {

    var bManager: LocalBroadcastManager? = null

    var planes = arrayOf<Image.Plane>()
    lateinit var buffer : Buffer
    var pixelStride : Int = 0
    var rowStride : Int = 0
    var rowPadding: Int = 0

    var lastBitmap : Bitmap? = null
    var bitmap: Bitmap? = null

    var folderDir : String = "/DCIM/SWrunesStorage/"
    var preBitmapText : String = "swrunes_"

    companion object {

        val REQUEST_CODE_RECORDING = 10101
        private val TAG = "ScreenCaptureService"
        private val RESULT_CODE = "RESULT_CODE"
        private val DATA = "DATA"
        private val ACTION = "ACTION"
        private val START = "START"
        private val STOP = "STOP"
        private val SCREENCAP_NAME = "screencap"

        private var mMediaProjection: MediaProjection? = null
        private var mStoreDir: String? = null
        private var mImageReader: ImageReader? = null
        private var mHandler: Handler? = null
        private var mDisplay: Display? = null
        private var mVirtualDisplay: VirtualDisplay? = null
        private var mDensity = 0
        private var mWidth = 0
        private var mHeight = 0
        private var mRotation = 0
        val SCREENSHOT = "SCREENSHOT"

        private var mOrientationChangeCallback: OrientationChangeCallback? = null


        fun getStartIntent(context: Context?, resultCode: Int, data: Intent?): Intent? {
            val intent = Intent(context, ScreenCaptureService::class.java)
            intent.putExtra(ACTION, START)
            intent.putExtra(RESULT_CODE, resultCode)
            intent.putExtra(DATA, data)
            return intent
        }


        fun getStopIntent(context: Context?): Intent? {
            val intent = Intent(context, ScreenCaptureService::class.java)
            intent.putExtra(ScreenCaptureService.ACTION, ScreenCaptureService.STOP)
            return intent
        }

        private fun isStartCommand(intent: Intent): Boolean {
            return (intent.hasExtra(RESULT_CODE) && intent.hasExtra(DATA)
                    && intent.hasExtra(ACTION) && intent.getStringExtra(ACTION) == START)
        }

        private fun isStopCommand(intent: Intent): Boolean {
            return intent.hasExtra(ACTION) && intent.getStringExtra(ACTION) == STOP
        }

        private fun getVirtualDisplayFlags(): Int {
            return DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY or DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC
        }



    }





    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
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

    override fun onCreate() {
        super.onCreate()

        bManager = LocalBroadcastManager.getInstance(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction(MainActivity.SCREENSHOT)
        bManager!!.registerReceiver(bReceiver, intentFilter)

        // create store dir
        val externalFilesDir = getExternalFilesDir(null)
        if (externalFilesDir != null) {
            mStoreDir =
                Environment.getExternalStorageDirectory().absolutePath.toString() + folderDir
            val storeDirectory: File = File(mStoreDir)
            if (!storeDirectory.exists()) {
                val success = storeDirectory.mkdirs()
                if (!success) {
                    Log.e(ScreenCaptureService.TAG, "failed to create file storage directory.")
                    stopSelf()
                }
            }

        } else {
            Log.e(
                ScreenCaptureService.TAG,
                "failed to create file storage directory, getExternalFilesDir is null."
            )
            stopSelf()
        }

        // start capture handling thread
        object : Thread() {
            override fun run() {
                Looper.prepare()
                mHandler = Handler()
                Looper.loop()
            }
        }.start()
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (isStartCommand(intent)) {
            // create notification
            val notification: Pair<Int, Notification> = Notifications.getNotification(this)
            startForeground(notification.first!!, notification.second)
            // start projection
            val resultCode =
                intent.getIntExtra(RESULT_CODE, Activity.RESULT_CANCELED)
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

    inner class ImageAvailableListener : OnImageAvailableListener {
        override fun onImageAvailable(reader: ImageReader) {

                try {
                    mImageReader!!.acquireLatestImage().use { image ->
                        if (image != null) {
                             planes = image.planes
                             buffer = planes[0].buffer
                             pixelStride = planes[0].pixelStride
                             rowStride = planes[0].rowStride
                             rowPadding = rowStride - pixelStride * mWidth
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun saveBitmap(){

        if (rowPadding != 0 && pixelStride != 0 && mWidth != 0 && mHeight != 0){
            try {
                bitmap = Bitmap.createBitmap(
                    mWidth + rowPadding / pixelStride,
                    mHeight,
                    Bitmap.Config.ARGB_8888
                )
                bitmap!!.copyPixelsFromBuffer(buffer)

                /*
                var fos: FileOutputStream? = null
                // write bitmap to a file
                val sdf = SimpleDateFormat("mm-ss")
                val currentDate = sdf.format(Date())

                var size : Int =  dirSize( File(mStoreDir))

                fos =
                    FileOutputStream(mStoreDir +"/"+ preBitmapText + size+"_"+currentDate + ".png")


                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                */

                val byteArray: ByteArray = bitmap!!.toByteArray()
                bitmap!!.recycle()
            /*    var myData = Intent(this, PopupService::class.java)
                myData.putExtra("byteArray",byteArray)

                stopService(Intent(this, PopupService::class.java))
                startService(myData)*/


                lastBitmap = bitmap
                /*if (fos != null) {
                    fos!!.close()
                    if (bitmap != null) {
                        bitmap!!.recycle()
                    }
                }*/
            } catch (ioe: IOException) {
                ioe.printStackTrace()
            }
        }






    }



    inner class OrientationChangeCallback internal constructor(context: Context?) :
        OrientationEventListener(context) {

        var baseOrientation = Configuration.ORIENTATION_PORTRAIT

        override fun onOrientationChanged(orientation: Int) {

            var phoneOrientation =getResources().getConfiguration().orientation
            if(phoneOrientation== Configuration.ORIENTATION_LANDSCAPE && baseOrientation != Configuration.ORIENTATION_LANDSCAPE ){
                baseOrientation = Configuration.ORIENTATION_LANDSCAPE

                try {
                    // clean up
                    mVirtualDisplay?.release()
                    mImageReader?.setOnImageAvailableListener(null, null)

                    // re-create virtual display depending on device width / height
                    createVirtualDisplay()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }else if (phoneOrientation== Configuration.ORIENTATION_PORTRAIT && baseOrientation != Configuration.ORIENTATION_PORTRAIT){
                baseOrientation = Configuration.ORIENTATION_PORTRAIT

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


        }
    }

    inner class MediaProjectionStopCallback : MediaProjection.Callback() {
        override fun onStop() {
            Log.e(TAG, "stopping projection.")
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
            }
        }
    }

    fun Bitmap.toByteArray():ByteArray{
        ByteArrayOutputStream().apply {
            compress(Bitmap.CompressFormat.JPEG,50,this)
            return toByteArray()
        }
    }

    private fun stopProjection() {
        if (mHandler != null) {
            mHandler!!.post {
                mMediaProjection?.stop()
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun createVirtualDisplay() {
        // get width and height
        mWidth = Resources.getSystem().displayMetrics.widthPixels
        mHeight = Resources.getSystem().displayMetrics.heightPixels

        // start capture reader
        mImageReader = ImageReader.newInstance(mWidth, mHeight, PixelFormat.RGBA_8888, 2)
        mVirtualDisplay = mMediaProjection!!.createVirtualDisplay(
            SCREENCAP_NAME, mWidth, mHeight,
            mDensity, getVirtualDisplayFlags(), mImageReader!!.getSurface(), null, mHandler
        )
        mImageReader!!.setOnImageAvailableListener(ImageAvailableListener(), mHandler)
    }


    private val bReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onReceive(context: Context, intent: Intent) {

            if (intent.action == ScreenCaptureService.SCREENSHOT) {
                    saveBitmap()
            }


        }
    }



}*/