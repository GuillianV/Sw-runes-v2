package com.example.sw_runes.sw.rune.stats.primary

import com.example.sw_runes.sw.rune.RuneLevel
import com.example.sw_runes.sw.rune.RuneStar
import com.example.sw_runes.sw.rune.emplacement.*

class Speed: PrimaryStat() {


    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "VIT"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && !stringVal.contains('%')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, primaryStatValue: Int ) :Int  {


            var SpeedList : MutableList<Primary> = mutableListOf(
                SpeedSix(primaryStatValue),
                SpeedFive(primaryStatValue),
                SpeedFour(primaryStatValue),
                SpeedThree(primaryStatValue),
                SpeedTwo(primaryStatValue),
                SpeedOne(primaryStatValue),

            )

            SpeedList.forEach {

                if ( it.checkPrimaryWithLevel(primaryStatValue,runeLevel)){
                    return it.STARS
                }
                    
            }

            return RuneStar.NaN

    }


    override fun setPrimaryStat(runeStars : Int, primaryStatValue : Int   ) : PrimaryStat {


        when(runeStars){
            1 -> PRIMARY = SpeedOne(primaryStatValue)
            2 -> PRIMARY = SpeedTwo(primaryStatValue)
            3 -> PRIMARY = SpeedThree(primaryStatValue)
            4 -> PRIMARY = SpeedFour(primaryStatValue)
            5 -> PRIMARY = SpeedFive(primaryStatValue)
            6 -> PRIMARY = SpeedSix(primaryStatValue)
            else ->{
                PRIMARY = Primary(0)
            }
        }


        return this
    }




    open inner class SpeedOne(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,18)
 

    }


    open inner class SpeedTwo(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,19)
 

    }


    open inner class SpeedThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(3,4,5,6,8,9,10,12,13,14,16,17,18,20,21,25)
 

    }


    open inner class SpeedFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(4,5,7,8,10,11,13,14,16,17,19,20,22,23,25,30)


    }


    open inner class SpeedFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,39)


    }


    open inner class SpeedSix(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,42)


    }






}