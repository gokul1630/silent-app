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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import kotlin.math.roundToInt



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RequestNotificationPermission(this)
            MaterialTheme {
                VolumeLimitScreen()
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


@Composable
fun VolumeLimitScreen() {
    val context = LocalContext.current
    var value by remember { mutableFloatStateOf(0.5f) }

    LaunchedEffect(Unit) {
        value = getPrefs(context).getFloat("volume_limit_ratio", 0.5f)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${(value * 100).roundToInt()}%", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Slider(
                value = value,
                onValueChange = {
                    value = it
                    getPrefs(context).edit().putFloat("volume_limit_ratio", value).apply()
                },
                valueRange = 0.4f..0.6f,
                steps = 100,
                modifier = Modifier.width(300.dp)
            )
        }
    }
}


private fun getPrefs(context: Context) =
    context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
