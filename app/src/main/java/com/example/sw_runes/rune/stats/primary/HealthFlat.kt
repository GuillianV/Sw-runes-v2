package com.example.sw_runes.sw.rune.stats.primary

import com.example.sw_runes.sw.rune.RuneLevel
import com.example.sw_runes.sw.rune.RuneStar
import com.example.sw_runes.sw.rune.emplacement.*

class HealthFlat: PrimaryStat() {


    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "PV"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && !stringVal.contains('%')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, primaryStatValue: Int ) :Int  {

            var HealthFlatList : MutableList<Primary> = mutableListOf(
                HealthFlatSix(primaryStatValue),
                HealthFlatFive(primaryStatValue),
                HealthFlatFour(primaryStatValue),
                HealthFlatThree(primaryStatValue),
                HealthFlatTwo(primaryStatValue),
                HealthFlatOne(primaryStatValue),

            )

            HealthFlatList.forEach {

                if ( it.checkPrimaryWithLevel(primaryStatValue,runeLevel)){
                    return it.STARS
                }
                    
            }

            return RuneStar.NaN

    }


    override fun setPrimaryStat(runeStars : Int, primaryStatValue : Int   ) : PrimaryStat {

        when(runeStars){
            1 -> PRIMARY = HealthFlatOne(primaryStatValue)
            2 -> PRIMARY = HealthFlatTwo(primaryStatValue)
            3 -> PRIMARY = HealthFlatThree(primaryStatValue)
            4 -> PRIMARY = HealthFlatFour(primaryStatValue)
            5 -> PRIMARY = HealthFlatFive(primaryStatValue)
            6 -> PRIMARY = HealthFlatSix(primaryStatValue)
            else ->{
                PRIMARY = Primary(0)
            }
        }


        return this
    }




    open inner class HealthFlatOne(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(40,85,130,175,220,265,310,355,400,445,490,535,580,625,670,804)
 

    }


    open inner class HealthFlatTwo(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(70,130,190,250,310,370,430,490,550,610,670,730,790,850,910,1092)
 

    }


    open inner class HealthFlatThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(100,175,250,325,400,475,550,625,700,775,850,925,1000,1075,1150,1380)
 

    }


    open inner class HealthFlatFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(160,250,340,430,520,610,700,790,880,970,1060,1150,1240,1330,1420,1704)
 

    }


    open inner class HealthFlatFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(270,375,480,585,690,795,900,1005,1110,1215,1320,1425,1530,1635,1740,2088)
 

    }


    open inner class HealthFlatSix(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(360,480,600,720,840,960,1080,1200,1320,1440,1560,1680,1800,1920,2040,2448)
 
    }






}