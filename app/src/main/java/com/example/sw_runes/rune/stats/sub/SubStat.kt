package com.example.sw_runes.sw.rune.stats.sub

import com.example.sw_runes.rune.RuneRarity
import com.example.sw_runes.sw.rune.RuneStar
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat

open  class SubStat{

    companion object{
        val Nan = -1
        val PROC_NONE = 0
        val PROC_ONCE = 1
        val PROC_TWICE = 2
        val PROC_TRIPLE = 3
        val PROC_QUAD = 4
    }

    open var SUB : Sub = Sub(0)
    open var SUB_STAT_TEXT = ""
    open var SECONDARY_STAT_TEXT = ""
    open var POIDS_DEFAULT : Float = 0f
    open var POIDS_DEFINI : Float = 0f



    open  fun setSubStat(runeStars : Int, subStatValue: Int ) :SubStat  {

        return SubStat()

    }

    open fun checkSubStat(stringVal : String, primaryStat: PrimaryStat):Boolean  {

        return false

    }



    open inner class  Sub(statValue : Int) {




        open var STARS = RuneStar.NaN
        open var ACTUAL_STAT = 0

        open var MIN_PROC = 0
        open var MAX_PROC = 0

        open var MAX_MEULE = 0
        open var MIN_STAT = 0
        open var MAX_STAT = 0
        init {

            ACTUAL_STAT = statValue

        }




        open fun getPoidsCalculated():Int{
            if (ACTUAL_STAT <= 0)
                return 0

            return (ACTUAL_STAT * POIDS_DEFAULT * POIDS_DEFINI).toInt()
        }




    }


}