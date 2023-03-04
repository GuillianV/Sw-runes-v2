package com.example.sw_runes.sw.rune.stats.primary

import com.example.sw_runes.sw.rune.RuneStar

class AttackPercent: PrimaryStat() {

    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "ATQ"
    override var SECONDARY_STAT_TEXT = "%"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT)&& stringVal.contains('+')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, primaryStatValue: Int ) :Int  {


                var attackFlatList : MutableList<Primary> = mutableListOf(
                    AttackPercentSix(primaryStatValue),
                    AttackPercentFive(primaryStatValue),
                    AttackPercentFour(primaryStatValue),
                    AttackPercentThree(primaryStatValue),
                    AttackPercentTwo(primaryStatValue),
                    AttackPercentOne(primaryStatValue),

                    )

                attackFlatList.forEach {

                    if ( it.checkPrimaryWithLevel(primaryStatValue,runeLevel)){
                        return it.STARS
                    }

                }

                return RuneStar.NaN

    }


    override fun setPrimaryStat(runeStars : Int, primaryStatValue : Int   ) : PrimaryStat {

        when(runeStars){
            1 -> PRIMARY = AttackPercentOne(primaryStatValue)
            2 -> PRIMARY = AttackPercentTwo(primaryStatValue)
            3 -> PRIMARY = AttackPercentThree(primaryStatValue)
            4 -> PRIMARY = AttackPercentFour(primaryStatValue)
            5 -> PRIMARY = AttackPercentFive(primaryStatValue)
            6 -> PRIMARY = AttackPercentSix(primaryStatValue)
            else ->{
                PRIMARY = Primary(0)
            }
        }
        return this
    }




    open inner class AttackPercentOne(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,18)

    }


    open inner class AttackPercentTwo(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }


        override var statsList:  Array<Int> = arrayOf(2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,20)

    }


    open inner class AttackPercentThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,38)
 

    }


    open inner class AttackPercentFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(5,7,9,11,14,16,18,20,23,25,27,29,32,34,36,43)
 

    }


    open inner class AttackPercentFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(8,10,12,15,17,20,22,24,27,29,32,34,37,40,43,51)
 

    }



    open inner class AttackPercentSix (statValue: Int) : PrimaryStat.Primary(statValue){
        override var ACTUAL_STAT = 0
        override var STARS = RuneStar.SIX
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(11,14,19,20,23,26,29,32,35,38,41,44,47,50,53,63)
 

    }



}