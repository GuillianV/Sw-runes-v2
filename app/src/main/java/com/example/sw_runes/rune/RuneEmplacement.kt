package com.example.sw_runes.sw.rune

import com.example.sw_runes.sw.rune.emplacement.*
import com.example.sw_runes.sw.rune.stats.primary.AttackFlat
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat

class RuneEmplacement() {
    companion object{

        val NaN = 0
        val EMPLACEMENT_ONE = 1
        val EMPLACEMENT_TWO = 2
        val EMPLACEMENT_THREE = 3
        val EMPLACEMENT_FOUR = 4
        val EMPLACEMENT_FIVE = 5
        val EMPLACEMENT_SIX = 6




        fun setRuneEmplacement(emplacementNumber: Int):Emplacement{
                when(emplacementNumber){
                    1 -> return EmplacementOne()
                    2 -> return EmplacementTwo()
                    3 -> return EmplacementThree()
                    4 -> return EmplacementFour()
                    5 -> return EmplacementFive()
                    6 -> return EmplacementSix()
                    else ->{
                        return Emplacement()
                    }
                }
        }


    }



}