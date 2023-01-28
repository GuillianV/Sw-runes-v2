package com.example.sw_runes.services.extension

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Context.WINDOW_SERVICE
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.view.*
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.sw_runes.enums.TapStatus
import com.example.sw_runes.fragments.BubbleFragment
import com.example.sw_runes.services.RuneAnalyzerService
import com.example.sw_runes.workers.BubbleWorker


class Bubble(_runeAnalyzerService: RuneAnalyzerService) {

    private var runeAnalyzerService : RuneAnalyzerService

    private var windowManager: WindowManager? = null
    private lateinit var windowManagerParams :WindowManager.LayoutParams;

    private var bubbleFragment: BubbleFragment? = null



    init {

        runeAnalyzerService = _runeAnalyzerService
        windowManager = runeAnalyzerService.getSystemService(WINDOW_SERVICE) as WindowManager

    }



    fun createBubble(){
        createLayout()
    }


    fun destroyBubble(){
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
        val inflater = runeAnalyzerService.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        bubbleFragment!!.onCreateView(inflater,FrameLayout(runeAnalyzerService), bundleOf())
        bubbleFragment!!.setWindowParams(windowManager!!,windowManagerParams)
        windowManager!!.addView(bubbleFragment?.getRootView(), windowManagerParams)


        bubbleFragment!!.onInteraction =  { tap -> sendDataBubble(tap) }

        var mOrientationChangeCallback = OrientationChangeCallback(runeAnalyzerService)
        if (mOrientationChangeCallback?.canDetectOrientation() == true) {
            mOrientationChangeCallback!!.enable()
        }

    }



    inner class OrientationChangeCallback internal constructor(context: Context?) :
        OrientationEventListener(context) {

        var baseOrientation =Configuration.ORIENTATION_PORTRAIT

        override fun onOrientationChanged(orientation: Int) {
            try {
                var phoneOrientation =runeAnalyzerService.getResources().getConfiguration().orientation
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


    private val workManager = WorkManager.getInstance(runeAnalyzerService)

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
