package com.example.sw_runes.sw.rune.stats.primary

import com.example.sw_runes.sw.rune.RuneStar

class CritiqDamage: PrimaryStat() {

    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "Dgts critiq."
    override var SECONDARY_STAT_TEXT = "%"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT)&& stringVal.contains('+')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, primaryStatValue: Int ) :Int  {

                var CritiqDamageList : MutableList<Primary> = mutableListOf(
                    CritiqDamageSix(primaryStatValue),
                    CritiqDamageFive(primaryStatValue),
                    CritiqDamageFour(primaryStatValue),
                    CritiqDamageThree(primaryStatValue),
                    CritiqDamageTwo(primaryStatValue),
                    CritiqDamageOne(primaryStatValue),

                    )

                CritiqDamageList.forEach {

                    if ( it.checkPrimaryWithLevel(primaryStatValue,runeLevel)){
                        return it.STARS
                    }

                }

                return RuneStar.NaN

    }


    override fun setPrimaryStat(runeStars : Int, primaryStatValue : Int   ) : PrimaryStat {


        when(runeStars){
            1 -> PRIMARY = CritiqDamageOne(primaryStatValue)
            2 -> PRIMARY = CritiqDamageTwo(primaryStatValue)
            3 -> PRIMARY = CritiqDamageThree(primaryStatValue)
            4 -> PRIMARY = CritiqDamageFour(primaryStatValue)
            5 -> PRIMARY = CritiqDamageFive(primaryStatValue)
            6 -> PRIMARY = CritiqDamageSix(primaryStatValue)
            else ->{
                PRIMARY = Primary(0)
            }
        }
        return this
    }




    open inner class CritiqDamageOne(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,20)


    }


    open inner class CritiqDamageTwo(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }


          override var statsList:  Array<Int> = arrayOf(3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,37)
 

    }


    open inner class CritiqDamageThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(4,6,8,10,13,15,17,19,22,24,26,28,31,33,35,43)


    }


    open inner class CritiqDamageFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(6,9,12,15,18,21,24,27,30,33,36,39,42,45,48,58)
 

    }


    open inner class CritiqDamageFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(8,11,14,17,21,24,27,31,34,37,41,44,47,51,54,65)
 

    }



    open inner class CritiqDamageSix (statValue: Int) : PrimaryStat.Primary(statValue){
        override var ACTUAL_STAT = 0
        override var STARS = RuneStar.SIX
        init {
            ACTUAL_STAT = statValue
        }
          override var statsList:  Array<Int> = arrayOf(11,15,19,23,27,31,35,39,43,47,51,55,59,63,67,80)
 

    }



}