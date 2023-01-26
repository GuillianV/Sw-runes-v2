package com.example.myapplication.sw.rune.emplacement

import com.example.myapplication.R
import com.example.myapplication.sw.rune.stats.primary.*
import com.example.myapplication.sw.rune.stats.sub.*

class EmplacementTwo : Emplacement() {

    override var NUMBER = 2
    override var TRADUCTION = "Emplacement "+NUMBER
    override var AVAILABLE_MAIN_STATS : MutableList<PrimaryStat> = mutableListOf(AttackFlat(),AttackPercent(),HealthFlat(),
        DefenceFlat(),
        DefencePercent(),
        HealthPercent(),
        Speed()
    )
    override var AVAILABLE_SUB_STATS : MutableList<SubStat> = mutableListOf(SubSpeed(),SubAccuracy(),SubResistance(),SubAttackPercent(),SubDefencePercent(),SubHealthPercent(),SubCritiqDamage(),SubCritiqRate(),SubAttackFlat(),SubDefenceFlat(),SubHealthFlat())
    override var RUNE_IMAGE: Int = R.drawable.rune_emplacement_two
}