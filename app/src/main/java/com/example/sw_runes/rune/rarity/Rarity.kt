package com.example.sw_runes.rune.rarity

import com.example.sw_runes.R
import com.example.sw_runes.sw.rune.RuneLevel

open class Rarity {
    open var NUMBER = 0
    open var TRADUCTION = "Rareté Non trouvé"
    open var COLOR = "#ff000000"
    open var BACKGROUND_DRAWABLE = R.drawable.background_rarity_normal

    var compt = 0


    companion object{

       fun getProcsRemaining(_rarity : Rarity, _procsDone: Int):Int{

           if (_rarity.NUMBER == RarityNormal().NUMBER)
           {
               return 0
           }

           if (_rarity.NUMBER == RarityMagique().NUMBER){

               when(_procsDone){

                   0 -> return 1
                   1 -> return 0
                   else -> {
                        Exception("Erreur nombre de proc magique "+_procsDone)
                   }
               }

           }

           if (_rarity.NUMBER == RarityRare().NUMBER){

               when(_procsDone){
                   0 -> return 2
                   1 -> return 1
                   2 -> return 0
                   else -> {
                       Exception("Erreur nombre de proc rare "+_procsDone)
                   }
               }

           }

           if (_rarity.NUMBER == RarityHeroique().NUMBER){

               when(_procsDone){
                   0 -> return 3
                   1 -> return 2
                   2 -> return 1
                   3 -> return 0
                   else -> {
                       Exception("Erreur nombre de proc heroique "+_procsDone)
                   }
               }

           }

           if (_rarity.NUMBER == RarityLegendaire().NUMBER){

               when(_procsDone){
                   0 -> return 4
                   1 -> return 3
                   2 -> return 2
                   3 -> return 1
                   4 -> return 0
                   else -> {
                       Exception("Erreur nombre de proc legendaire "+_procsDone)
                   }
               }

           }

           Exception("Rareté non trouvé")
           return 0

       }

    }

}