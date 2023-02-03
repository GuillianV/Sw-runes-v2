package com.example.sw_runes.sw.rune.stats.primary

import com.example.sw_runes.sw.rune.RuneStar

class Resistance : PrimaryStat() {



    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "RES"
    override var SECONDARY_STAT_TEXT = "%"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT)&& stringVal.contains('+')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, primaryStatValue: Int ) :Int  {


                var resistanceList : MutableList<Primary> = mutableListOf(
                    ResistanceOne(primaryStatValue),
                    ResistanceTwo(primaryStatValue),
                    ResistanceThree(primaryStatValue),
                    ResistanceFour(primaryStatValue),
                    ResistanceFive(primaryStatValue),
                    ResistanceSix(primaryStatValue),

                    )

                resistanceList.forEach {

                    if ( it.checkPrimaryWithLevel(primaryStatValue,runeLevel)){
                        return it.STARS
                    }

                }

                return RuneStar.NaN

    }


    override fun setPrimaryStat(runeStars : Int, primaryStatValue : Int   ) : PrimaryStat {
        when(runeStars){
            1 -> PRIMARY = ResistanceOne(primaryStatValue)
            2 -> PRIMARY = ResistanceTwo(primaryStatValue)
            3 -> PRIMARY = ResistanceThree(primaryStatValue)
            4 -> PRIMARY = ResistanceFour(primaryStatValue)
            5 -> PRIMARY = ResistanceFive(primaryStatValue)
            6 -> PRIMARY = ResistanceSix(primaryStatValue)
            else ->{
                PRIMARY = Primary(0)
            }
        }
        return this
    }




    open inner class ResistanceOne(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,18)


    }


    open inner class ResistanceTwo(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }


          override var statsList:  Array<Int> = arrayOf(2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,20)
 

    }


    open inner class ResistanceThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,38)
 

    }


    open inner class ResistanceFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(6,8,10,12,15,17,19,21,24,26,28,30,33,35,37,44)


    }


    open inner class ResistanceFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(9,11,14,16,19,21,24,26,29,31,34,36,39,41,44,51)
 

    }



    open inner class ResistanceSix  (statValue: Int) : PrimaryStat.Primary(statValue){
        override var ACTUAL_STAT = 0
        override var STARS = RuneStar.SIX
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(12,15,18,21,24,27,30,33,36,39,42,45,48,51,54,64)


    }


}