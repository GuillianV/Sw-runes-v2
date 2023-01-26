package com.example.myapplication.sw.rune.stats.primary

import com.example.myapplication.sw.rune.RuneLevel
import com.example.myapplication.sw.rune.RuneStar
import com.example.myapplication.sw.rune.emplacement.*

class AttackFlat: PrimaryStat() {


    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "ATQ"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && !stringVal.contains('%')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, stringVal: String ) :Int  {

        if (stringVal.contains(PRIMARY_STAT_TEXT) && !stringVal.contains('%')){
            var value = stringVal.substringAfter(PRIMARY_STAT_TEXT).substringAfter('+').substringBefore('+').replace(" ","").toIntOrNull()

            if (value!=  null ){

            var attackFlatList : MutableList<Primary> = mutableListOf(
                AttackFlatSix(value),
                AttackFlatFive(value),
                AttackFlatFour(value),
                AttackFlatThree(value),
                AttackFlatTwo(value),
                AttackFlatOne(value),

            )

            attackFlatList.forEach {

                if ( it.checkPrimaryWithLevel(value,runeLevel)){
                    return it.STARS
                }
                    
            }

            return RuneStar.NaN
        }else{
            return  RuneStar.NaN
        }
        }else{
            return  RuneStar.NaN
        }
    }


    override fun setPrimaryStat(runeStars : Int, stringVal: String  ) : PrimaryStat {

        if (stringVal.contains(PRIMARY_STAT_TEXT) && !stringVal.contains('%')) {
            var value =
                stringVal.substringAfter(PRIMARY_STAT_TEXT).substringAfter('+').substringBefore('+').replace(" ", "")
                    .toIntOrNull()
      

            if (value != null){
                when(runeStars){
                    1 -> PRIMARY = AttackFlatOne(value)
                    2 -> PRIMARY = AttackFlatTwo(value)
                    3 -> PRIMARY = AttackFlatThree(value)
                    4 -> PRIMARY = AttackFlatFour(value)
                    5 -> PRIMARY = AttackFlatFive(value)
                    6 -> PRIMARY = AttackFlatSix(value)
                    else ->{
                        PRIMARY = Primary(0)
                    }
                }
            }else{
                PRIMARY =  Primary(0)
            }

        }else{
            PRIMARY =  Primary(0)
        }

        return this
    }




    open inner class AttackFlatOne(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 3
        override var MAX_STAT = 54
        override var EACH_LEVEL = 3f
        override var LAST_LEVEL = 9

    }


    open inner class AttackFlatTwo(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 5
        override var MAX_STAT = 74
        override var EACH_LEVEL = 4f
        override var LAST_LEVEL = 13

    }


    open inner class AttackFlatThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 7
        override var MAX_STAT = 93
        override var EACH_LEVEL = 5f
        override var LAST_LEVEL = 16

    }


    open inner class AttackFlatFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 10
        override var MAX_STAT = 112
        override var EACH_LEVEL = 6f
        override var LAST_LEVEL = 18

    }


    open inner class AttackFlatFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 15
        override var MAX_STAT = 135
        override var EACH_LEVEL = 7f
        override var LAST_LEVEL = 22

    }


    open inner class AttackFlatSix(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 22
        override var MAX_STAT = 160
        override var EACH_LEVEL = 8f
        override var LAST_LEVEL = 26

    }






}