package com.example.myapplication.sw.rune

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.SparseArray
import androidx.annotation.RequiresApi
import com.example.myapplication.sw.rune.emplacement.Emplacement
import com.example.myapplication.sw.rune.stats.primary.PrimaryStat
import com.example.myapplication.sw.rune.stats.sub.SubStat
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer

class Rune()  {

    private var runeRarity : Int = RuneRarity.NaN
    private var runeStar : RuneStar = RuneStar(0)
    private var runeType : Int = RuneType.NaN
    private var runeEmplacement: Emplacement = Emplacement()
    private var runeMainStat : PrimaryStat = PrimaryStat()
    private var runeInnateStat : SubStat = SubStat()
    private var runeSubStats : Array<SubStat> = emptyArray()
    var runeSubStatsNotAccurate : Array<SubStat> = emptyArray()
    private var runeLevel : Int = RuneLevel.ZERO
    private var runeRemainingProc = SubStat.Nan
    var listOfpossibleDefaultGrade : Array<Int> = emptyArray()
    var baseRuneDefaultGrade = RuneRarity.NaN
    var runeDefaultGrade = RuneRarity.NaN
    var IS_DEFAULT_GRADE_FIXED = false
    var IS_VIABLE = false

    private lateinit var textBlocks : SparseArray<TextBlock>
    private lateinit var textBlocksStrings : Array<String>
    private lateinit var bitmap : Bitmap

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setRuneBaseStats(){

        val stringList : MutableList<String> = arrayListOf()

        for (i in 0 until textBlocks.size()) {


            val textBlock = textBlocks[textBlocks.keyAt(i)]


            if (textBlock.value.contains('(') && textBlock.value.contains("Rune") && textBlock.value.contains(')')){

                if (RuneEmplacement.setRuneEmplacement(textBlock.value).NUMBER != RuneEmplacement.NaN)
                    runeEmplacement = RuneEmplacement.setRuneEmplacement(textBlock.value)

                if (RuneLevel.setRuneLevel(textBlock.value) != RuneLevel.Nan && runeLevel != RuneLevel.Nan){
                    runeLevel = RuneLevel.setRuneLevel(textBlock.value)

                    if (runeLevel >= RuneLevel.THREE && runeLevel < RuneLevel.SIX){
                        runeRemainingProc = SubStat.PROC_ONCE
                    }else if (runeLevel >= RuneLevel.SIX && runeLevel < RuneLevel.NINE){
                        runeRemainingProc = SubStat.PROC_TWICE
                    }else if (runeLevel >= RuneLevel.NINE && runeLevel < RuneLevel.TWELVE){
                        runeRemainingProc = SubStat.PROC_TRIPLE
                    }else if (runeLevel >= RuneLevel.TWELVE && runeLevel <= RuneLevel.FIFTEEN){
                        runeRemainingProc = SubStat.PROC_QUAD
                    }else{
                        runeRemainingProc = SubStat.PROC_NONE
                    }

                }


                if (RuneRarity.setRuneRarity(textBlock.value, textBlock.boundingBox, bitmap) != RuneRarity.NaN){
                    runeRarity = RuneRarity.setRuneRarity(textBlock.value, textBlock.boundingBox, bitmap)
                }

            }
            stringList.add(textBlock.value)
        }
        textBlocksStrings = stringList.toTypedArray()
    }

    private fun setRunePrimaryStat(){
        IS_VIABLE = true
        textBlocksStrings.forEach {  textBlocksValue->



            runeEmplacement.AVAILABLE_MAIN_STATS.forEach {

                if (textBlocksValue.contains("\n")){
                    var textvalueSplit = textBlocksValue.split("\n");
                    textvalueSplit.forEach {value ->
                        if (it.checkPrimaryStat(value)){

                            if (runeStar.NUMBER == RuneStar.NaN)
                                runeStar = RuneStar(it.setStarByPrimaryStat(runeLevel,value))

                            if (runeMainStat.PRIMARY.ACTUAL_STAT == 0)
                                runeMainStat = it.setPrimaryStat(runeStar.NUMBER,value)

                        }
                    }
                }else{

                    if (it.checkPrimaryStat(textBlocksValue)){

                        if (runeStar.NUMBER == RuneStar.NaN)
                            runeStar = RuneStar(it.setStarByPrimaryStat(runeLevel,textBlocksValue))

                        if (runeMainStat.PRIMARY.ACTUAL_STAT == 0)
                            runeMainStat = it.setPrimaryStat(runeStar.NUMBER,textBlocksValue)

                    }
                }


            }
        }
    }

