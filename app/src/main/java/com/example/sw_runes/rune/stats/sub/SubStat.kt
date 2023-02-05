package com.example.sw_runes.sw.rune.stats.sub

import com.example.sw_runes.rune.RuneRarity
import com.example.sw_runes.sw.rune.RuneStar
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat

open  class SubStat{

    companion object{
        val Nan = -1
        val PROC_NONE = 0
        val PROC_ONCE = 1
        val PROC_TWICE = 2
        val PROC_TRIPLE = 3
        val PROC_QUAD = 4
    }

    open var SUB : Sub = Sub(0)
    open var SUB_STAT_TEXT = ""
    open var SECONDARY_STAT_TEXT = ""
    open var POIDS_DEFAULT : Float = 0f
    open var POIDS_DEFINI : Float = 0f



    open  fun setSubStat(runeStars : Int, subStatValue: Int ) :SubStat  {

        return SubStat()

    }

    open fun checkSubStat(stringVal : String, primaryStat: PrimaryStat):Boolean  {

        return false

    }



    open inner class  Sub(statValue : Int) {




        open var STARS = RuneStar.NaN
        open var ACTUAL_STAT = 0

        open var PROC_NUMBER = Nan
        open var MIN_PROC_NUMBER = 0
        open var MAX_PROC_NUMBER = 0
        open var MIN_PROC = 0
        open var MAX_PROC = 0

        init {
            if (statValue == 0  )
                ACTUAL_STAT = Nan
            else
                ACTUAL_STAT = statValue
        }

        var INATE_STAT = false;

        var PROC_STABLE = false
        var MIN_EFFICIENCY = 0f
        var MAX_EFFICIENCY = 0f

        var IS_MEULED = false
        var IS_MAX_PROC = false

        var MIN_STAT = MIN_PROC
        var MAX_STAT = MAX_PROC + (MAX_PROC * PROC_QUAD)

        open fun setSubWithLevel(runeLevel: Int, remainingProc: Int){



                PROC_NUMBER = setActualProc(remainingProc,false)

                var minProc = setActualProc(remainingProc,false)
                var maxProc = setActualProc(RuneRarity.LEGENDARY,true)

                if (minProc == maxProc)
                    PROC_STABLE = true;

                if (PROC_NUMBER != Nan){

                    setMinEfficiency(maxProc,minProc)

                }







        }

        open fun checkNotAccurateSub( remainingProc: Int, isLastSub : Boolean){

            MAX_PROC_NUMBER = setActualProc(RuneRarity.LEGENDARY,true)

            var minProc = setActualProc(remainingProc + MIN_PROC_NUMBER,false)
            var maxProc = setActualProc(MAX_PROC_NUMBER,true)


            MIN_PROC_NUMBER = minProc;

            if (minProc == maxProc){
                PROC_NUMBER = minProc
                PROC_STABLE = true
                setMinEfficiency(PROC_NUMBER,PROC_NUMBER)
            }else{

                IS_MAX_PROC = true

                if (minProc < remainingProc && maxProc > remainingProc)
                    IS_MEULED = true

                setMinEfficiency(maxProc,minProc)
            }

        }


        open fun setSubMaxEfficiency( remainingProc: Int ){

            var maxCaped = false

            var minProc = setActualProc(MIN_PROC_NUMBER +remainingProc ,false)
            var maxProc = setActualProc(MIN_PROC_NUMBER +remainingProc ,true)
            var extremProc = setActualProc(MIN_PROC_NUMBER + RuneRarity.LEGENDARY ,true)

            setMinEfficiency(maxProc,maxProc)

/*
            if (minProc == MIN_PROC_NUMBER +remainingProc){
                setMinEfficiency(minProc,minProc)
            }else  if (maxProc == MIN_PROC_NUMBER +remainingProc){
                if (minProc > 0)
                    setMinEfficiency(maxProc,minProc)
                else
                    setMinEfficiency(maxProc,maxProc)
            } else{
                if (minProc > 0 && maxProc > 0)
                    setMinEfficiency(maxProc,minProc)
                else
                {
                  setMinEfficiency(setActualProc(MIN_PROC_NUMBER + RuneRarity.LEGENDARY ,true),setActualProc(MIN_PROC_NUMBER +RuneRarity.LEGENDARY ,false))
                }
            }
*/

        }



        fun setActualProc(remainingProc: Int, startFromTop : Boolean):Int{
            if (remainingProc == 0){
                return PROC_NONE
            }
            var numberOfProc = PROC_NONE

            if (startFromTop){
                for (index in remainingProc downTo  0 ){
                    if (ACTUAL_STAT >= (MIN_PROC + MIN_PROC*index) && ACTUAL_STAT <= (MAX_PROC + MAX_PROC*index)){
                        numberOfProc = index
                        break
                    }
                }
            }else{
                for (index in 0..remainingProc){
                    if (ACTUAL_STAT >= (MIN_PROC + MIN_PROC*index) && ACTUAL_STAT <= (MAX_PROC + MAX_PROC*index)){
                        numberOfProc = index
                        break
                    }
                }
            }


            return numberOfProc
        }



        fun setMinEfficiency(procNumberLowest : Int,procNumberHightest: Int){
            var MIN_EFFICIENCY_LOW = (MIN_PROC +(procNumberLowest) *MIN_PROC  ).toFloat()
            var MAX_EFFICIENCY_LOW = (MAX_PROC +(procNumberLowest) *MAX_PROC  ).toFloat()
            MIN_EFFICIENCY = ( (ACTUAL_STAT.toFloat() - MIN_EFFICIENCY_LOW) / (MAX_EFFICIENCY_LOW - MIN_EFFICIENCY_LOW))

            var MIN_EFFICIENCY_HIGH = (MIN_PROC +(procNumberHightest) *MIN_PROC  ).toFloat()
            var MAX_EFFICIENCY_HIGH = (MAX_PROC +(procNumberHightest) *MAX_PROC  ).toFloat()
            MAX_EFFICIENCY = ( (ACTUAL_STAT.toFloat() - MIN_EFFICIENCY_HIGH) / (MAX_EFFICIENCY_HIGH - MIN_EFFICIENCY_HIGH))


        }




    }


}