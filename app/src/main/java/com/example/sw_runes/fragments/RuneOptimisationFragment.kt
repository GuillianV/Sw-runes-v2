package com.example.sw_runes.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.fragment.app.Fragment
import com.example.sw_runes.R
import com.example.sw_runes.databinding.RuneOptimisationBinding
import com.example.sw_runes.enums.TapStatus
import com.example.sw_runes.services.RuneAnalyzerService
import com.example.sw_runes.sw.rune.Rune
import com.example.sw_runes.ui.theme.black025

class RuneOptimisationFragment(_runeAnalyzerService: RuneAnalyzerService, _rune : Rune) : Fragment() {


    private var _binding: RuneOptimisationBinding? = null
    private val binding get() = _binding!!

    private val rune : Rune
    private val runeAnalyzerService : RuneAnalyzerService

    init {
        rune = _rune
        runeAnalyzerService = _runeAnalyzerService;
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = RuneOptimisationBinding.inflate(inflater,container,false)
        val view = binding.root
        bindView()

        return view
    }

    fun bindView(){

        binding.ivRuneEmplacementRarity.setBackgroundResource(rune.runeRarity.BACKGROUND_DRAWABLE)
        binding.ivRuneEmplacementRarity.foreground = runeAnalyzerService.getDrawable(rune.runeEmplacement.RUNE_DRAWABLE)

        binding.tvRuneLevel.setText("+${rune.runeLevel} Rune")
        binding.imRuneStars.setImageResource(rune.runeStar.IMAGE)

        binding.runePrimaryDescription.setText(rune.runePrimaryStat.PRIMARY_STAT_TEXT)
        binding.runePrimaryValue.setText("+${rune.runePrimaryStat.PRIMARY.ACTUAL_STAT}${rune.runePrimaryStat.SECONDARY_STAT_TEXT}")


        if (rune.runeSubStats.count() > 0){

            var substat = rune.runeSubStats[0]
            binding.tvRuneSubPropriete1.setText("${substat.SUB_STAT_TEXT}")
            binding.tvRuneSubValue1.setText("+${substat.SUB.ACTUAL_STAT}${substat.SECONDARY_STAT_TEXT}")
            binding.tvRuneSubOptimisation1.setText("${substat.SUB.getPoidsCalculated()}")

        }

        if (rune.runeSubStats.count() > 1){

            var substat = rune.runeSubStats[1]
            binding.tvRuneSubPropriete2.setText("${substat.SUB_STAT_TEXT}")
            binding.tvRuneSubValue2.setText("+${substat.SUB.ACTUAL_STAT}${substat.SECONDARY_STAT_TEXT}")
            binding.tvRuneSubOptimisation2.setText("${substat.SUB.getPoidsCalculated()}")

        }

        if (rune.runeSubStats.count() > 2){

            var substat = rune.runeSubStats[2]
            binding.tvRuneSubPropriete3.setText("${substat.SUB_STAT_TEXT}")
            binding.tvRuneSubValue3.setText("+${substat.SUB.ACTUAL_STAT}${substat.SECONDARY_STAT_TEXT}")
            binding.tvRuneSubOptimisation3.setText("${substat.SUB.getPoidsCalculated()}")

        }

        if (rune.runeSubStats.count() > 3){

            var substat = rune.runeSubStats[3]
            binding.tvRuneSubPropriete4.setText("${substat.SUB_STAT_TEXT}")
            binding.tvRuneSubValue4.setText("+${substat.SUB.ACTUAL_STAT}${substat.SECONDARY_STAT_TEXT}")
            binding.tvRuneSubOptimisation4.setText("${substat.SUB.getPoidsCalculated()}")

        }

        if (rune.runeSubStats.count() > 4){

            var substat = rune.runeSubStats[4]
            binding.tvRuneSubPropriete5.setText("${substat.SUB_STAT_TEXT}")
            binding.tvRuneSubValue5.setText("+${substat.SUB.ACTUAL_STAT}${substat.SECONDARY_STAT_TEXT}")
            binding.tvRuneSubOptimisation5.setText("${substat.SUB.getPoidsCalculated()}")

        }

        var poidsCumule : Int = 0
        rune.runeSubStats.forEach {

                poidsCumule+=it.SUB.getPoidsCalculated()
        }

        binding.tvRuneOptimisation.setText(poidsCumule.toString())


        binding.buttonCross.setOnClickListener {
            runeAnalyzerService.runeOptimisation?.destroyRuneOptimisation()
        }


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







    var windowManager: WindowManager? = null
    var windowManagerSettings : WindowManager.LayoutParams? = null;

    fun setWindowParams (_windowManager : WindowManager, _windowManagerSettings: WindowManager.LayoutParams ){
        windowManager = _windowManager
        windowManagerSettings = _windowManagerSettings

    }


    private fun interact(status: String){
        when (status) {
            TapStatus.Dragging -> {

            }
            TapStatus.DragEnd -> {
            }
            TapStatus.Tap -> {

            }
            else -> {
            }

        }

    }





}