    private fun setRuneSubStats() {
        textBlocksStrings.forEach { textBlocksValue ->


            if (textBlocksValue.contains("\n")) {
                var textvalueSplit = textBlocksValue.split("\n");
                textvalueSplit.forEach { value ->
                    val subGot: SubStat? = SnipeSub(value, runeSubStats)
                    subGot?.let {
                        runeSubStats += arrayOf(subGot)
                    }
                }
            } else {
                val subGot: SubStat? = SnipeSub(textBlocksValue, runeSubStats)
                subGot?.let {
                    runeSubStats += arrayOf(subGot)
                }
            }

        }



        runeRemainingProc = runeRarity - 1;
        runeSubStats.forEach { subChoosed ->
            subChoosed.SUB.setSubWithLevel(runeLevel, runeRemainingProc)


            if (!subChoosed.SUB.PROC_STABLE)
                runeSubStatsNotAccurate += subChoosed
            else
                runeRemainingProc -= subChoosed.SUB.PROC_NUMBER


        }


        if (runeSubStatsNotAccurate.size == 1) {
            runeSubStatsNotAccurate[0].SUB.checkNotAccurateSub(runeRemainingProc, true)
            runeRemainingProc -=  runeSubStatsNotAccurate[0].SUB.MIN_PROC_NUMBER
            if (runeSubStatsNotAccurate[0].SUB.PROC_STABLE){

                runeSubStatsNotAccurate = emptyArray()
            }

        }

        else if (runeSubStatsNotAccurate.size > 1){

            runeSubStatsNotAccurate.forEach { subChoosed ->
                subChoosed.SUB.checkNotAccurateSub(runeRemainingProc, false)
                runeRemainingProc -=  subChoosed.SUB.MIN_PROC_NUMBER
            }
        }

        else{
            runeSubStatsNotAccurate = emptyArray()
        }


        if ((runeRarity-1) == runeRemainingProc){
            runeDefaultGrade = runeRarity
            baseRuneDefaultGrade= runeRarity
        }

        else{
            runeDefaultGrade = RuneRarity.LEGENDARY - runeRemainingProc
            baseRuneDefaultGrade= RuneRarity.LEGENDARY - runeRemainingProc
        }



    }


    fun updateSub(runeDfaultGrade : Int) {

        var bluredProc = 0
        bluredProc += (runeRemainingProc + runeDfaultGrade - baseRuneDefaultGrade)

       /* if (runeSubStatsNotAccurate.size != 0)
            bluredProc =  bluredProc.div(runeSubStatsNotAccurate.size)

        if ( runeDfaultGrade != runeDefaultGrade)
            bluredProc += procAdded
*/
        if (runeSubStatsNotAccurate.size == 1) {
            runeSubStatsNotAccurate[0].SUB.setSubMaxEfficiency(bluredProc)
        }

        else if (runeSubStatsNotAccurate.size > 1){

            runeSubStatsNotAccurate.forEach { subChoosed ->
                subChoosed.SUB.setSubMaxEfficiency(bluredProc)
            }

        }

        runeDefaultGrade = runeDfaultGrade


    }


