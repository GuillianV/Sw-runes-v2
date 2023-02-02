package com.example.sw_runes.rune

import android.graphics.Bitmap
import com.example.sw_runes.rune.rarity.*
import com.example.sw_runes.sw.rune.ImageUtils
import com.example.sw_runes.sw.rune.emplacement.*
import com.google.mlkit.vision.text.Text
import java.util.*

class RuneRarity {

    inner class RarityDefault{

    }

    companion object{

        val NaN = 0
        val NORMAL = 1
        val MAGIC = 2
        val RARE = 3
        val HERO = 4
        val LEGENDARY = 5


        val  rarityList : Array<Rarity> = arrayOf(
            RarityNormal(),
            RarityMagique(),
            RarityRare(),
            RarityHeroique(),
            RarityLegendaire(),
        )

        fun setRuneRarity(textblock : Text.TextBlock, bitmap: Bitmap):Rarity{

                if (textblock.boundingBox == null)
                    return Rarity()

                val threshold = 50

                for (y in textblock.boundingBox!!.top..textblock.boundingBox!!.bottom step 10){

                    for (x in textblock.boundingBox!!.left..textblock.boundingBox!!.right step 10){

                        rarityList.forEach {
                            if (ImageUtils.colorNearOf(
                                    Integer.toHexString(bitmap.getPixel(x, y)),
                                    it.COLOR,
                                    threshold
                                )
                            ){
                                it.compt++
                            }

                        }




                    }
                }

                var sortedlist = rarityList.sortedWith(compareByDescending {it.compt})
                sortedlist.let {
                        rarityList.map { it.compt = 0 }
                        return sortedlist[0]
                }

        }


        fun getRuneRarity(runeValue:Int):String{
            var lang : String = Locale.getDefault().getLanguage()
            when (runeValue) {
                NORMAL -> return "Normal"
                MAGIC  -> return "Magique"
                RARE   -> return "Rare"
                HERO  -> return "Héroique"
                LEGENDARY -> return "Légendaire"
                else -> {
                    return "Rareté non trouvé"
                }
            }

        }

    }




}