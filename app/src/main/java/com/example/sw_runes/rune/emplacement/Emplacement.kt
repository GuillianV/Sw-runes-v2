package com.example.myapplication.sw.rune.emplacement

import com.example.myapplication.R
import com.example.myapplication.sw.rune.stats.primary.AttackFlat
import com.example.myapplication.sw.rune.stats.primary.PrimaryStat
import com.example.myapplication.sw.rune.stats.sub.SubStat

open class Emplacement {
    open var NUMBER = 0
    open var TRADUCTION = "Emplacement Non trouvé"
    open val AVAILABLE_MAIN_STATS : MutableList<PrimaryStat> = emptyList<PrimaryStat>().toMutableList()
    open val AVAILABLE_SUB_STATS : MutableList<SubStat> = emptyList<SubStat>().toMutableList()
    open var RUNE_IMAGE = R.drawable.rune_emplacement_nan
}