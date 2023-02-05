package com.example.sw_runes.sw.rune.stats.sub

import com.example.sw_runes.sw.rune.RuneLevel
import com.example.sw_runes.sw.rune.RuneStar
import com.example.sw_runes.sw.rune.emplacement.*
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat

class SubSpeed: SubStat() {


    override var SUB: Sub = Sub(0)
    override var SUB_STAT_TEXT = "VIT"

    override fun checkSubStat(stringVal : String, primaryStat: PrimaryStat ):Boolean {
        return (!stringVal.contains("Set") && stringVal.contains(SUB_STAT_TEXT) && !stringVal.contains('%') && !primaryStat.PRIMARY_STAT_TEXT.contains(SUB_STAT_TEXT) )
    }


    override fun setSubStat(runeStars : Int, subStatValue: Int ) : SubStat {
        if (SUB.ACTUAL_STAT == 0) return this
        when(runeStars){
            1 ->  SUB = SubSpeedOne(subStatValue) ;
            2 -> SUB = SubSpeedTwo(subStatValue);
            3 -> SUB = SubSpeedThree(subStatValue);
            4 -> SUB = SubSpeedFour(subStatValue);
            5 -> SUB = SubSpeedFive(subStatValue);
            6 -> SUB = SubSpeedSix(subStatValue);
            else ->{
                SUB = Sub(0)
            }
        }

        return this
    }




    open inner class SubSpeedOne(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 1




    }


    open inner class SubSpeedTwo(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 2




    }


    open inner class SubSpeedThree(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 3



    }


    open inner class SubSpeedFour(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 2
        override var MAX_PROC = 4




    }


    open inner class SubSpeedFive(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 3
        override var MAX_PROC = 5



    }


    open inner class SubSpeedSix(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 4
        override var MAX_PROC = 6


    }






}