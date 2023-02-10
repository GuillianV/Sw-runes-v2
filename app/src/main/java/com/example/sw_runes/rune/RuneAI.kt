package com.example.sw_runes.rune

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import com.example.sw_runes.ml.RuneDetection
import com.example.sw_runes.services.RuneAnalyzerService
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


class RuneAI(_runeAnalyzerService: RuneAnalyzerService) {

    val imageSize : Int = 256

    var runeAnalyzerService : RuneAnalyzerService

    init {
        runeAnalyzerService = _runeAnalyzerService
    }

    fun inherance(_bitmap:Bitmap){

        var image = _bitmap

        val model = RuneDetection.newInstance(runeAnalyzerService)

        val dimension: Int = Math.min(image.getWidth(), image.getHeight())
        image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)

        image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, imageSize, imageSize, 3), DataType.FLOAT32)
        val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(imageSize * imageSize)
        image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight())
        var pixel = 0
        //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
        //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
            }
        }


        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        val outputFeature1 = outputs.outputFeature1AsTensorBuffer


        val confidences = outputFeature0.floatArray
        // find the index of the class with the biggest confidence.
        // find the index of the class with the biggest confidence.
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }
        val classes = arrayOf("rune", "norune")

        println(classes[maxPos])


        model.close()
    }






}