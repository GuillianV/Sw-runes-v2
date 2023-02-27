package com.example.sw_runes

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sw_runes.models.RecordingViewModel
import com.example.sw_runes.services.RuneAnalyzerService
import com.example.sw_runes.ui.theme.*
import com.example.sw_runes.utils.Permissions
import com.example.sw_runes.utils.Permissions.Companion.READ_EXTERNAL_STORAGE_CODE
import com.example.sw_runes.utils.Permissions.Companion.WRITE_EXTERNAL_STORAGE_CODE
import com.example.sw_runes.utils.Permissions.Companion.checkReadExternalStorageGranted
import com.example.sw_runes.utils.Permissions.Companion.checkWriteExternalStorageGranted
import com.example.sw_runes.utils.Permissions.Companion.requestReadExternalStorageGranted
import com.example.sw_runes.utils.Permissions.Companion.requestWriteExternalStorageGranted
import com.example.sw_runes.utils.Permissions.Companion.resultReadExternalStorageGranted
import com.example.sw_runes.utils.Permissions.Companion.resultWriteExternalStorageGranted
import java.io.File
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {




    lateinit var runeAnalyzerService : Intent;


    private val recordingViewModel: RecordingViewModel by viewModels()


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_CODE -> {
                if(!resultWriteExternalStorageGranted(permissions,grantResults))
                {
                    finish();
                    System.exit(0);
                }
                else
                    start()
            }

            READ_EXTERNAL_STORAGE_CODE -> {
                if(!resultReadExternalStorageGranted(permissions,grantResults))
                {
                    finish();
                    System.exit(0);
                }
                else
                    start()
            }
            else -> { }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

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


        start()


    }


    fun manageOverlay() : Boolean{

        if (Settings.canDrawOverlays(this))
            return true

        var resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_CANCELED) {
                if (Settings.canDrawOverlays(this))
                {
                    start()
                }else
                    manageOverlay()


            }else
            {
                finish()
                exitProcess(0)
            }
        }

        resultLauncher.launch(Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse(
                "package:${applicationContext.packageName}"
            )
        ))
        return false
    }
    fun start(){

            if (manageOverlay())
            {
                if (!checkWriteExternalStorageGranted(this, applicationContext)){
                    requestWriteExternalStorageGranted(this, applicationContext)
                    return
                }

                if (!checkReadExternalStorageGranted(this, applicationContext)){
                    requestReadExternalStorageGranted(this, applicationContext)
                    return
                }


                recordingViewModel.startRecoding.value = true;
                startProjection()
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

