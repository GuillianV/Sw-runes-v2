package com.example.sw_runes.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.app.ActivityCompat


class Permissions {

    companion object {

        fun isWriteExternalStorageGranted(activity: Activity ,context : Context): Boolean {
            return if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
                false
            }

        }


        fun isReadExternalStorageGranted(activity: Activity ,context : Context): Boolean {
            return if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
                false
            }

        }




        fun isManageOverlayGranted(activity: Activity ,context : Context): Boolean {

            // Checks if app already has permission to draw overlays
            return if (!Settings.canDrawOverlays(activity)) {

                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse(
                        "package:${context.packageName}"
                    )
                )

                if (activity is ComponentActivity){

                    var resultLauncher = (activity as ComponentActivity ).registerForActivityResult(StartActivityForResult()) { result ->
                        if (result.resultCode == Activity.RESULT_OK) {
                            // There are no request codes
                            val data: Intent? = result.data
                            true
                        }else
                            false
                    }

                    resultLauncher.launch(intent)
                    false
                }else
                    false


            }else{
                true
            }
        }

    }

}