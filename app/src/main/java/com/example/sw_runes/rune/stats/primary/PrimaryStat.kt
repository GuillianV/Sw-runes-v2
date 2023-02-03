package com.example.sw_runes.sw.rune.stats.primary

import com.example.sw_runes.sw.rune.RuneLevel
import com.example.sw_runes.sw.rune.RuneStar
import kotlin.math.roundToInt

open  class PrimaryStat{



    open var PRIMARY : Primary = Primary(0)
    open var PRIMARY_STAT_TEXT = ""
    open var SECONDARY_STAT_TEXT = ""

    open  fun setStarByPrimaryStat(runeLevel : Int , primaryStatValue : Int) :Int  {

        return RuneStar.NaN

    }

    open  fun setPrimaryStat(runeStars : Int, primaryStatValue : Int ) :PrimaryStat  {

        return PrimaryStat()

    }

    open fun checkPrimaryStat(stringVal : String ):Boolean  {

        return false

    }



    open inner class  Primary(statValue : Int) {

        open var STARS = RuneStar.NaN
        open var ACTUAL_STAT = 0

        init {
            ACTUAL_STAT = statValue
        }

        open var statsList:  Array<Int> = arrayOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)


        fun checkPrimaryWithLevel(primaryStatvalue : Int, runeLevel: Int):Boolean{
                return primaryStatvalue == statsList[runeLevel]
        }




    }


}