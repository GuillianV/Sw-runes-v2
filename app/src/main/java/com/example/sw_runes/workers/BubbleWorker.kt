package com.example.sw_runes.workers

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters


class BubbleWorker(context: Context, params: WorkerParameters) : Worker(context, params) {




    override fun doWork(): Result {
        val data = inputData.getString("data")
        val outputData = Data.Builder().putString("data", data).build()

        return Result.success(outputData)
    }
}