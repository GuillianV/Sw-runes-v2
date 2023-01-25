package com.example.sw_runes.services

import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.sw_runes.enums.TapStatus
import com.example.sw_runes.workers.BubbleWorker
import com.example.sw_runes.workers.RuneAnalyzerWorker

class RuneAnalyzerService : LifecycleService() {


    private val workManager = WorkManager.getInstance(this)

    var runeScreenByteArray : ByteArray? = null;


    override fun onCreate() {
        super.onCreate()
        listenRuneAnalyzerWorker()
    }

    private fun listenRuneAnalyzerWorker(){
        val request = OneTimeWorkRequestBuilder<RuneAnalyzerWorker>()
            .addTag(RuneAnalyzerWorker.tag)
            .build()

        workManager.enqueue(request)
        workManager.getWorkInfosByTagLiveData(RuneAnalyzerWorker.tag)
            .observe(this, Observer { workInfos ->
                if (workInfos != null) {
                    for (workInfo in workInfos) {
                        when (workInfo.state) {
                            WorkInfo.State.SUCCEEDED -> {

                                runeScreenByteArray = workInfo.outputData.getByteArray(RuneAnalyzerWorker.key)
                                analyzeRuneBitmap()

                            }
                            else -> {
                                print("failed")
                            }
                        }
                    }
                    workManager.pruneWork()
                }})

    }

    fun analyzeRuneBitmap(){

        print("analyzing")

    }


}