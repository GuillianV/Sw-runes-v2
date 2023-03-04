package com.example.sw_runes.sw.rune

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.sw_runes.enums.TapStatus
import com.example.sw_runes.fragments.RuneOptimisationFragment
import com.example.sw_runes.rune.RuneRarity
import com.example.sw_runes.rune.rarity.Rarity
import com.example.sw_runes.services.RuneAnalyzerService
import com.example.sw_runes.sw.rune.emplacement.Emplacement
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat
import com.example.sw_runes.sw.rune.stats.sub.SubStat
import com.example.sw_runes.utils.StringUtil
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.*
class Rune(_runeAnalyzerService : RuneAnalyzerService)  {

    var runeAnalyzerService: RuneAnalyzerService
    init {
        runeAnalyzerService = _runeAnalyzerService
    }

    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    lateinit var bitmap : Bitmap
    lateinit var runeOptimisationFragment : RuneOptimisationFragment
    var isViable : Boolean = false


    //Rune params
    var runeEmplacement : Emplacement = Emplacement()
    var runeLevel : Int = RuneLevel.Nan
    var runeRarity : Rarity = Rarity()
    var runeStar : RuneStar = RuneStar(0)
    //Rune stats
    var runePrimaryStat : PrimaryStat = PrimaryStat()
    var runeInnateStat : SubStat? = null
    var runeSubStats : List<SubStat> = arrayListOf()




     suspend fun setRune(_bitmap: Bitmap): Boolean = coroutineScope {
        bitmap = _bitmap
        val inputImage = InputImage.fromBitmap(_bitmap, 0)
        val visionTextTask = recognizer.process(inputImage)

        val visionText =  Tasks.await(visionTextTask)

        var textBlocksSorted: List<Text.TextBlock> = visionText.textBlocks.sortedWith(compareBy { it.boundingBox?.top })

        if (!setRuneBaseStats(textBlocksSorted) || !setPrimaryStat(textBlocksSorted)) {
            return@coroutineScope false
        } else {
            setSubStats(textBlocksSorted)
            setOtherStats();
            isViable = true



            return@coroutineScope true
        }
    }



    private fun setRuneBaseStats(textBlocks : List<Text.TextBlock>) : Boolean{

        // Set Rune emplacement - Level - Rarity
        for (index in 0..textBlocks.size - 1) {
            var text = textBlocks[index].text
            if (text.contains('(') && text.contains("Rune") && text.contains(')'))
                (text.substringAfter('(').substringBefore(')')).toIntOrNull()?.let {
                    if (it > 0 && it <= 6){
                        runeEmplacement = RuneEmplacement.setRuneEmplacement(it)
                        runeLevel = RuneLevel.setRuneLevel(text)
                        runeRarity = RuneRarity.setRuneRarity(textBlocks[index], bitmap)
                    }
                }

            if (runeEmplacement.NUMBER != RuneEmplacement.NaN && runeLevel != RuneLevel.Nan &&  runeRarity != Rarity())
                break
        }
        if (runeEmplacement.NUMBER == RuneEmplacement.NaN || runeLevel == RuneLevel.Nan ||  runeRarity == Rarity())
            return false

        return true
    }

    private fun setPrimaryStat(textBlocks: List<Text.TextBlock>): Boolean{

        textBlocks.forEach { textblock ->
            textblock.text.split("\n").forEach { text ->
                runeEmplacement.AVAILABLE_MAIN_STATS.forEach { primaryStat ->
                    if (primaryStat.checkPrimaryStat(text) && (runeStar.NUMBER == RuneStar.NaN || runePrimaryStat.PRIMARY.ACTUAL_STAT == 0)){
                        StringUtil.getOnlyNumber(text)?.let {primaryStatValue ->
                            primaryStat.setStarByPrimaryStat(runeLevel,primaryStatValue)?.let { runeStarValue ->
                                runeStar = RuneStar(runeStarValue)
                                runePrimaryStat = primaryStat.setPrimaryStat(runeStarValue,primaryStatValue)

                            }
                        }
                    }
                }
            }
        }

        return (runeStar.NUMBER != RuneStar.NaN && runePrimaryStat.PRIMARY.ACTUAL_STAT != 0)
    }

    private fun setSubStats(textBlocks: List<Text.TextBlock>){

        var subStats : MutableList<SubStat> = mutableListOf()
        textBlocks.forEach { textblock ->
            textblock.text.split("\n").forEach { text ->
                runeEmplacement.AVAILABLE_SUB_STATS.forEach { secondaryStat ->
                    if(secondaryStat.checkSubStat(text,runePrimaryStat)){

                        var subText =  if(text.count { it == '+' } >= 2) {
                            var firstVal =  (StringUtil.getOnlyNumber(text.substringAfter("+").substringBefore("+")))
                            var secondVal =  (StringUtil.getOnlyNumber(text.substringAfter("+").substringAfter("+")))
                            (secondVal?.let { firstVal?.plus(it) }).toString()
                        }else text

                        StringUtil.getOnlyNumber(subText)?.let { secondaryStatValue ->
                            var subStat = secondaryStat.setSubStat(runeStar.NUMBER, secondaryStatValue)
                            if (subStat.SUB.ACTUAL_STAT != 0 && (runePrimaryStat.PRIMARY_STAT_TEXT != subStat.SUB_STAT_TEXT || subStat.SECONDARY_STAT_TEXT != runePrimaryStat.SECONDARY_STAT_TEXT)){

                                if (subStats.count() == 0)
                                    subStats.add(subStat)
                                else
                                    if(subStats.count ({substatInside->  (substatInside.SUB_STAT_TEXT == subStat.SUB_STAT_TEXT && substatInside.SECONDARY_STAT_TEXT == subStat.SECONDARY_STAT_TEXT) }) == 0 )
                                        subStats.add(subStat)

                            }
                        }
                    }
                }
            }
        }

        runeSubStats = subStats

    }

    private fun setOtherStats(){

        val procsDone = RuneLevel.getProcsDone(runeLevel)
        val procsRemaining = Rarity.getProcsRemaining(runeRarity,procsDone)
        val isInnateStatIn = SubStat.isInnateStat(runeSubStats,runeRarity)
        println()
    }


}