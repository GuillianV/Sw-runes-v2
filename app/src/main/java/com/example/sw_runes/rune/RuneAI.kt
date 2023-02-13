package com.example.sw_runes.rune

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Environment
import com.example.sw_runes.ml.RuneBounding
import com.example.sw_runes.ml.RuneDetection
import com.example.sw_runes.services.RuneAnalyzerService
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.io.FileOutputStream
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

        if(classes[maxPos] == "rune"){



            val modelBB = RuneBounding.newInstance(runeAnalyzerService)

            // Creates inputs for reference.
                        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
                        inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
                        val outputs = model.process(inputFeature0)
                        val outputFeature1 = outputs.outputFeature1AsTensorBuffer

            // Releases model resources if no longer used.
            modelBB.close()


            val resizedBmp: Bitmap = Bitmap.createBitmap(_bitmap, (outputFeature1.floatArray[0] * _bitmap.width).toInt(), (outputFeature1.floatArray[1] * _bitmap.height).toInt(), (outputFeature1.floatArray[2] * _bitmap.width).toInt(), (outputFeature1.floatArray[3] * _bitmap.height).toInt())

            var folderDir : String = "/DCIM/SWrunesStorage/"
            var mStoreDir: String? = null
            val externalFilesDir = runeAnalyzerService.getExternalFilesDir(null)
            if (externalFilesDir != null) {
                mStoreDir =
                    Environment.getExternalStorageDirectory().absolutePath.toString() + folderDir
                val storeDirectory: File = File(mStoreDir)
                if (!storeDirectory.exists()) {
                    val success = storeDirectory.mkdirs()
                    if (!success) {
                        throw Exception("failed to create file storage directory.")
                    }
                }

            }
            val size : Int =  dirSize( File(mStoreDir))
            var fileOutputStream: FileOutputStream =   FileOutputStream(mStoreDir +"/newrune_"+size+ ".png")
            resizedBmp.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream)
            fileOutputStream.close()

        }

        println(classes[maxPos])


        model.close()
    }


    private fun dirSize(dir: File): Int {
        if (dir.exists()) {
            var result: Int = 0
            val fileList = dir.listFiles()
            if (fileList != null) {
                result = fileList.size
            }
            return result // return the file size
        }
        return 0
    }



}