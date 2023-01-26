package com.example.myapplication.sw.rune.emplacement

import com.example.myapplication.R
import com.example.myapplication.sw.rune.stats.primary.AttackFlat
import com.example.myapplication.sw.rune.stats.primary.HealthFlat
import com.example.myapplication.sw.rune.stats.primary.PrimaryStat
import com.example.myapplication.sw.rune.stats.sub.*

class EmplacementFive : Emplacement() {

    override var NUMBER = 5
    override var TRADUCTION = "Emplacement "+NUMBER
    override var AVAILABLE_MAIN_STATS : MutableList<PrimaryStat> = mutableListOf(HealthFlat())
    override var AVAILABLE_SUB_STATS : MutableList<SubStat> = mutableListOf(SubSpeed(),
        SubAccuracy(),
        SubResistance(),SubAttackPercent(),SubDefencePercent(),SubHealthPercent(),SubCritiqDamage(),
        SubCritiqRate(),SubAttackFlat(),SubDefenceFlat()
    )
    override var RUNE_IMAGE: Int = R.drawable.rune_emplacement_five
}