    fun SnipeSub(value:String, subAlreadyExist : Array<SubStat>):SubStat?{

        var  substatsAvailable : MutableList<SubStat> = runeEmplacement.AVAILABLE_SUB_STATS
        substatsAvailable.removeAll(subAlreadyExist)
        substatsAvailable.forEach { subAvailable ->

            if (subAvailable.checkSubStat(value, runeMainStat)){


                var substat =  subAvailable.setSubStat(runeStar.NUMBER,value,runeMainStat)

                var isAlreadyIn = false
                if (runeMainStat.PRIMARY_STAT_TEXT == substat.SUB_STAT_TEXT &&  substat.SECONDARY_STAT_TEXT == runeMainStat.SECONDARY_STAT_TEXT)
                    isAlreadyIn = true


                if (substat.SUB.ACTUAL_STAT != SubStat.Nan && !isAlreadyIn){

                    return substat
                }
            }

        }
        return null

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setRune(context: Context, _bitmap: Bitmap){


        val textRecognizer = TextRecognizer.Builder(context).build()

        val imageFrame: Frame = Frame.Builder()
            .setBitmap(_bitmap) // your image bitmap
            .build()

        var imageText = ""
        textBlocks = textRecognizer.detect(imageFrame)
        bitmap = _bitmap

        setRuneBaseStats()

        if(runeEmplacement.NUMBER != RuneEmplacement.NaN && runeRarity!=RuneRarity.NaN && runeEmplacement.NUMBER != RuneEmplacement.NaN && runeLevel!=RuneLevel.Nan){
            setRunePrimaryStat()
            setRuneSubStats()
            if (runeSubStats.size ==runeRarity){
                var innate =  runeSubStats.slice(0..0)
                runeSubStats.drop(1)
                runeInnateStat = innate[0]
            }
        }

        updateSub(runeDefaultGrade)
        var subsStable = true
        runeSubStats.forEach {
            if(!it.SUB.PROC_STABLE)
                subsStable = false
        }

        if (runeRemainingProc > 0 && !subsStable){
            when(runeDefaultGrade){
                RuneRarity.HERO -> listOfpossibleDefaultGrade = arrayOf(RuneRarity.LEGENDARY , RuneRarity.HERO)
                RuneRarity.RARE -> listOfpossibleDefaultGrade = arrayOf(RuneRarity.LEGENDARY, RuneRarity.HERO,RuneRarity.RARE)
                RuneRarity.MAGIC -> listOfpossibleDefaultGrade = arrayOf(RuneRarity.LEGENDARY, RuneRarity.HERO,RuneRarity.RARE,RuneRarity.MAGIC)
                RuneRarity.NORMAL -> listOfpossibleDefaultGrade = arrayOf(RuneRarity.LEGENDARY, RuneRarity.HERO,RuneRarity.RARE,RuneRarity.MAGIC,RuneRarity.NORMAL)
                else -> {
                    listOfpossibleDefaultGrade = arrayOf(RuneRarity.LEGENDARY, RuneRarity.HERO,RuneRarity.RARE,RuneRarity.MAGIC,RuneRarity.NORMAL)
                }
            }
        }else{
            listOfpossibleDefaultGrade = arrayOf(runeDefaultGrade)
        }





        bitmap!!.recycle()

    }

    fun getRuneRarity(): String {

        return RuneRarity.getRuneRarity(runeRarity)

    }

    fun getRuneRarityColor(): String {

        return RuneRarity.getRuneRarityColor(runeRarity)

    }


    fun getRuneEmplacement(): Emplacement {

        return runeEmplacement

    }





    fun getRunePrimaryStat(): PrimaryStat {

        return runeMainStat

    }

    fun getRuneLevel():Int{
        return runeLevel;
    }

    fun getRuneStars():RuneStar{
        return runeStar;
    }
    fun getRuneSubStats():Array<SubStat>{
        if (runeSubStats.size ==runeRarity){
            return runeSubStats.drop(1).toTypedArray()
        }else{
            return runeSubStats
        }
    }

    fun getRuneInnateStat():SubStat{
        return runeInnateStat;
    }

    fun getMinViability():Float{

        var medium : Float = 0f
        var totalProcs = 0

        runeSubStats.forEach {

            if (it.SUB.PROC_NUMBER != SubStat.Nan){

                medium += (it.SUB.PROC_NUMBER +1) * it.SUB.MIN_EFFICIENCY
                totalProcs += it.SUB.PROC_NUMBER

            }
        }

        medium = medium / (totalProcs+runeSubStats.size) *100

        if (runeInnateStat.SUB.STARS != RuneStar.NaN)
            medium   += (runeInnateStat.SUB.MAX_EFFICIENCY /(totalProcs+runeSubStats.size) *100 )



        return medium;
    }


    fun getMaxViability():Float{

        var medium : Float = 0f
        var totalProcs = 0

        runeSubStats.forEach {

            if (it.SUB.PROC_NUMBER != SubStat.Nan){

                medium += (it.SUB.PROC_NUMBER +1) * it.SUB.MAX_EFFICIENCY
                totalProcs += it.SUB.PROC_NUMBER


            }
        }

        medium = medium / (totalProcs+runeSubStats.size) *100

        if (runeInnateStat.SUB.STARS != RuneStar.NaN)
            medium   += (runeInnateStat.SUB.MAX_EFFICIENCY /(totalProcs+runeSubStats.size) *100 )





        return medium;
    }



}