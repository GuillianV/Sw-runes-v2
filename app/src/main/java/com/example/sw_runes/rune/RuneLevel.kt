package com.example.myapplication.sw.rune

class RuneLevel {

    companion object{

        val Nan = -1
        val ZERO = 0
        val ONE = 1
        val TWO = 2
        val THREE = 3
        val FOUR = 4
        val FIVE = 5
        val SIX = 6
        val SEVEN = 7
        val HEIGHT = 8
        val NINE = 9
        val TEN = 10
        val ELEVEN = 11
        val TWELVE = 12
        val THIRTEEN = 13
        val FOURTEEN = 14
        val FIFTEEN = 15

        fun setRuneLevel(string: String): Int {

                 string.let {

                     val nonNumber = "[^0-9]".toRegex()
                     var value = string.substringBefore("Rune")
                     val stringLevel = value.replace(nonNumber, "")
                     val level = (stringLevel.toIntOrNull() ?: Nan)

                     return level

                 }


        }


    }

}