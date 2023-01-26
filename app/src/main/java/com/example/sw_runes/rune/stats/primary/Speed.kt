package com.example.myapplication.sw.rune.stats.primary

import com.example.myapplication.sw.rune.RuneLevel
import com.example.myapplication.sw.rune.RuneStar
import com.example.myapplication.sw.rune.emplacement.*

class Speed: PrimaryStat() {


    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "VIT"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && !stringVal.contains('%')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, stringVal: String ) :Int  {

        if (stringVal.contains(PRIMARY_STAT_TEXT) && !stringVal.contains('%')){
            var value = stringVal.substringAfter(PRIMARY_STAT_TEXT).substringAfter('+').substringBefore('+').replace(" ","").toIntOrNull()

            if (value!=  null ){

            var SpeedList : MutableList<Primary> = mutableListOf(
                SpeedSix(value),
                SpeedFive(value),
                SpeedFour(value),
                SpeedThree(value),
                SpeedTwo(value),
                SpeedOne(value),

            )

            SpeedList.forEach {

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
                    1 -> PRIMARY = SpeedOne(value)
                    2 -> PRIMARY = SpeedTwo(value)
                    3 -> PRIMARY = SpeedThree(value)
                    4 -> PRIMARY = SpeedFour(value)
                    5 -> PRIMARY = SpeedFive(value)
                    6 -> PRIMARY = SpeedSix(value)
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




    open inner class SpeedOne(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 1
        override var MAX_STAT = 18
        override var EACH_LEVEL = 1f
        override var LAST_LEVEL = 3

    }


    open inner class SpeedTwo(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 2
        override var MAX_STAT = 19
        override var EACH_LEVEL =1f
        override var LAST_LEVEL = 3

    }


    open inner class SpeedThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 3
        override var MAX_STAT = 25
        override var EACH_LEVEL = 1.33f
        override var LAST_LEVEL = 3

    }


    open inner class SpeedFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 4
        override var MAX_STAT = 30
        override var EACH_LEVEL = 1.5f
        override var LAST_LEVEL = 5

    }


    open inner class SpeedFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 5
        override var MAX_STAT = 39
        override var EACH_LEVEL = 2f
        override var LAST_LEVEL = 6

    }


    open inner class SpeedSix(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 7
        override var MAX_STAT = 42
        override var EACH_LEVEL = 2f
        override var LAST_LEVEL = 7

    }






}