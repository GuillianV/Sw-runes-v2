package com.example.sw_runes.sw.rune.stats.primary

import com.example.sw_runes.sw.rune.RuneStar

class CritiqRate: PrimaryStat() {

    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "Tx critiq."
    override var SECONDARY_STAT_TEXT = "%"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT)&& stringVal.contains('+')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, stringVal: String ) :Int  {

        if (stringVal.contains(PRIMARY_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT)&& stringVal.contains('+')){
            var value = stringVal.substringAfter(PRIMARY_STAT_TEXT).substringAfter('+').substringBefore('%').replace(" ","").toIntOrNull()

            if (value!=  null ){

                var CritiqRateList : MutableList<Primary> = mutableListOf(
                    CritiqRateSix(value),
                    CritiqRateFive(value),
                    CritiqRateFour(value),
                    CritiqRateThree(value),
                    CritiqRateTwo(value),
                    CritiqRateOne(value),

                    )

                CritiqRateList.forEach {

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

        if (stringVal.contains(PRIMARY_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT) && stringVal.contains('%')) {
            var value =
                stringVal.substringAfter(PRIMARY_STAT_TEXT).substringAfter('+').substringBefore('%').replace(" ", "")
                    .toIntOrNull()


            if (value != null){
                when(runeStars){
                    1 -> PRIMARY = CritiqRateOne(value)
                    2 -> PRIMARY = CritiqRateTwo(value)
                    3 -> PRIMARY = CritiqRateThree(value)
                    4 -> PRIMARY = CritiqRateFour(value)
                    5 -> PRIMARY = CritiqRateFive(value)
                    6 -> PRIMARY = CritiqRateSix(value)
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




    open inner class CritiqRateOne(statValue: Int) : PrimaryStat.Primary(statValue){
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


    open inner class CritiqRateTwo(statValue: Int) : PrimaryStat.Primary(statValue){
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


    open inner class CritiqRateThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 3
        override var MAX_STAT = 37
        override var EACH_LEVEL = 2f
        override var LAST_LEVEL = 6

    }


    open inner class CritiqRateFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 4
        override var MAX_STAT = 41
        override var EACH_LEVEL = 2.25f
        override var LAST_LEVEL = 6

    }


    open inner class CritiqRateFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 5
        override var MAX_STAT = 47
        override var EACH_LEVEL = 2.5f
        override var LAST_LEVEL = 7

    }



    open inner class CritiqRateSix (statValue: Int) : PrimaryStat.Primary(statValue){
        override var ACTUAL_STAT = 0
        override var STARS = RuneStar.SIX
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 7
        override var MAX_STAT = 58
        override var EACH_LEVEL = 3f
        override var LAST_LEVEL = 9

    }



}