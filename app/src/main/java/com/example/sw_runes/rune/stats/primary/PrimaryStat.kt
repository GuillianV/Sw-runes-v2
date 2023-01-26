package com.example.myapplication.sw.rune.stats.primary

import com.example.myapplication.sw.rune.RuneLevel
import com.example.myapplication.sw.rune.RuneStar
import kotlin.math.roundToInt

open  class PrimaryStat{



    open var PRIMARY : Primary = Primary(0)
    open var PRIMARY_STAT_TEXT = ""
    open var SECONDARY_STAT_TEXT = ""

    open  fun setStarByPrimaryStat(runeLevel : Int , string: String) :Int  {

        return RuneStar.NaN

    }

    open  fun setPrimaryStat(runeStars : Int, stringVal: String) :PrimaryStat  {

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


        open var BASE_STAT = 0
        open var MAX_STAT = 0

        open var EACH_LEVEL : Float = 0f
        open var LAST_LEVEL = 0

        open fun checkPrimaryWithLevel(primaryStatvalue : Int, runeLevel: Int):Boolean{
            when(runeLevel){
                15 -> return  primaryStatvalue == MAX_STAT
                14 -> return primaryStatvalue == ( MAX_STAT - LAST_LEVEL)
                0 -> return primaryStatvalue == BASE_STAT
                else -> {
                    var amount = (runeLevel * EACH_LEVEL) + BASE_STAT

                    return amount.roundToInt() == primaryStatvalue && amount <= MAX_STAT



                }

            }
        }




    }


}