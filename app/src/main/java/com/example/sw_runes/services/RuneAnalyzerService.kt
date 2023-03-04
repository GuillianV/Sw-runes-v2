package com.example.sw_runes.services

import android.app.Activity
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Looper
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
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.*

class RuneAnalyzerService : LifecycleService() {


    private var baseOrientation : Int = ORIENTATION_PORTRAIT;

    lateinit var bubble : Bubble
    lateinit var screenCapture: ScreenCapture
    var runeOptimisation: RuneOptimisation? = null


    lateinit var rune : Rune

    private  var bitmap : Bitmap? = null

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


    fun setBitmap(_bitmap: Bitmap){
        if (bitmap != null){
            bitmap?.recycle()
            bitmap = null
        }

        bitmap = _bitmap
    }

    fun getBitmap() : Bitmap? {

        return  bitmap

    }


    override fun onCreate() {
        super.onCreate()

        baseOrientation = getResources().getConfiguration().orientation
        mutableBubbleStatus.value = TapStatus.Ready
        bubble = Bubble(this)
        screenCapture = ScreenCapture(this)



    }


    fun setRune(){

        var localBmp : Bitmap? = null

        getBitmap()?.let {bmp ->
        var runeAi : RuneAI = RuneAI(this,bmp)
            runeAi.inherance()?.let { localBmp = it }

        }

        rune = Rune(this)

        getBitmap()?.let {bmp ->

                if (localBmp != null ){


                    CoroutineScope(Dispatchers.Main).launch {
                        runBlocking {

                        withContext(Dispatchers.IO) {

                                if (Looper.myLooper() == null) {
                                    Looper.prepare()
                                }

                                var res= async { rune.setRune(localBmp!!) }.await()
                                if (!rune.isViable) {
                                    localBmp?.recycle()
                                    res = async {  rune.setRune(bmp) }.await()

                                }else
                                    setBitmap(localBmp!!)
                                //
                            }
                        }
                        mutableBubbleStatus.value = TapStatus.Ready
                        showRuneOptimisation()
                    }

                }




        }


    }

    fun showRuneOptimisation(){

        if (rune.isViable == false){
            toastError("Aucune rune trouvé")
            return
        }

        if (runeOptimisation != null){
            runeOptimisation!!.destroyRuneOptimisation()
        }
        runeOptimisation = RuneOptimisation(this,rune!!)
        runeOptimisation?.createRuneOptimisation()
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