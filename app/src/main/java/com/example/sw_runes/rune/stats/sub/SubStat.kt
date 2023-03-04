package com.example.sw_runes.sw.rune.stats.sub

import com.example.sw_runes.rune.rarity.*
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


       fun  isInnateStat(substats : List<SubStat> , _rarity : Rarity):Boolean{

           val substatsLength  = substats.count()

           if (substatsLength <0 || substatsLength > 5)
               throw Exception("Too many substats founds : ${substatsLength}.")

           if (substatsLength == 0)
                return false

           if (substatsLength == 5)
               return true


           if (_rarity.NUMBER == RarityNormal().NUMBER)
           {
                when(substatsLength){

                    0 -> return false
                    1 -> return true

                }
           }

           if (_rarity.NUMBER == RarityMagique().NUMBER){


               when(substatsLength){
                   1 -> return false
                   2 -> return true
               }

           }

           if (_rarity.NUMBER == RarityRare().NUMBER){

               when(substatsLength){
                   2 -> return false
                   3 -> return true
               }
           }

           if (_rarity.NUMBER == RarityHeroique().NUMBER){

               when(substatsLength){
                   3 -> return false
                   4 -> return true
               }

           }

           if (_rarity.NUMBER == RarityLegendaire().NUMBER){

               when(substatsLength){
                   4 -> return false
                   5 -> return true
               }

           }

           throw Exception("Substat error with rarity.")

       }

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

        open var MIN_PROC = 0
        open var MAX_PROC = 0

        open var MAX_MEULE = 0
        open var MIN_STAT = 0
        open var MAX_STAT = 0
        init {

            ACTUAL_STAT = statValue

        }




        open fun getPoidsCalculated():Int{
            if (ACTUAL_STAT <= 0)
                return 0

            return (ACTUAL_STAT * POIDS_DEFAULT * POIDS_DEFINI).toInt()
        }




    }


}