package com.example.myapplication.sw.rune.stats.primary

import com.example.myapplication.sw.rune.RuneLevel
import com.example.myapplication.sw.rune.RuneStar
import com.example.myapplication.sw.rune.emplacement.*

class HealthFlat: PrimaryStat() {


    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "PV"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && !stringVal.contains('%')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, stringVal: String ) :Int  {

        if (stringVal.contains(PRIMARY_STAT_TEXT) && !stringVal.contains('%')){
            var value = stringVal.substringAfter(PRIMARY_STAT_TEXT).substringAfter('+').substringBefore('+').replace(" ","").toIntOrNull()

            if (value!=  null ){

            var HealthFlatList : MutableList<Primary> = mutableListOf(
                HealthFlatSix(value),
                HealthFlatFive(value),
                HealthFlatFour(value),
                HealthFlatThree(value),
                HealthFlatTwo(value),
                HealthFlatOne(value),

            )

            HealthFlatList.forEach {

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
                    1 -> PRIMARY = HealthFlatOne(value)
                    2 -> PRIMARY = HealthFlatTwo(value)
                    3 -> PRIMARY = HealthFlatThree(value)
                    4 -> PRIMARY = HealthFlatFour(value)
                    5 -> PRIMARY = HealthFlatFive(value)
                    6 -> PRIMARY = HealthFlatSix(value)
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




    open inner class HealthFlatOne(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 40
        override var MAX_STAT = 804
        override var EACH_LEVEL = 45f
        override var LAST_LEVEL = 134

    }


    open inner class HealthFlatTwo(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 70
        override var MAX_STAT = 1092
        override var EACH_LEVEL = 60f
        override var LAST_LEVEL = 182

    }


    open inner class HealthFlatThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 100
        override var MAX_STAT = 1380
        override var EACH_LEVEL = 75f
        override var LAST_LEVEL = 230

    }


    open inner class HealthFlatFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 160
        override var MAX_STAT = 1704
        override var EACH_LEVEL = 90f
        override var LAST_LEVEL = 284

    }


    open inner class HealthFlatFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 270
        override var MAX_STAT = 2088
        override var EACH_LEVEL = 105f
        override var LAST_LEVEL = 348

    }


    open inner class HealthFlatSix(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 360
        override var MAX_STAT = 2448
        override var EACH_LEVEL = 120f
        override var LAST_LEVEL = 408

    }






}