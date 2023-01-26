package com.example.myapplication.sw.rune.emplacement

import com.example.myapplication.R
import com.example.myapplication.sw.rune.stats.primary.AttackFlat
import com.example.myapplication.sw.rune.stats.primary.DefenceFlat
import com.example.myapplication.sw.rune.stats.primary.PrimaryStat
import com.example.myapplication.sw.rune.stats.sub.*

class EmplacementThree : Emplacement() {

    override var NUMBER = 3
    override var TRADUCTION = "Emplacement "+NUMBER
    override var AVAILABLE_MAIN_STATS : MutableList<PrimaryStat> = mutableListOf(
        DefenceFlat()
    )
    override var AVAILABLE_SUB_STATS : MutableList<SubStat> = mutableListOf(SubSpeed(),
        SubAccuracy(),
        SubResistance(),SubAttackPercent(),SubDefencePercent(),
        SubHealthPercent(),SubCritiqDamage(),SubCritiqRate(),SubAttackFlat(),SubHealthFlat()
    )
    override var RUNE_IMAGE: Int = R.drawable.rune_emplacement_three
}