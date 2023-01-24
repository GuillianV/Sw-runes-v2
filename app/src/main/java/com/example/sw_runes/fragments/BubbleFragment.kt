package com.example.sw_runes.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.sw_runes.R
import com.example.sw_runes.databinding.BubbleBinding
import com.example.sw_runes.enums.TapStatus
import androidx.work.Worker
import androidx.work.WorkerParameters

class BubbleFragment : Fragment() {



    private var _binding: BubbleBinding? = null
    private val binding get() = _binding!!


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
        binding.bubbleImage.setImageResource(R.drawable.rune_emplacement_one)
        return view
    }



    private var millisecondBetweenTwoTap = 1000
    private var minDragValue = 100


    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var dragVale = 0
    private  var startTime : Long = System.currentTimeMillis();



    fun onTouchListener(view : View , motionEvent: MotionEvent): Boolean {


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

                }

            }

            true

        }
        else false


    }



    var windowManager: WindowManager? = null
    var windowManagerSettings : WindowManager.LayoutParams? = null;

    fun giveWindowParams (_windowManager : WindowManager,_windowManagerSettings: WindowManager.LayoutParams ){
        windowManager = _windowManager
        windowManagerSettings = _windowManagerSettings

    }

    @SuppressLint("WrongConstant")
    private fun interact(status: TapStatus){
        when (status) {
            TapStatus.DragEnd -> {

                binding.bubbleImage.setImageResource(R.drawable.ic_launcher_foreground)

            }
            TapStatus.Tap -> {

                binding.bubbleImage.setImageResource(R.drawable.rune_emplacement_one)

                //  val RTReturn = Intent(ScreenCaptureService.SCREENSHOT)
                //  LocalBroadcastManager.getInstance(this).sendBroadcast(RTReturn)

               // println(viewModel.startRecoding.value)

            }
            else -> {

            }

        }

    }


}



