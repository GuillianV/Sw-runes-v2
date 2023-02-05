package com.example.sw_runes.sw.rune.stats.sub

import com.example.sw_runes.sw.rune.RuneLevel
import com.example.sw_runes.sw.rune.RuneStar
import com.example.sw_runes.sw.rune.emplacement.*
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat

class SubAttackPercent: SubStat() {


    override var SUB: Sub = Sub(0)
    override var SUB_STAT_TEXT = "ATQ"
    override var SECONDARY_STAT_TEXT = "%"

    override fun checkSubStat(stringVal : String, primaryStat: PrimaryStat ):Boolean {
        return (stringVal.contains(SUB_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT)  && (primaryStat.PRIMARY_STAT_TEXT != SUB_STAT_TEXT || (primaryStat.PRIMARY_STAT_TEXT.contains(SUB_STAT_TEXT) && !primaryStat.SECONDARY_STAT_TEXT.contains("%"))) )
    }


    override fun setSubStat(runeStars : Int, subStatValue: Int ) : SubStat {

        when(runeStars){
            1 ->  SUB = SubAttackPercentOne(subStatValue) ;
            2 -> SUB = SubAttackPercentTwo(subStatValue);
            3 -> SUB = SubAttackPercentThree(subStatValue);
            4 -> SUB = SubAttackPercentFour(subStatValue);
            5 -> SUB = SubAttackPercentFive(subStatValue);
            6 -> SUB = SubAttackPercentSix(subStatValue);
            else ->{
                SUB = Sub(0)
            }
        }


        return this
    }




    open inner class SubAttackPercentOne(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 2


    }


    open inner class SubAttackPercentTwo(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 3



    }


    open inner class SubAttackPercentThree(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 2
        override var MAX_PROC = 5


    }


    open inner class SubAttackPercentFour(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 3
        override var MAX_PROC = 6


    }


    open inner class SubAttackPercentFive(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 4
        override var MAX_PROC = 7


    }


    open inner class SubAttackPercentSix(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 5
        override var MAX_PROC = 8


    }






}