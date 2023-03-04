package com.example.sw_runes.sw.rune.stats.primary

import com.example.sw_runes.sw.rune.RuneStar

class DefencePercent: PrimaryStat() {

    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "DEF"
    override var SECONDARY_STAT_TEXT = "%"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT)&& stringVal.contains('+')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, primaryStatValue: Int ) :Int  {


                var DefencePercentList : MutableList<Primary> = mutableListOf(
                    DefencePercentSix(primaryStatValue),
                    DefencePercentFive(primaryStatValue),
                    DefencePercentFour(primaryStatValue),
                    DefencePercentThree(primaryStatValue),
                    DefencePercentTwo(primaryStatValue),
                    DefencePercentOne(primaryStatValue),

                    )

                DefencePercentList.forEach {

                    if ( it.checkPrimaryWithLevel(primaryStatValue,runeLevel)){
                        return it.STARS
                    }

                }

                return RuneStar.NaN

    }


    override fun setPrimaryStat(runeStars : Int, primaryStatValue : Int   ) : PrimaryStat {

        when(runeStars){
            1 -> PRIMARY = DefencePercentOne(primaryStatValue)
            2 -> PRIMARY = DefencePercentTwo(primaryStatValue)
            3 -> PRIMARY = DefencePercentThree(primaryStatValue)
            4 -> PRIMARY = DefencePercentFour(primaryStatValue)
            5 -> PRIMARY = DefencePercentFive(primaryStatValue)
            6 -> PRIMARY = DefencePercentSix(primaryStatValue)
            else ->{
                PRIMARY = Primary(0)
            }
        }

        return this
    }




    open inner class DefencePercentOne(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,18)


    }


    open inner class DefencePercentTwo(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }


        override var statsList:  Array<Int> = arrayOf(2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,20)


    }


    open inner class DefencePercentThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,38)


    }


    open inner class DefencePercentFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(5,7,9,11,14,16,18,20,23,25,27,29,32,34,36,43)


    }


    open inner class DefencePercentFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> =  arrayOf(8,10,12,15,17,20,22,24,27,29,32,34,37,40,43,51)


    }



    open inner class DefencePercentSix (statValue: Int) : PrimaryStat.Primary(statValue){
        override var ACTUAL_STAT = 0
        override var STARS = RuneStar.SIX
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(11,14,19,20,23,26,29,32,35,38,41,44,47,50,53,63)


    }



}