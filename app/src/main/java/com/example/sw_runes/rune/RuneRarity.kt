package com.example.sw_runes.sw.rune

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.sw_runes.R
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat
import java.util.*

class RuneRarity {

    companion object{

        val NaN = 0
        val NaN_COLOR = "#ff000000"
        val NORMAL = 1
        val NORMAL_COLOR = "#ffE7E7F3"
        val MAGIC = 2
        val MAGIC_COLOR = "#ff04BC54"
        val RARE = 3
        val RARE_COLOR = "#ff5BE4FF"
        val HERO = 4
        val HERO_COLOR = "#ffB773C3"
        val LEGENDARY = 5
        val LEGENDARY_COLOR = "#ffEFA74E"

        @RequiresApi(Build.VERSION_CODES.O)
        fun setRuneRarity(text:String, rect: Rect, bitmap: Bitmap):Int{


                val threshold = 50

                var normal_counter = 0
                var magic_counter = 0
                var rare_counter = 0
                var hero_counter = 0
                var legendary_counter = 0

                for (y in rect.top..rect.bottom step 10){
                    for (x in rect.left..rect.right){




                        if (ImageUtils.colorNearOf(Integer.toHexString(bitmap.getPixel(x, y)), NORMAL_COLOR,threshold)) {
                            normal_counter++
                        }

                        if (ImageUtils.colorNearOf(Integer.toHexString(bitmap.getPixel(x, y)), MAGIC_COLOR,threshold)) {
                            magic_counter++
                        }

                        if (ImageUtils.colorNearOf(Integer.toHexString(bitmap.getPixel(x, y)), RARE_COLOR,threshold)) {
                            rare_counter++
                        }

                        if (ImageUtils.colorNearOf(Integer.toHexString(bitmap.getPixel(x, y)), HERO_COLOR,threshold)) {
                            hero_counter++
                        }

                        if (ImageUtils.colorNearOf(Integer.toHexString(bitmap.getPixel(x, y)), LEGENDARY_COLOR,threshold)) {
                            legendary_counter++
                        }




                    }
                }

                if (normal_counter > magic_counter && normal_counter > rare_counter && normal_counter > hero_counter && normal_counter > legendary_counter )
                    return NORMAL
                else  if (magic_counter > normal_counter && magic_counter > rare_counter && magic_counter > hero_counter && magic_counter > legendary_counter )
                    return MAGIC
                else  if (rare_counter > normal_counter && rare_counter > magic_counter && rare_counter > hero_counter && rare_counter > legendary_counter )
                    return RARE
                else  if (hero_counter > normal_counter && hero_counter > magic_counter && hero_counter > rare_counter && hero_counter > legendary_counter )
                    return HERO
                else  if (legendary_counter > normal_counter && legendary_counter > magic_counter && legendary_counter > rare_counter && legendary_counter > hero_counter )
                    return LEGENDARY
                else
                    return NaN


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

        fun getRuneRarityColor(runeValue:Int):String{
            when (runeValue) {
                NORMAL -> return NORMAL_COLOR
                MAGIC  -> return MAGIC_COLOR
                RARE   -> return RARE_COLOR
                HERO  -> return HERO_COLOR
                LEGENDARY -> return LEGENDARY_COLOR
                else -> {
                    return NaN_COLOR
                }
            }

        }



    }




}