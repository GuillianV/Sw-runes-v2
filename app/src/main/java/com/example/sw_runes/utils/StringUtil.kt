package com.example.sw_runes.utils

import com.example.sw_runes.sw.rune.RuneLevel

class StringUtil {


    companion object{


        fun getOnlyNumber(text:String):Int?{
            text.let {

                val nonNumber = "[^0-9]".toRegex()
                return  it.replace(nonNumber, "").toIntOrNull()

            }
        }


    }

}