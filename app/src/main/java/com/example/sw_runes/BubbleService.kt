package com.example.sw_runes

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.*
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.sw_runes.ui.theme.SwRunesTheme
import java.util.*


enum class TapStatus {
   Tap,Drag,Nothing
}

class BubbleService : Service()  {
    private var windowManager: WindowManager? = null
    private var floatyView: View? = null

    private var mOrientationChangeCallback: OrientationChangeCallback? = null
    private lateinit var params :WindowManager.LayoutParams;



    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        addOverlayView()
        println("intent Received")


    }

    inner class OrientationChangeCallback internal constructor(context: Context?) :
        OrientationEventListener(context) {

        var baseOrientation =Configuration.ORIENTATION_PORTRAIT

        override fun onOrientationChanged(orientation: Int) {
                try {
                    var phoneOrientation =getResources().getConfiguration().orientation
                    if(phoneOrientation== Configuration.ORIENTATION_LANDSCAPE && baseOrientation != Configuration.ORIENTATION_LANDSCAPE ){
                        baseOrientation = Configuration.ORIENTATION_LANDSCAPE
                        params.x = 0
                        params.y = 0

                        windowManager!!.updateViewLayout(floatyView, params)
                    }else if (phoneOrientation== Configuration.ORIENTATION_PORTRAIT && baseOrientation != Configuration.ORIENTATION_PORTRAIT){
                        baseOrientation = Configuration.ORIENTATION_PORTRAIT
                        params.x = 0
                        params.y = 0

                        windowManager!!.updateViewLayout(floatyView, params)
                    }




                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


    @SuppressLint("WrongConstant")
    private fun addOverlayView() {
        val layoutParamsType: Int
        layoutParamsType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutParamsType,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSPARENT
        )

        params.gravity = Gravity.LEFT or Gravity.TOP
        params.x = 0
        params.y = 0

        mOrientationChangeCallback = OrientationChangeCallback(this)
        if (mOrientationChangeCallback!!.canDetectOrientation()) {
            mOrientationChangeCallback!!.enable()
        }

        val interceptorLayout: FrameLayout = object : FrameLayout(this) {


            private var millisecondBetweenTwoTap = 1000
            private var minDragValue = 100


            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f
            private var dragVale = 0
            private  var startTime : Long = System.currentTimeMillis();


            override fun onTouchEvent(event: MotionEvent?): Boolean {

                if (event?.action == MotionEvent.ACTION_OUTSIDE)
                    return super.onTouchEvent(event)

                else{

                    when (event!!.action) {
                        MotionEvent.ACTION_DOWN -> {
                            initialX = params.x
                            initialY = params.y
                            initialTouchX = event!!.rawX
                            initialTouchY = event!!.rawY

                        }
                        MotionEvent.ACTION_UP -> {

                            if (dragVale <=30){
                                dragVale = 0
                                var difference: Long = System.currentTimeMillis() - startTime
                                if (difference >= 500){
                                    interact(TapStatus.Tap)
                                    startTime = System.currentTimeMillis();
                                    /*bubbleImage.setImageResource(bubbleImageTapedIcon);
                                    bubbleText.setText(bubbleTextTaped)*/

                                    val handler = Handler()
                                    handler.postDelayed({
                                     /*   bubbleImage.setImageResource(bubbleImageBaseIcon);
                                        bubbleText.setText(bubbleTextBase)*/

                                    }, millisecondBetweenTwoTap.toLong())
                                }



                            }

                            else{
                                dragVale = 0
                                interact(TapStatus.Drag)
                            }


                        }
                        MotionEvent.ACTION_MOVE -> {
                            params.x = initialX + (event!!.rawX - initialTouchX).toInt()
                            params.y = initialY + (event!!.rawY - initialTouchY).toInt()
                            dragVale = Math.abs((event!!.rawX - initialTouchX).toInt()) +Math.abs((event!!.rawY - initialTouchY).toInt())

                            windowManager!!.updateViewLayout(floatyView, params)

                        }

                    }


                    return true

                }
            }

        }
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (inflater != null) {
            floatyView = ComposeView(context = applicationContext).apply {  }
            windowManager!!.addView(floatyView, params)
        } else {
            Log.e(
                "SAW-example",
                "Layout Inflater Service is null; can't inflate and display R.layout.floating_view"
            )
        }
    }

    //Close service
    override fun onDestroy() {
        super.onDestroy()
        if (floatyView != null) {
            windowManager!!.removeView(floatyView)
            floatyView = null
        }
    }

    @SuppressLint("WrongConstant")
    fun interact(status: TapStatus){
        when (status) {
            TapStatus.Drag -> {

            }
            TapStatus.Tap -> {

              /*  val RTReturn = Intent(ScreenCaptureService.SCREENSHOT)
                LocalBroadcastManager.getInstance(this).sendBroadcast(RTReturn)
*/
            }
            else -> {

            }

        }

    }





}
