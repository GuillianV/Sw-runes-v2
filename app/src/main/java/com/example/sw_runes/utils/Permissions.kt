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
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat


class Permissions {

    companion object {

        val WRITE_EXTERNAL_STORAGE_CODE : Int = 1
        val READ_EXTERNAL_STORAGE_CODE : Int = 2
        fun requestWriteExternalStorageGranted(activity: Activity ,context : Context) {
            requestPermissions(activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_CODE)



        }

        fun resultWriteExternalStorageGranted( permissions: Array<out String>, grantResults: IntArray) : Boolean {
            return (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)

        }

        fun checkWriteExternalStorageGranted(activity: Activity ,context : Context) : Boolean {
            return when (ContextCompat.checkSelfPermission( context, Manifest.permission.WRITE_EXTERNAL_STORAGE  )) {
                PackageManager.PERMISSION_GRANTED -> {
                    true
                }
                PackageManager.PERMISSION_DENIED -> {
                    false
                }
                //activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {    }
                else -> {

                    false
                }
            }
        }


        fun requestReadExternalStorageGranted(activity: Activity ,context : Context) {
            requestPermissions(activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_CODE)



        }

        fun resultReadExternalStorageGranted( permissions: Array<out String>, grantResults: IntArray) : Boolean {
            return (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)

        }

        fun checkReadExternalStorageGranted(activity: Activity ,context : Context) : Boolean {
            return when (ContextCompat.checkSelfPermission( context, Manifest.permission.READ_EXTERNAL_STORAGE  )) {
                PackageManager.PERMISSION_GRANTED -> {
                    true
                }
                PackageManager.PERMISSION_DENIED -> {
                    false
                }
                //activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {    }
                else -> {
                    false
                }
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