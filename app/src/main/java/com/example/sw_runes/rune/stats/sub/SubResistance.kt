package com.example.sw_runes.sw.rune.stats.sub

import com.example.sw_runes.sw.rune.RuneLevel
import com.example.sw_runes.sw.rune.RuneStar
import com.example.sw_runes.sw.rune.emplacement.*
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat

class SubResistance: SubStat() {


    override var SUB: Sub = Sub(0)
    override var SUB_STAT_TEXT = "RES"
    override var SECONDARY_STAT_TEXT = "%"

    override var POIDS_DEFAULT : Float = 50f
    override var POIDS_DEFINI : Float = 1.3f
override fun checkSubStat(stringVal : String, primaryStat: PrimaryStat ):Boolean {
        return (!stringVal.contains("Set") && (stringVal.contains(SUB_STAT_TEXT) && stringVal.contains(SECONDARY_STAT_TEXT) && !primaryStat.PRIMARY_STAT_TEXT.contains(SUB_STAT_TEXT) ))
    }


    override fun setSubStat(runeStars : Int, subStatValue: Int ) : SubStat {
        var testingSub : SubStat.Sub

        when(runeStars){
            1 -> testingSub = SubResistanceOne(subStatValue) ;
            2 -> testingSub = SubResistanceTwo(subStatValue);
            3 -> testingSub = SubResistanceThree(subStatValue);
            4 -> testingSub = SubResistanceFour(subStatValue);
            5 -> testingSub = SubResistanceFive(subStatValue);
            6 -> testingSub = SubResistanceSix(subStatValue);
            else ->{
                testingSub = Sub(0)
            }
        }

        if (SUB.ACTUAL_STAT != 0 || subStatValue > testingSub.MAX_STAT ) return this

        SUB = testingSub
        return this
    }




    open inner class SubResistanceOne(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 2
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE



    }


    open inner class SubResistanceTwo(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 3
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE




    }


    open inner class SubResistanceThree(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 2
        override var MAX_PROC = 4
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE


    }


    open inner class SubResistanceFour(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 2
        override var MAX_PROC = 5
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE


    }


    open inner class SubResistanceFive(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 3
        override var MAX_PROC = 7
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE

    }


    open inner class SubResistanceSix(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 4
        override var MAX_PROC = 8
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE

    }






}