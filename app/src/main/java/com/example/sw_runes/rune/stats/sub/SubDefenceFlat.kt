package com.example.myapplication.sw.rune.stats.sub

import com.example.myapplication.sw.rune.RuneLevel
import com.example.myapplication.sw.rune.RuneStar
import com.example.myapplication.sw.rune.emplacement.*
import com.example.myapplication.sw.rune.stats.primary.PrimaryStat

class SubDefenceFlat: SubStat() {


    override var SUB: Sub = Sub(0)
    override var SUB_STAT_TEXT = "DEF"
    override var SECONDARY_STAT_TEXT = ""

    override fun checkSubStat(stringVal : String, primaryStat: PrimaryStat ):Boolean {
        return (stringVal.contains(SUB_STAT_TEXT) && !stringVal.contains("%"))
    }


    override fun setSubStat(runeStars : Int, stringVal: String, primaryStat: PrimaryStat ) : SubStat {

        if (!checkSubStat(stringVal,primaryStat)){
            return  this
        }
        val nonNumber = "[^0-9]".toRegex()
        var value : Int? = null
        if (!stringVal.contains("Set"))
           value = stringVal.substringAfter(SUB_STAT_TEXT).replace(nonNumber, "").toIntOrNull()



        if (value != null){
                when(runeStars){
                    1 ->  SUB = SubDefenceFlatOne(value) ;
                    2 -> SUB = SubDefenceFlatTwo(value);
                    3 -> SUB = SubDefenceFlatThree(value);
                    4 -> SUB = SubDefenceFlatFour(value);
                    5 -> SUB = SubDefenceFlatFive(value);
                    6 -> SUB = SubDefenceFlatSix(value);
                    else ->{
                        SUB = Sub(0)
                    }
                }
            }else{
                SUB =  Sub(0)
            }

        

        return this
    }




    open inner class SubDefenceFlatOne(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 1
        override var MAX_PROC = 4




    }


    open inner class SubDefenceFlatTwo(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 2
        override var MAX_PROC = 5

    }


    open inner class SubDefenceFlatThree(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 3
        override var MAX_PROC = 8



    }


    open inner class SubDefenceFlatFour(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 4
        override var MAX_PROC = 10



    }


    open inner class SubDefenceFlatFive(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 8
        override var MAX_PROC = 15



    }


    open inner class SubDefenceFlatSix(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 10
        override var MAX_PROC = 20


    }






}