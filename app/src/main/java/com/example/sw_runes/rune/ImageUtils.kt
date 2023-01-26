package com.example.myapplication.sw.rune

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

class ImageUtils {

    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        fun colorNearOf(colorHexa1: String,colorHexa2: String, threshold: Int): Boolean {

            var colorHexaa1 : String = colorHexa1
            if (!colorHexa1.contains('#')){
                colorHexaa1 = "#"+colorHexa1
            }

            var colorHexaa2 : String = colorHexa2
            if (!colorHexa2.contains('#')){
                colorHexaa2 = "#"+colorHexa2
            }



            val color = Color.parseColor(colorHexaa1)
            val otherColor = Color.parseColor(colorHexaa2)


            val m_r = color shr 16 and 0xFF
            val m_g = color shr 8 and 0xFF
            val m_b = color shr 0 and 0xFF


            val o_r = otherColor shr 16 and 0xFF
            val o_g = otherColor shr 8 and 0xFF
            val o_b = otherColor shr 0 and 0xFF

            val r: Int = m_r - o_r
            val g: Int = m_g - o_g
            val b: Int = m_b - o_b
            return r * r + g * g + b * b <= threshold * threshold

        }
    }



}