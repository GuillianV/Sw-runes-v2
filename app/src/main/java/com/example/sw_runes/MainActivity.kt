package com.example.sw_runes

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
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
import com.example.sw_runes.models.RecordingViewModel
import com.example.sw_runes.services.RuneAnalyzerService
import com.example.sw_runes.ui.theme.*
import com.example.sw_runes.utils.Permissions
import com.example.sw_runes.utils.Permissions.Companion.isManageOverlayGranted
import com.example.sw_runes.utils.Permissions.Companion.isWriteExternalStorageGranted
import java.io.File

class MainActivity : ComponentActivity() {




    lateinit var runeAnalyzerService : Intent;


    private val recordingViewModel: RecordingViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())


        Permissions.isReadExternalStorageGranted(this,applicationContext)


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

        } else {

            // Check that the user has granted permission, and prompt them if not

        }
    }
    override fun onResume() {
        super.onResume()

    }
    override fun onDestroy() {

        stopProjection()
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
                    RuneAnalyzerService.getStartIntent(
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
       stopService(RuneAnalyzerService.getStopIntent(this))
    }





}

