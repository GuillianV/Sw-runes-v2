package com.example.sw_runes

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.sw_runes.models.RecordingViewModel
import com.example.sw_runes.ui.theme.*
import com.example.sw_runes.utils.Permissions
import com.example.sw_runes.utils.Permissions.Companion.isManageOverlayGranted
import com.example.sw_runes.utils.Permissions.Companion.isWriteExternalStorageGranted

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

                val activity = LocalContext.current as Activity
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

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
        startProjection()
        if ( isManageOverlayGranted(this, applicationContext) && isWriteExternalStorageGranted(this, applicationContext)) {

            // Launch service right away - the user has already previously granted permission
            launchBubbleService()
        } else {

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

