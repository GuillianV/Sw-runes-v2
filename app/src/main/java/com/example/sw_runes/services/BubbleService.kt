package com.example.sw_runes.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.os.*
import android.view.*
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.core.os.bundleOf
import androidx.core.os.persistableBundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.sw_runes.enums.TapStatus
import com.example.sw_runes.fragments.BubbleFragment
import com.example.sw_runes.models.RecordingViewModel
import com.example.sw_runes.workers.BubbleWorker
import java.util.*




class BubbleService : Service() {



    private var windowManager: WindowManager? = null
    private lateinit var windowManagerParams :WindowManager.LayoutParams;

    private var bubbleFragment: BubbleFragment? = null




    override fun onBind(intent: Intent): IBinder? {

        return null
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        createLayout()
    }

    //Close service
    override fun onDestroy() {
        super.onDestroy()
        if (bubbleFragment?.getRootView() != null) {
            windowManager!!.removeView(bubbleFragment!!.getRootView())
            bubbleFragment = null
        }
    }



    private fun createLayout() {

        windowManagerParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or

                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH ,

            PixelFormat.TRANSPARENT
        )

        windowManagerParams.gravity = Gravity.LEFT or Gravity.TOP
        windowManagerParams.x = 0
        windowManagerParams.y = 0



        bubbleFragment = BubbleFragment()
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        bubbleFragment!!.onCreateView(inflater,FrameLayout(this), bundleOf())
        bubbleFragment!!.setWindowParams(windowManager!!,windowManagerParams)
        windowManager!!.addView(bubbleFragment?.getRootView(), windowManagerParams)


        bubbleFragment!!.onInteraction =  { tap -> sendDataBubble(tap) }

        var mOrientationChangeCallback = OrientationChangeCallback(this)
        if (mOrientationChangeCallback?.canDetectOrientation() == true) {
            mOrientationChangeCallback!!.enable()
        }

    }



    inner class OrientationChangeCallback internal constructor(context: Context?) :
        OrientationEventListener(context) {

        var baseOrientation =Configuration.ORIENTATION_PORTRAIT

        override fun onOrientationChanged(orientation: Int) {
            try {
                var phoneOrientation =getResources().getConfiguration().orientation
                if(phoneOrientation== Configuration.ORIENTATION_LANDSCAPE && baseOrientation != Configuration.ORIENTATION_LANDSCAPE ){
                    baseOrientation = Configuration.ORIENTATION_LANDSCAPE
                    windowManagerParams.x = 0
                    windowManagerParams.y = 0

                    windowManager!!.updateViewLayout(bubbleFragment?.getRootView(), windowManagerParams)
                }else if (phoneOrientation== Configuration.ORIENTATION_PORTRAIT && baseOrientation != Configuration.ORIENTATION_PORTRAIT){
                    baseOrientation = Configuration.ORIENTATION_PORTRAIT
                    windowManagerParams.x = 0
                    windowManagerParams.y = 0

                    windowManager!!.updateViewLayout(bubbleFragment?.getRootView(), windowManagerParams)
                }




            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private val workManager = WorkManager.getInstance(this)

    fun sendDataBubble(tapStatus: String): Boolean {

        if (tapStatus == TapStatus.Dragging)
            return false


        val data = Data.Builder()

            .putString(BubbleWorker.key,tapStatus)
            .build()

        val request = OneTimeWorkRequestBuilder<BubbleWorker>()
            .addTag(BubbleWorker.tag)
            .setInputData(data)
            .build()

        workManager.enqueue(request)
        return  true
    }

}
