package com.example.sw_runes.sw.rune.stats.primary

import com.example.sw_runes.sw.rune.RuneLevel
import com.example.sw_runes.sw.rune.RuneStar
import com.example.sw_runes.sw.rune.emplacement.*

class AttackFlat: PrimaryStat() {


    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "ATQ"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && !stringVal.contains('%')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, primaryStatValue: Int ) :Int  {

            var attackFlatList : MutableList<Primary> = mutableListOf(
                AttackFlatSix(primaryStatValue),
                AttackFlatFive(primaryStatValue),
                AttackFlatFour(primaryStatValue),
                AttackFlatThree(primaryStatValue),
                AttackFlatTwo(primaryStatValue),
                AttackFlatOne(primaryStatValue),

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
                    1 -> PRIMARY = AttackFlatOne(primaryStatValue)
                    2 -> PRIMARY = AttackFlatTwo(primaryStatValue)
                    3 -> PRIMARY = AttackFlatThree(primaryStatValue)
                    4 -> PRIMARY = AttackFlatFour(primaryStatValue)
                    5 -> PRIMARY = AttackFlatFive(primaryStatValue)
                    6 -> PRIMARY = AttackFlatSix(primaryStatValue)
                    else ->{
                        PRIMARY = Primary(0)
                    }
                }

        return this
    }




    open inner class AttackFlatOne(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(3,6,9,12,15,18,21,24,27,30,33,36,39,42,45,54)

    }


    open inner class AttackFlatTwo(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(5,9,13,17,21,25,29,33,37,41,45,49,53,57,61,74)

    }


    open inner class AttackFlatThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var statsList:  Array<Int> = arrayOf(7,13,17,22,27,32,37,42,47,52,57,62,67,72,77,93)


    }


    open inner class AttackFlatFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }

        override var statsList:  Array<Int> = arrayOf(10,16,22,28,34,40,46,52,58,64,70,76,82,88,94,112)


    }


    open inner class AttackFlatFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }

        override var statsList:  Array<Int> = arrayOf(15,22,29,36,43,50,57,64,71,78,85,92,99,106,113,135)


    }


    open inner class AttackFlatSix(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }

        override var statsList:  Array<Int> = arrayOf(22,30,38,46,54,62,70,78,86,94,102,110,118,126,134,160)



    }






}