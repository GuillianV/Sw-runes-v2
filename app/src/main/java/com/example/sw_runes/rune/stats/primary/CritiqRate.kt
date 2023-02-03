package com.example.sw_runes.sw.rune.stats.primary

import com.example.sw_runes.sw.rune.RuneStar

class CritiqRate: PrimaryStat() {

    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "Tx critiq."
    override var SECONDARY_STAT_TEXT = "%"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT)&& stringVal.contains('+')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, primaryStatValue: Int ) :Int  {


                var CritiqRateList : MutableList<Primary> = mutableListOf(
                    CritiqRateSix(primaryStatValue),
                    CritiqRateFive(primaryStatValue),
                    CritiqRateFour(primaryStatValue),
                    CritiqRateThree(primaryStatValue),
                    CritiqRateTwo(primaryStatValue),
                    CritiqRateOne(primaryStatValue),

                    )

                CritiqRateList.forEach {

                    if ( it.checkPrimaryWithLevel(primaryStatValue,runeLevel)){
                        return it.STARS
                    }

                }

                return RuneStar.NaN

    }


    override fun setPrimaryStat(runeStars : Int, primaryStatValue : Int
   ) : PrimaryStat {

        when(runeStars){
            1 -> PRIMARY = CritiqRateOne(primaryStatValue)
            2 -> PRIMARY = CritiqRateTwo(primaryStatValue)
            3 -> PRIMARY = CritiqRateThree(primaryStatValue)
            4 -> PRIMARY = CritiqRateFour(primaryStatValue)
            5 -> PRIMARY = CritiqRateFive(primaryStatValue)
            6 -> PRIMARY = CritiqRateSix(primaryStatValue)
            else ->{
                PRIMARY = Primary(0)
            }
        }

        return this
    }




    open inner class CritiqRateOne(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,18)


    }


    open inner class CritiqRateTwo(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }


        override var statsList:  Array<Int> = arrayOf(2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,20)


    }


    open inner class CritiqRateThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,37)
 

    }


    open inner class CritiqRateFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(4,6,8,10,13,15,17,19,22,24,26,28,31,33,35,41)


    }


    open inner class CritiqRateFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(5,7,10,12,15,17,20,22,25,27,30,32,35,37,40,47)
 

    }



    open inner class CritiqRateSix (statValue: Int) : PrimaryStat.Primary(statValue){
        override var ACTUAL_STAT = 0
        override var STARS = RuneStar.SIX
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(7,10,13,16,19,22,25,28,31,34,37,40,43,46,49,58)
 

    }



}