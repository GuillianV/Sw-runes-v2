package com.example.sw_runes.rune

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.media.ThumbnailUtils
import android.os.Environment
import com.example.sw_runes.ml.RuneMl
import com.example.sw_runes.services.RuneAnalyzerService
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder


class RuneAI(_runeAnalyzerService: RuneAnalyzerService, _bitmap: Bitmap) {

    val imageSize : Int = 256

    var runeAnalyzerService : RuneAnalyzerService
    var bitmap : Bitmap
    private var byteBuffer : ByteBuffer

    val classes = arrayOf("rune", "norune")

    init {
        runeAnalyzerService = _runeAnalyzerService
        bitmap = _bitmap
        var bmpLocal = _bitmap
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

    fun inherance():Bitmap?{


       return searchForRune()


    }

    private fun searchForRune() : Bitmap?{

        val model = RuneMl.newInstance(runeAnalyzerService)

        // Creates inputs for reference.
        val inputFeature = TensorBuffer.createFixedSize(intArrayOf(1, imageSize, imageSize, 3), DataType.FLOAT32)
        inputFeature.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature)

        val confidences = outputs.outputFeature0AsTensorBuffer.floatArray
        var outputBBox = outputs.outputFeature1AsTensorBuffer.floatArray
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

        if (classes[maxPos] == "rune")
            return searchForBbox(outputBBox)
        else
            return null
    }

    private fun searchForBbox(outputBBox: FloatArray):Bitmap{


        println("bmp width : "+bitmap.width)
        println("bmp height : "+bitmap.height)

        var xmin =  (outputBBox[0] * bitmap.width).toInt()
        xmin = xmin - 50
        if(xmin < 0)
            xmin = 0

        var ymin =  (outputBBox[1] * bitmap.height).toInt()
        ymin = ymin - 50
        if(ymin < 0)
            ymin = 0

        var xmax =  (outputBBox[2] * bitmap.width).toInt()
        xmax = xmax + 50
        if(xmax > bitmap.width)
            xmax = bitmap.width

        var ymax =  (outputBBox[3] * bitmap.height).toInt()
        ymax = ymax + 50
        if(ymax > bitmap.height)
            ymax = bitmap.height
        
        println("xmin px : "+ xmin)
        println("ymin px : "+ ymin)
        println("xmax px : "+ xmax)
        println("ymax px : "+ ymax)

        val resizedBmp: Bitmap = Bitmap.createBitmap(bitmap,xmin,ymin,xmax-xmin,ymax-ymin )

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
        //runeAnalyzerService.setBitmap(resizedBmp)
        fileOutputStream.close()

        return resizedBmp
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