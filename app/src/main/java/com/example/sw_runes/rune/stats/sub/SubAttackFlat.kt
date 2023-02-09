package com.example.sw_runes.sw.rune.stats.sub

import com.example.sw_runes.sw.rune.RuneLevel
import com.example.sw_runes.sw.rune.RuneStar
import com.example.sw_runes.sw.rune.emplacement.*
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat

class SubAttackFlat: SubStat() {


    override var SUB: Sub = Sub(0)
    override var SUB_STAT_TEXT = "ATQ"
    override var SECONDARY_STAT_TEXT = ""

    override var POIDS_DEFAULT : Float = 20f
    override var POIDS_DEFINI : Float = 0.4f
override fun checkSubStat(stringVal : String, primaryStat: PrimaryStat ):Boolean {
        return (!stringVal.contains("Set") && (stringVal.contains(SUB_STAT_TEXT) && !stringVal.contains("%")))
    }


    override fun setSubStat(runeStars : Int, subStatValue: Int ) : SubStat {

        var testingSub : SubStat.Sub

        when(runeStars){
            1 -> testingSub = SubAttackFlatOne(subStatValue) ;
            2 -> testingSub = SubAttackFlatTwo(subStatValue);
            3 -> testingSub = SubAttackFlatThree(subStatValue);
            4 -> testingSub = SubAttackFlatFour(subStatValue);
            5 -> testingSub = SubAttackFlatFive(subStatValue);
            6 -> testingSub = SubAttackFlatSix(subStatValue);
            else ->{
                testingSub = Sub(0)
            }
        }

        if (SUB.ACTUAL_STAT != 0 || subStatValue > testingSub.MAX_STAT ) return this

        SUB = testingSub
        return this
    }




    open inner class SubAttackFlatOne(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 4

        override var MAX_MEULE = 30
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE


    }


    open inner class SubAttackFlatTwo(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 2
        override var MAX_PROC = 5
        override var MAX_MEULE = 30
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE
    }


    open inner class SubAttackFlatThree(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 3
        override var MAX_PROC = 8
        override var MAX_MEULE = 30
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE


    }


    open inner class SubAttackFlatFour(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 4
        override var MAX_PROC = 10
        override var MAX_MEULE = 30
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE


    }


    open inner class SubAttackFlatFive(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 8
        override var MAX_PROC = 15
        override var MAX_MEULE = 30
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE


    }


    open inner class SubAttackFlatSix(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 10
        override var MAX_PROC = 20
        override var MAX_MEULE = 30
        override var MIN_STAT = MIN_PROC
        override var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD ) +  MAX_MEULE

    }






}