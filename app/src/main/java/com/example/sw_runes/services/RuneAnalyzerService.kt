package com.example.sw_runes.services

import android.app.Activity
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.core.util.Pair
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.example.sw_runes.enums.TapStatus
import com.example.sw_runes.rune.RuneAI
import com.example.sw_runes.services.extension.Bubble
import com.example.sw_runes.services.extension.RuneOptimisation
import com.example.sw_runes.services.extension.ScreenCapture
import com.example.sw_runes.sw.rune.Rune
import com.example.sw_runes.utils.Notifications

class RuneAnalyzerService : LifecycleService() {


    private var baseOrientation : Int = ORIENTATION_PORTRAIT;

    lateinit var bubble : Bubble
    lateinit var screenCapture: ScreenCapture
    lateinit var runeOptimisation: RuneOptimisation


    var rune : Rune? = null

    var mutableBubbleStatus: MutableLiveData<String> = MutableLiveData()


    companion object {

        private val KEY_RESULT_CODE = "RESULT_CODE"
        private val KEY_DATA = "DATA"
        private val KEY_ACTION = "ACTION"

        private val ACTION_START = "START"
        private val ACTION_STOP = "STOP"

        fun getStartIntent(context: Context?, resultCode: Int, data: Intent?): Intent? {
            val intent = Intent(context, RuneAnalyzerService::class.java)
            intent.putExtra(KEY_ACTION, ACTION_START)
            intent.putExtra(KEY_RESULT_CODE, resultCode)
            intent.putExtra(KEY_DATA, data)
            return intent
        }


        fun getStopIntent(context: Context?): Intent? {
            val intent = Intent(context, RuneAnalyzerService::class.java)
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
        mutableBubbleStatus.value = TapStatus.Ready
        bubble = Bubble(this)
        screenCapture = ScreenCapture(this)



    }


    fun setRune(bitmapByteArray: ByteArray){

        val bitmap = bitmapByteArray.let { BitmapFactory.decodeByteArray(bitmapByteArray, 0, it!!.size) }

        var runeAi : RuneAI = RuneAI(this)
        runeAi.inherance(bitmap)
        rune = Rune(this).setRune(this,bitmap)

    }

    fun showRuneOptimisation(){

        if (rune == null){
            toastError("Aucune rune trouvé")
            return
        }

        runeOptimisation = RuneOptimisation(this,rune!!)
        runeOptimisation.createRuneOptimisation()
    }

    fun toastError(message:String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {

        screenCapture.destroyMediaProjection()
        bubble.destroyBubble()

        super.onDestroy()
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


            //startProjection(resultCode, data)
            startAnalyze(resultCode,data!!)
        } else if (isStopCommand(intent)) {
            stopAnalyze()
           // stopProjection()
            stopSelf()
        } else {
            stopSelf()
        }
        return START_NOT_STICKY
    }

    fun startAnalyze(resultCode: Int,data: Intent){

        bubble.createBubble()
        screenCapture.createMediaProjection( resultCode,data)

    }

    fun stopAnalyze(){

        screenCapture.destroyMediaProjection()
        bubble.destroyBubble()

    }




}