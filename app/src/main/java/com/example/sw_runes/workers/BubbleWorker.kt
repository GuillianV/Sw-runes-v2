package com.example.sw_runes.workers

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.sw_runes.enums.TapStatus


class BubbleWorker(context: Context, params: WorkerParameters) : Worker(context, params) {


    companion object{
        val tag = "bubble_worker"
        val key = "bubble_tap_status"

    }


    override fun doWork(): Result {

        var tapStatus = inputData.getString(key)
        val outputData = Data.Builder().putString(key,tapStatus).build()

        return Result.success(outputData)
    }
}