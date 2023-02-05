package com.example.sw_runes.sw.rune.emplacement

import com.example.sw_runes.R
import com.example.sw_runes.sw.rune.stats.primary.AttackFlat
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat
import com.example.sw_runes.sw.rune.stats.sub.*

class EmplacementOne : Emplacement() {

    override var NUMBER = 1
    override var TRADUCTION = "Emplacement "+NUMBER
    override var AVAILABLE_MAIN_STATS : MutableList<PrimaryStat> = mutableListOf(AttackFlat())
    override var AVAILABLE_SUB_STATS : MutableList<SubStat> = mutableListOf(SubSpeed(),
        SubAccuracy(),
        SubResistance(),SubAttackPercent(),SubDefencePercent(),SubHealthPercent(),SubCritiqDamage(),
        SubCritiqRate(),SubDefenceFlat(),SubHealthFlat()
    )
    override var RUNE_DRAWABLE: Int = R.drawable.rune_emplacement_one
}