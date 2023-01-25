package com.example.sw_runes

import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.sw_runes.models.RecordingViewModel
import com.example.sw_runes.services.BubbleService
import com.example.sw_runes.ui.theme.*
import com.example.sw_runes.utils.Permissions.Companion.isManageOverlayGranted
import com.example.sw_runes.utils.Permissions.Companion.isWriteExternalStorageGranted
import com.example.sw_runes.workers.BubbleWorker

class MainActivity : ComponentActivity() {




    lateinit var bubbleIntent : Intent;

    private val recordingViewModel: RecordingViewModel by viewModels()
    private val workManager = WorkManager.getInstance(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())

        setContent {
            SwRunesTheme {

              /*  val activity = LocalContext.current as Activity
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE */

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    Column() {
                        Container()
                    }

                }
            }
        }



        recordingViewModel.startRecoding.value = true;

        checkPermissions()
        startProjection()


        val request = OneTimeWorkRequestBuilder<BubbleWorker>()
            .addTag("hey")
            .build()

        workManager.enqueue(request)
        workManager.getWorkInfosByTagLiveData("hey")
            .observe(this, Observer { workInfos ->
                if (workInfos != null) {
                    for (workInfo in workInfos) {
                        when (workInfo.state) {
                            WorkInfo.State.SUCCEEDED -> {
                                print(workInfo.outputData.getString("data"))
                            }
                            else -> {
                                print("enque")
                            }
                        }
                    }
                }})



    }


    fun checkPermissions(){
        if ( isManageOverlayGranted(this, applicationContext) && isWriteExternalStorageGranted(this, applicationContext)) {

            // Launch service right away - the user has already previously granted permission
            launchBubbleService()
        } else {
            checkPermissions()
            // Check that the user has granted permission, and prompt them if not

        }
    }
    override fun onResume() {
        super.onResume()


    }
    override fun onDestroy() {

       // stopService(bubbleIntent)
      //  val intent = Intent(this, ScreenCaptureService::class.java)
        stopProjection()
        super.onDestroy()
    }
    fun startProjection() {
        val mProjectionManager =
            getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

        startActivity(mProjectionManager.createScreenCaptureIntent(),)
  /*      startActivityForResult(
            mProjectionManager.createScreenCaptureIntent(),
            ScreenCaptureService.REQUEST_CODE_RECORDING
        )*/
    }
    private fun stopProjection() {
       // startService(ScreenCaptureService.getStopIntent(this))
    }

    private fun launchBubbleService() {
        bubbleIntent = Intent(this, BubbleService::class.java)
        stopService(bubbleIntent)
        startService(bubbleIntent)

    }



}

