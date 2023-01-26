package com.example.myapplication.sw.rune.stats.primary

import com.example.myapplication.sw.rune.RuneStar

class Resistance : PrimaryStat() {



    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "RES"
    override var SECONDARY_STAT_TEXT = "%"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT)&& stringVal.contains('+')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, stringVal: String ) :Int  {

        if (checkPrimaryStat(stringVal)){
            var value = stringVal.substringAfter(PRIMARY_STAT_TEXT).substringAfter('+').substringBefore('%').replace(" ","").toIntOrNull()

            if (value!=  null ){

                var resistanceList : MutableList<Primary> = mutableListOf(
                    ResistanceOne(value),
                    ResistanceTwo(value),
                    ResistanceThree(value),
                    ResistanceFour(value),
                    ResistanceFive(value),
                    ResistanceSix(value),

                    )

                resistanceList.forEach {

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

        if (checkPrimaryStat(stringVal)) {
            var value =
                stringVal.substringAfter(PRIMARY_STAT_TEXT).substringAfter('+').substringBefore('%').replace(" ", "")
                    .toIntOrNull()


            if (value != null){
                when(runeStars){
                    1 -> PRIMARY = ResistanceOne(value)
                    2 -> PRIMARY = ResistanceTwo(value)
                    3 -> PRIMARY = ResistanceThree(value)
                    4 -> PRIMARY = ResistanceFour(value)
                    5 -> PRIMARY = ResistanceFive(value)
                    6 -> PRIMARY = ResistanceSix(value)
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




    open inner class ResistanceOne(statValue: Int) : PrimaryStat.Primary(statValue){
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


    open inner class ResistanceTwo(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }


        override var BASE_STAT= 2
        override var MAX_STAT = 20
        override var EACH_LEVEL = 1f
        override var LAST_LEVEL = 4

    }


    open inner class ResistanceThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 4
        override var MAX_STAT = 38
        override var EACH_LEVEL = 2f
        override var LAST_LEVEL = 6

    }


    open inner class ResistanceFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 5
        override var MAX_STAT = 44
        override var EACH_LEVEL = 2.25f
        override var LAST_LEVEL = 7

    }


    open inner class ResistanceFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 8
        override var MAX_STAT = 51
        override var EACH_LEVEL = 2.5f
        override var LAST_LEVEL = 8

    }



    open inner class ResistanceSix  (statValue: Int) : PrimaryStat.Primary(statValue){
        override var ACTUAL_STAT = 0
        override var STARS = RuneStar.SIX
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 12
        override var MAX_STAT = 64
        override var EACH_LEVEL = 3f
        override var LAST_LEVEL = 10

    }


}