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
        return (!stringVal.contains("Set") && stringVal.contains(SUB_STAT_TEXT) && !stringVal.contains("%"))
    }


    override fun setSubStat(runeStars : Int, subStatValue: Int ) : SubStat {

        if (SUB.ACTUAL_STAT == 0)
            return this

        when(runeStars){
            1 ->  SUB = SubAttackFlatOne(subStatValue) ;
            2 -> SUB = SubAttackFlatTwo(subStatValue);
            3 -> SUB = SubAttackFlatThree(subStatValue);
            4 -> SUB = SubAttackFlatFour(subStatValue);
            5 -> SUB = SubAttackFlatFive(subStatValue);
            6 -> SUB = SubAttackFlatSix(subStatValue);
            else ->{
                SUB = Sub(0)
            }
        }



        return this
    }




    open inner class SubAttackFlatOne(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 4




    }


    open inner class SubAttackFlatTwo(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 2
        override var MAX_PROC = 5

    }


    open inner class SubAttackFlatThree(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 3
        override var MAX_PROC = 8



    }


    open inner class SubAttackFlatFour(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 4
        override var MAX_PROC = 10



    }


    open inner class SubAttackFlatFive(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 8
        override var MAX_PROC = 15



    }


    open inner class SubAttackFlatSix(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 10
        override var MAX_PROC = 20


    }






}