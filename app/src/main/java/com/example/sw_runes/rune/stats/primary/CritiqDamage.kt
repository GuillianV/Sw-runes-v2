package com.example.sw_runes.sw.rune.stats.primary

import com.example.sw_runes.sw.rune.RuneStar

class CritiqDamage: PrimaryStat() {

    override var PRIMARY: Primary = Primary(0)
    override var PRIMARY_STAT_TEXT = "Dgts critiq."
    override var SECONDARY_STAT_TEXT = "%"

    override fun checkPrimaryStat(stringVal : String ):Boolean {
        return stringVal.contains(PRIMARY_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT)&& stringVal.contains('+')
    }

    override fun setStarByPrimaryStat(runeLevel : Int, stringVal: String ) :Int  {

        if (stringVal.contains(PRIMARY_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT)&& stringVal.contains('+')){
            var value = stringVal.substringAfter(PRIMARY_STAT_TEXT).substringAfter('+').substringBefore('%').replace(" ","").toIntOrNull()

            if (value!=  null ){

                var CritiqDamageList : MutableList<Primary> = mutableListOf(
                    CritiqDamageSix(value),
                    CritiqDamageFive(value),
                    CritiqDamageFour(value),
                    CritiqDamageThree(value),
                    CritiqDamageTwo(value),
                    CritiqDamageOne(value),

                    )

                CritiqDamageList.forEach {

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
                    1 -> PRIMARY = CritiqDamageOne(value)
                    2 -> PRIMARY = CritiqDamageTwo(value)
                    3 -> PRIMARY = CritiqDamageThree(value)
                    4 -> PRIMARY = CritiqDamageFour(value)
                    5 -> PRIMARY = CritiqDamageFive(value)
                    6 -> PRIMARY = CritiqDamageSix(value)
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




    open inner class CritiqDamageOne(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 2
        override var MAX_STAT = 20
        override var EACH_LEVEL = 1f
        override var LAST_LEVEL = 4

    }


    open inner class CritiqDamageTwo(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }


        override var BASE_STAT= 3
        override var MAX_STAT = 37
        override var EACH_LEVEL = 2f
        override var LAST_LEVEL = 6

    }


    open inner class CritiqDamageThree(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 4
        override var MAX_STAT = 43
        override var EACH_LEVEL = 2.25f
        override var LAST_LEVEL = 7

    }


    open inner class CritiqDamageFour(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 6
        override var MAX_STAT = 58
        override var EACH_LEVEL = 3f
        override var LAST_LEVEL = 10

    }


    open inner class CritiqDamageFive(statValue: Int) : PrimaryStat.Primary(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 8
        override var MAX_STAT = 65
        override var EACH_LEVEL = 3.33f
        override var LAST_LEVEL = 10

    }



    open inner class CritiqDamageSix (statValue: Int) : PrimaryStat.Primary(statValue){
        override var ACTUAL_STAT = 0
        override var STARS = RuneStar.SIX
        init {
            ACTUAL_STAT = statValue
        }
        override var BASE_STAT= 11
        override var MAX_STAT = 80
        override var EACH_LEVEL = 4f
        override var LAST_LEVEL = 13

    }



}