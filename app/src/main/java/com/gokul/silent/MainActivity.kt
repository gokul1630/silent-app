package com.gokul.silent

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val packageManager = getPackageManager()
//        val componentName = ComponentName(
//            this,
//            MainActivity::class.java
//        )
//        packageManager.setComponentEnabledSetting(
//            componentName,
//            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//            PackageManager.DONT_KILL_APP
//        )

        setContent {
            RequestNotificationPermission(this)
            MaterialTheme {
                Surface (modifier = Modifier.fillMaxSize()) {
                    Text("Volume Limiter is active")
                }
            }
        }
    }
}

fun startVolumeMonitorService(context: Context) {
    Intent(context, VolumeMonitorService::class.java).apply {
        ContextCompat.startForegroundService(context, this)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermission(context: Context) {
    val permissionState = rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            !permissionState.status.isGranted
        ) {
            permissionState.launchPermissionRequest()
        }
    }

    if (!permissionState.status.isGranted) {
        Text(
            "Please allow notification permission so the app can run in the background properly.",
            modifier = Modifier.padding(16.dp)
        )
    } else {
        Log.d("silence", "starting service on main activity")
        startVolumeMonitorService(context)
    }
}
