package com.example.sw_runes.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.sw_runes.databinding.BubbleBinding
import com.example.sw_runes.enums.TapStatus
import com.example.sw_runes.services.RuneAnalyzerService
import com.example.sw_runes.ui.theme.black025

class BubbleFragment(_runeAnalyzerService: RuneAnalyzerService) : Fragment() {



    private var _binding: BubbleBinding? = null
    private val binding get() = _binding!!

    private val runeAnalyzerService : RuneAnalyzerService

    init {
        runeAnalyzerService = _runeAnalyzerService;
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      super.onCreateView(inflater, container, savedInstanceState)

        _binding = BubbleBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.container.setOnTouchListener { view, motionEvent -> onTouchListener(view,motionEvent) }
        listenBubbleTapStatus()

        return view
    }

    fun getRootView () : View?{
        return binding.root.rootView
    }


    private var millisecondBetweenTwoTap = 1000
    private var minDragValue = 100
    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var dragVale = 0
    private  var startTime : Long = System.currentTimeMillis();




    private fun onTouchListener(view : View , motionEvent: MotionEvent): Boolean {


        if (windowManager == null || windowManagerSettings == null)
            false

        return if (motionEvent?.action != MotionEvent.ACTION_OUTSIDE)
        {



            when (motionEvent!!.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = windowManagerSettings!!.x
                    initialY = windowManagerSettings!!.y
                    initialTouchX = motionEvent!!.rawX
                    initialTouchY = motionEvent!!.rawY




                }
                MotionEvent.ACTION_UP -> {

                    if (dragVale <=30){
                        dragVale = 0
                        var difference: Long = System.currentTimeMillis() - startTime
                        if (difference >= 500){
                            interact(TapStatus.Tap)
                            startTime = System.currentTimeMillis();

                        }



                    }

                    else{
                        dragVale = 0
                        interact(TapStatus.DragEnd)
                    }

                }
                MotionEvent.ACTION_MOVE -> {
                    windowManagerSettings?.x = initialX + (motionEvent!!.rawX - initialTouchX).toInt()
                    windowManagerSettings?.y = initialY + (motionEvent!!.rawY - initialTouchY).toInt()
                    dragVale = Math.abs((motionEvent!!.rawX - initialTouchX).toInt()) +Math.abs((motionEvent!!.rawY - initialTouchY).toInt())

                    windowManager!!.updateViewLayout(binding.root, windowManagerSettings)
                    if (dragVale >= 30){
                        interact(TapStatus.Dragging)
                    }
                }

            }

            true

        }
        else false


    }




    var windowManager: WindowManager? = null
    var windowManagerSettings : WindowManager.LayoutParams? = null;

    fun setWindowParams (_windowManager : WindowManager,_windowManagerSettings: WindowManager.LayoutParams ){
        windowManager = _windowManager
        windowManagerSettings = _windowManagerSettings

    }


    private fun interact(status: String){
        when (status) {
            TapStatus.Dragging -> {

                binding.bubbleImage.rotation += 1
                sendBubbleTapStatus(TapStatus.Dragging)
            }
            TapStatus.DragEnd -> {
                sendBubbleTapStatus(TapStatus.DragEnd)
            }
            TapStatus.Tap -> {

                binding.bubbleImage.setColorFilter(black025.toArgb())
                sendBubbleTapStatus(TapStatus.Tap)

            }
            else -> {
                sendBubbleTapStatus(TapStatus.Ready)
            }

        }

    }

    private fun sendBubbleTapStatus(tapStatus: String){
        runeAnalyzerService.mutableBubbleStatus.value = tapStatus
    }
    private fun listenBubbleTapStatus(){

        runeAnalyzerService.mutableBubbleStatus.observe(runeAnalyzerService, Observer {

            if (it == TapStatus.Ready){

                binding.bubbleImage.setColorFilter(Color.Transparent.toArgb())

            }
        })


    }



}



