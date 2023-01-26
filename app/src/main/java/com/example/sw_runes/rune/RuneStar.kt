package com.example.myapplication.sw.rune

import com.example.myapplication.R

class RuneStar(star:Int) {

    var IMAGE = 0
    var NUMBER = 0

    init {
        NUMBER =star

        when(NUMBER){
            1->IMAGE = ONE_IMAGE
            2->IMAGE = TWO_IMAGE
            3->IMAGE = THREE_IMAGE
            4->IMAGE = FOUR_IMAGE
            5->IMAGE = FIVE_IMAGE
            6->IMAGE = SIX_IMAGE
            else ->{
                IMAGE = Nan_IMAGE
            }
        }
    }

    companion object{

        val NaN = 0
        val Nan_IMAGE = R.drawable.rune_star_nan
        val ONE = 1
        val ONE_IMAGE = R.drawable.rune_star_one
        val TWO = 2
        val TWO_IMAGE = R.drawable.rune_star_two
        val THREE = 3
        val THREE_IMAGE = R.drawable.rune_star_three
        val FOUR = 4
        val FOUR_IMAGE = R.drawable.rune_star_four
        val FIVE = 5
        val FIVE_IMAGE = R.drawable.rune_star_five
        val SIX = 6
        val SIX_IMAGE = R.drawable.rune_star_six
        val ANCIENT = 7

    }



}