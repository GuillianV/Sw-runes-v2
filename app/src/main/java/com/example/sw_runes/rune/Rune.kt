package com.example.sw_runes.sw.rune

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.sw_runes.rune.RuneRarity
import com.example.sw_runes.rune.rarity.Rarity
import com.example.sw_runes.sw.rune.emplacement.Emplacement
import com.example.sw_runes.sw.rune.stats.primary.PrimaryStat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class Rune()  {

    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    lateinit var bitmap : Bitmap


    //Rune params
    var runeEmplacement : Emplacement = Emplacement()
    var runeLevel : Int = RuneLevel.Nan
    var runeRarity : Rarity = Rarity()
    var runeStar : RuneStar = RuneStar(0)
    //Rune stats
    var runeMainStat : PrimaryStat = PrimaryStat()



    fun  setRune(context: Context, _bitmap: Bitmap) : Rune?{

        bitmap = _bitmap
        val inputImage = InputImage.fromBitmap(_bitmap,0)
        recognizer.process(inputImage).addOnSuccessListener { visionText ->

            if(!setRuneBaseStats(visionText.textBlocks)){

            }


            _bitmap!!.recycle()
        }.addOnFailureListener { throw Exception("Erreur recognizer") }.addOnCompleteListener {

        }

        return this;
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
                    if (primaryStat.checkPrimaryStat(text)){



                    }
                }
            }
        }

    }

}