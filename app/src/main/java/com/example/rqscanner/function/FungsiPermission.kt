package com.example.rqscanner.function

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SinglePermission(viewUnit: Unit, permissionReq: String) {
    val permissionState =
        rememberPermissionState(permission = permissionReq)
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionState.launchPermissionRequest()
                }
                else -> {

                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    when {
        permissionState.hasPermission -> {
            viewUnit
        }
        !permissionState.hasPermission && !permissionState.shouldShowRationale -> {
            permissionState.launchPermissionRequest()
        }
        !permissionState.shouldShowRationale -> {
            permissionState.launchPermissionRequest()
        }
    }
}

@Composable
fun PermissionCamera(): Boolean {
    val context = LocalContext.current as Activity
    if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return true
    }
    return false
}