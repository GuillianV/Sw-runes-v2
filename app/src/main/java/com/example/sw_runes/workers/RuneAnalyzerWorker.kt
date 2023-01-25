package com.example.sw_runes.workers

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters


class RuneAnalyzerWorker(context: Context, params: WorkerParameters) : Worker(context, params) {


    companion object{
        val tag = "rune_analyzer_worker"
        val key = "rune_analyzer_bitmap_bytearray"

    }


    override fun doWork(): Result {

        var bitmapByteArray = inputData.getByteArray(key)
        bitmapByteArray?.let {
            val outputData = Data.Builder().putByteArray(key,it).build()
            return Result.success(outputData)
        }
        return Result.failure()
    }
}