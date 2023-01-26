package com.example.myapplication.sw.rune.emplacement

import com.example.myapplication.R
import com.example.myapplication.sw.rune.stats.primary.*
import com.example.myapplication.sw.rune.stats.sub.*

class EmplacementFour : Emplacement() {

    override var NUMBER = 4
    override var TRADUCTION = "Emplacement "+NUMBER
    override var AVAILABLE_MAIN_STATS : MutableList<PrimaryStat> = mutableListOf(AttackFlat(),
        AttackPercent(),
        DefenceFlat(),
        HealthFlat(),
        DefencePercent(),
        HealthPercent(),
        CritiqRate(),
        CritiqDamage()
    )
    override var AVAILABLE_SUB_STATS : MutableList<SubStat> = mutableListOf(SubSpeed(),
        SubAccuracy(),
        SubResistance(),SubAttackPercent(),SubDefencePercent(),SubHealthPercent(),SubCritiqDamage(),
        SubCritiqRate(),SubAttackFlat(),SubDefenceFlat(),SubHealthFlat()
    )
    override var RUNE_IMAGE: Int = R.drawable.rune_emplacement_four
}