package com.example.myapplication.sw.rune.stats.sub

import com.example.myapplication.sw.rune.RuneLevel
import com.example.myapplication.sw.rune.RuneStar
import com.example.myapplication.sw.rune.emplacement.*
import com.example.myapplication.sw.rune.stats.primary.PrimaryStat

class SubHealthFlat: SubStat() {


    override var SUB: Sub = Sub(0)
    override var SUB_STAT_TEXT = "PV"
    override var SECONDARY_STAT_TEXT = ""

    override fun checkSubStat(stringVal : String, primaryStat: PrimaryStat ):Boolean {
        return (stringVal.contains(SUB_STAT_TEXT) && !stringVal.contains("%") )
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
                    1 ->  SUB = SubHealthFlatOne(value) ;
                    2 -> SUB = SubHealthFlatTwo(value);
                    3 -> SUB = SubHealthFlatThree(value);
                    4 -> SUB = SubHealthFlatFour(value);
                    5 -> SUB = SubHealthFlatFive(value);
                    6 -> SUB = SubHealthFlatSix(value);
                    else ->{
                        SUB = Sub(0)
                    }
                }
            }else{
                SUB =  Sub(0)
            }

        

        return this
    }




    open inner class SubHealthFlatOne(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.ONE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 15
        override var MAX_PROC = 60




    }


    open inner class SubHealthFlatTwo(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.TWO
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 30
        override var MAX_PROC = 105




    }


    open inner class SubHealthFlatThree(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.THREE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 45
        override var MAX_PROC = 165



    }


    open inner class SubHealthFlatFour(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FOUR
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 60
        override var MAX_PROC = 225



    }


    open inner class SubHealthFlatFive(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.FIVE
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 90
        override var MAX_PROC = 300



    }


    open inner class SubHealthFlatSix(statValue: Int) : SubStat.Sub(statValue){
        override var STARS = RuneStar.SIX
        override var ACTUAL_STAT = 0

        override var MIN_PROC = 135
        override var MAX_PROC = 375


    }






}