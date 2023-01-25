package com.example.sw_runes

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.sw_runes.services.ScreenCaptureService
import com.example.sw_runes.ui.theme.*
import com.example.sw_runes.utils.Permissions.Companion.isManageOverlayGranted
import com.example.sw_runes.utils.Permissions.Companion.isWriteExternalStorageGranted
import com.example.sw_runes.workers.BubbleWorker

class MainActivity : ComponentActivity() {




    lateinit var bubbleIntent : Intent;

    private val recordingViewModel: RecordingViewModel by viewModels()


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

    }


    fun checkPermissions(){
        if ( isManageOverlayGranted(this, applicationContext) && isWriteExternalStorageGranted(this, applicationContext)) {

            // Launch service right away - the user has already previously granted permission
            startProjection()
            startBubbleService()

        } else {

            // Check that the user has granted permission, and prompt them if not

        }
    }
    override fun onResume() {
        super.onResume()

    }
    override fun onDestroy() {

        stopProjection()
        stopBubbleService()
        super.onDestroy()
    }
    fun startProjection() {
        val mProjectionManager =
            getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager


        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                startService(
                    ScreenCaptureService.getStartIntent(
                        this,
                        result.resultCode,
                        data
                    )
                )
            }
        }

        resultLauncher.launch(mProjectionManager.createScreenCaptureIntent())
    }
    private fun stopProjection() {
       stopService(ScreenCaptureService.getStopIntent(this))
    }

    private fun startBubbleService() {
        bubbleIntent = Intent(this, BubbleService::class.java)
        startService(bubbleIntent)

    }
    private fun stopBubbleService() {
        bubbleIntent = Intent(this, BubbleService::class.java)
        stopService(bubbleIntent)

    }



}

