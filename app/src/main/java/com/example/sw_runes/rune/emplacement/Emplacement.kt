package com.example.sw_runes.sw.rune.emplacement

import com.example.sw_runes.R
import com.example.sw_runes.sw.rune.stats.primary.AttackFlat
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat
import com.example.sw_runes.sw.rune.stats.sub.SubStat

open class Emplacement {
    open var NUMBER = 0
    open var TRADUCTION = "Emplacement Non trouvé"
    open val AVAILABLE_MAIN_STATS : MutableList<PrimaryStat> = emptyList<PrimaryStat>().toMutableList()
    open val AVAILABLE_SUB_STATS : MutableList<SubStat> = emptyList<SubStat>().toMutableList()
    open var RUNE_IMAGE = R.drawable.rune_emplacement_nan
}