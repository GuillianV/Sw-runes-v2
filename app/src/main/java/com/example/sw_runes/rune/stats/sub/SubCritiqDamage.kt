package com.example.sw_runes.sw.rune.stats.sub

import com.example.sw_runes.sw.rune.RuneLevel
import com.example.sw_runes.sw.rune.RuneStar
import com.example.sw_runes.sw.rune.emplacement.*
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat

class SubCritiqDamage: SubStat() {


    override var SUB: Sub = Sub(0)
    override var SUB_STAT_TEXT = "Dgts critiq."
    override var SECONDARY_STAT_TEXT = "%"

    override fun checkSubStat(stringVal : String, primaryStat: PrimaryStat ):Boolean {
        return (stringVal.contains(SUB_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT)  )
    }


    override fun setSubStat(runeStars : Int, subStatValue: Int ) : SubStat {

        when(runeStars){
            1 ->  SUB = SubCritiqDamageOne(subStatValue) ;
            2 -> SUB = SubCritiqDamageTwo(subStatValue);
            3 -> SUB = SubCritiqDamageThree(subStatValue);
            4 -> SUB = SubCritiqDamageFour(subStatValue);
            5 -> SUB = SubCritiqDamageFive(subStatValue);
            6 -> SUB = SubCritiqDamageSix(subStatValue);
            else ->{
                SUB = Sub(0)
            }
        }

        return this
    }




    open inner class SubCritiqDamageOne(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 2



    }


    open inner class SubCritiqDamageTwo(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 3


    }


    open inner class SubCritiqDamageThree(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 2
        override var MAX_PROC = 4


    }


    open inner class SubCritiqDamageFour(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 2
        override var MAX_PROC = 5


    }


    open inner class SubCritiqDamageFive(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 3
        override var MAX_PROC = 5

    }


    open inner class SubCritiqDamageSix(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 4
        override var MAX_PROC = 7



    }






}