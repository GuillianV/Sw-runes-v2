package com.example.sw_runes.sw.rune.emplacement

import com.example.sw_runes.R
import com.example.sw_runes.sw.rune.stats.primary.*
import com.example.sw_runes.sw.rune.stats.sub.*

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
    override var RUNE_IMAGE: Int = R.drawable.rune_emplacement_one
}