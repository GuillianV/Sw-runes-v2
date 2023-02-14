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


class RuneAI(_runeAnalyzerService: RuneAnalyzerService, _bitmapByteArray : ByteArray) {

    val imageSize : Int = 256

    var runeAnalyzerService : RuneAnalyzerService
    var bitmapByteArray : ByteArray
    private var byteBuffer : ByteBuffer

    val classes = arrayOf("rune", "norune")

    init {
        runeAnalyzerService = _runeAnalyzerService
        bitmapByteArray = _bitmapByteArray

        var bmpLocal = bitmapByteArray.let { BitmapFactory.decodeByteArray(bitmapByteArray, 0, it!!.size) }
        bmpLocal = Bitmap.createScaledBitmap(bmpLocal, imageSize, imageSize, false)
        byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(imageSize * imageSize)
        bmpLocal.getPixels(intValues, 0, bmpLocal.getWidth(), 0, 0, bmpLocal.getWidth(), bmpLocal.getHeight())
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

        bmpLocal?.recycle()
    }

    fun inherance(){


        searchForRune()
        searchForBbox()


    }

    private fun searchForRune(){

        val model = RuneDetection.newInstance(runeAnalyzerService)

        // Creates inputs for reference.
        val inputFeature = TensorBuffer.createFixedSize(intArrayOf(1, imageSize, imageSize, 3), DataType.FLOAT32)
        inputFeature.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature)
        val outputFeatureRune = outputs.outputFeature0AsTensorBuffer


        val confidences = outputFeatureRune.floatArray
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

        println(classes[maxPos])
        model.close()

    }

    private fun searchForBbox(){

        val model = RuneBounding.newInstance(runeAnalyzerService)

        // Creates inputs for reference.
        val inputFeature = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
        inputFeature.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature)
        val outputBBox = outputs.outputFeature1AsTensorBuffer

        // Releases model resources if no longer used.
        model.close()

        var bmpLocal = bitmapByteArray.let { BitmapFactory.decodeByteArray(bitmapByteArray, 0, it!!.size) }

        println("bmp width : "+bmpLocal.width)
        println("bmp height : "+bmpLocal.height)

        var xmin =  (outputBBox.floatArray[0] * bmpLocal.width).toInt()
        var ymin =  (outputBBox.floatArray[1] * bmpLocal.height).toInt()
        var xmax =  (outputBBox.floatArray[2] * bmpLocal.width).toInt()
        var ymax =  (outputBBox.floatArray[3] * bmpLocal.height).toInt()
        println("xmin px : "+ xmin)
        println("ymin px : "+ ymin)
        println("xmax px : "+ xmax)
        println("ymax px : "+ ymax)

        val resizedBmp: Bitmap = Bitmap.createBitmap(bmpLocal,xmin,ymin,xmax-xmin,ymax-ymin )

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
        bmpLocal.recycle()
        resizedBmp.recycle()
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