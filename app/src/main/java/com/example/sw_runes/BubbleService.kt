package com.example.sw_runes

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
import com.example.sw_runes.fragments.BubbleFragment
import com.example.sw_runes.models.RecordingViewModel
import java.util.*




class BubbleService : Service() {
    private var windowManager: WindowManager? = null
    private var floatyView: View? = null




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

        params = WindowManager.LayoutParams(
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

        params.gravity = Gravity.LEFT or Gravity.TOP
        params.x = 0
        params.y = 0

        var mOrientationChangeCallback = OrientationChangeCallback(this)
        if (mOrientationChangeCallback?.canDetectOrientation() == true) {
            mOrientationChangeCallback!!.enable()
        }

        var bubbleFragment = BubbleFragment()

        val viewModelStore = ViewModelStore()
        val lifecycleOwner = MyLifecycleOwner()
        lifecycleOwner.performRestore(null)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        floatyView = bubbleFragment.onCreateView(inflater,FrameLayout(this), bundleOf())
        bubbleFragment.giveWindowParams(windowManager!!,params)
        ViewTreeLifecycleOwner.set(floatyView!!, lifecycleOwner)
        ViewTreeViewModelStoreOwner.set(floatyView!!) { viewModelStore }
        floatyView!!.setViewTreeSavedStateRegistryOwner(lifecycleOwner)
        windowManager!!.addView(floatyView, params)
    }

    //Close service
    override fun onDestroy() {
        super.onDestroy()
        if (floatyView != null) {
            windowManager!!.removeView(floatyView)
            floatyView = null
        }
    }


    internal class MyLifecycleOwner : SavedStateRegistryOwner {
        private var mLifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
        private var mSavedStateRegistryController: SavedStateRegistryController = SavedStateRegistryController.create(this)

        /**
         * @return True if the Lifecycle has been initialized.
         */
        val isInitialized: Boolean
            get() = true

        override val savedStateRegistry: SavedStateRegistry
            get() = mSavedStateRegistryController.savedStateRegistry

        override fun getLifecycle(): Lifecycle {
            return mLifecycleRegistry
        }

        fun setCurrentState(state: Lifecycle.State) {
            mLifecycleRegistry.currentState = state
        }

        fun handleLifecycleEvent(event: Lifecycle.Event) {
            mLifecycleRegistry.handleLifecycleEvent(event)
        }



        fun performRestore(savedState: Bundle?) {
            mSavedStateRegistryController.performRestore(savedState)
        }

        fun performSave(outBundle: Bundle) {
            mSavedStateRegistryController.performSave(outBundle)
        }
    }

}
