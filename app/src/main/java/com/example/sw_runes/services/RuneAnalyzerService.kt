package com.example.sw_runes.services

import android.app.Activity
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Handler
import android.os.Looper
import androidx.core.util.Pair
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.sw_runes.enums.TapStatus
import com.example.sw_runes.utils.Notifications
import com.example.sw_runes.workers.BubbleWorker
import com.example.sw_runes.workers.RuneAnalyzerWorker

class RuneAnalyzerService : LifecycleService() {


    private var baseOrientation : Int = ORIENTATION_PORTRAIT;
    private lateinit var handler : Handler


    companion object {

        private val KEY_RESULT_CODE = "RESULT_CODE"
        private val KEY_DATA = "DATA"
        private val KEY_ACTION = "ACTION"

        private val ACTION_START = "START"
        private val ACTION_STOP = "STOP"

        fun getStartIntent(context: Context?, resultCode: Int, data: Intent?): Intent? {
            val intent = Intent(context, ScreenCaptureService::class.java)
            intent.putExtra(KEY_ACTION, ACTION_START)
            intent.putExtra(KEY_RESULT_CODE, resultCode)
            intent.putExtra(KEY_DATA, data)
            return intent
        }


        fun getStopIntent(context: Context?): Intent? {
            val intent = Intent(context, ScreenCaptureService::class.java)
            intent.putExtra(KEY_ACTION, ACTION_STOP)
            return intent
        }

        private fun isStartCommand(intent: Intent): Boolean {
            return (intent.hasExtra(KEY_RESULT_CODE) && intent.hasExtra(KEY_DATA)
                    && intent.hasExtra(KEY_ACTION) && intent.getStringExtra(KEY_ACTION) == ACTION_START)
        }

        private fun isStopCommand(intent: Intent): Boolean {
            return intent.hasExtra(KEY_ACTION) && intent.getStringExtra(KEY_ACTION) == ACTION_STOP
        }
    }




    override fun onCreate() {
        super.onCreate()

        baseOrientation = getResources().getConfiguration().orientation

        // start capture handling thread
        object : Thread() {
            override fun run() {
                Looper.prepare()
                handler = Handler(Looper.getMainLooper())
                Looper.loop()
            }
        }.start()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (intent?.getParcelableExtra<Intent>(KEY_DATA) == null)
            stopSelf()

        if (isStartCommand(intent!!)) {
            // create notification
            val notification: Pair<Int, Notification> = Notifications.getNotification(this)
            startForeground(notification.first!!, notification.second)

            // start projection
            val resultCode = intent.getIntExtra(KEY_RESULT_CODE, Activity.RESULT_CANCELED)

            val data = intent.getParcelableExtra<Intent>(KEY_DATA)
            startProjection(resultCode, data)

        } else if (isStopCommand(intent)) {
            stopProjection()
            stopSelf()
        } else {
            stopSelf()
        }
        return START_NOT_STICKY
    }




}