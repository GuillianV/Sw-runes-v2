package com.example.sw_runes.sw.rune.stats.sub

import com.example.sw_runes.sw.rune.RuneLevel
import com.example.sw_runes.sw.rune.RuneStar
import com.example.sw_runes.sw.rune.emplacement.*
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat

class SubHealthFlat: SubStat() {


    override var SUB: Sub = Sub(0)
    override var SUB_STAT_TEXT = "PV"
    override var SECONDARY_STAT_TEXT = ""

    override var POIDS_DEFAULT : Float = 1f
    override var POIDS_DEFINI : Float = 0.4f
override fun checkSubStat(stringVal : String, primaryStat: PrimaryStat ):Boolean {
        return (!stringVal.contains("Set") && (stringVal.contains(SUB_STAT_TEXT) && !stringVal.contains("%") ))
    }


    override fun setSubStat(runeStars : Int, subStatValue: Int ) : SubStat {
        var testingSub : SubStat.Sub

        when(runeStars){
            1 -> testingSub = SubHealthFlatOne(subStatValue) ;
            2 -> testingSub = SubHealthFlatTwo(subStatValue);
            3 -> testingSub = SubHealthFlatThree(subStatValue);
            4 -> testingSub = SubHealthFlatFour(subStatValue);
            5 -> testingSub = SubHealthFlatFive(subStatValue);
            6 -> testingSub = SubHealthFlatSix(subStatValue);
            else ->{
                testingSub = Sub(0)
            }
        }

        if (SUB.ACTUAL_STAT != 0 || subStatValue > testingSub.MAX_STAT ) return this

        SUB = testingSub
        return this
    }




    open inner class SubHealthFlatOne(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 15
        override var MAX_PROC = 60


        override var MAX_MEULE = 550
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE

    }


    open inner class SubHealthFlatTwo(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 30
        override var MAX_PROC = 105

        override var MAX_MEULE = 550
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE


    }


    open inner class SubHealthFlatThree(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 45
        override var MAX_PROC = 165

        override var MAX_MEULE = 550
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE

    }


    open inner class SubHealthFlatFour(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 60
        override var MAX_PROC = 225

        override var MAX_MEULE = 550
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE

    }


    open inner class SubHealthFlatFive(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 90
        override var MAX_PROC = 300

        override var MAX_MEULE = 550
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE

    }


    open inner class SubHealthFlatSix(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 135
        override var MAX_PROC = 375

        override var MAX_MEULE = 550
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE

    }






}