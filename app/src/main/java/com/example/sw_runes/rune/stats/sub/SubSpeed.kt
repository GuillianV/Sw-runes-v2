package com.example.sw_runes.sw.rune.stats.sub

import com.example.sw_runes.sw.rune.RuneLevel
import com.example.sw_runes.sw.rune.RuneStar
import com.example.sw_runes.sw.rune.emplacement.*
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat

class SubSpeed: SubStat() {


    override var SUB: Sub = Sub(0)
    override var SUB_STAT_TEXT = "VIT"
    override var POIDS_DEFAULT : Float = 66.5f
    override var POIDS_DEFINI : Float = 1.3f

override fun checkSubStat(stringVal : String, primaryStat: PrimaryStat ):Boolean {
        return (!stringVal.contains("Set") &&( stringVal.contains(SUB_STAT_TEXT) && !stringVal.contains('%') && !primaryStat.PRIMARY_STAT_TEXT.contains(SUB_STAT_TEXT)) )
    }


    override fun setSubStat(runeStars : Int, subStatValue: Int ) : SubStat {

        var testingSub : SubStat.Sub

        when(runeStars){
            1 -> testingSub = SubSpeedOne(subStatValue) ;
            2 -> testingSub = SubSpeedTwo(subStatValue);
            3 -> testingSub = SubSpeedThree(subStatValue);
            4 -> testingSub = SubSpeedFour(subStatValue);
            5 -> testingSub = SubSpeedFive(subStatValue);
            6 -> testingSub = SubSpeedSix(subStatValue);
            else ->{
                testingSub = Sub(0)
            }
        }

        if (SUB.ACTUAL_STAT != 0 || subStatValue > testingSub.MAX_STAT ) return this

        SUB = testingSub
        return this
    }




    open inner class SubSpeedOne(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 1

        override var MAX_MEULE = 5

        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE


    }


    open inner class SubSpeedTwo(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 2

        override var MAX_MEULE = 5
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE


    }


    open inner class SubSpeedThree(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 3

        override var MAX_MEULE = 5
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE

    }


    open inner class SubSpeedFour(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 2
        override var MAX_PROC = 4

        override var MAX_MEULE = 5
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE


    }


    open inner class SubSpeedFive(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0
        override var MIN_PROC = 3
        override var MAX_PROC = 5

        override var MAX_MEULE = 5
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE

    }


    open inner class SubSpeedSix(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 4
        override var MAX_PROC = 6

        override var MAX_MEULE = 5
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE

    }






}