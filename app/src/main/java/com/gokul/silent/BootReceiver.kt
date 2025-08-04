package com.gokul.silent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat

class BootReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent?) {

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
           try {
               Intent(context, VolumeMonitorService::class.java).apply {
                   ContextCompat.startForegroundService(context, this)
               }

               Log.d("silence", "started service after reboot")
           } catch (error: Error) {
               Log.d("silence", "something went wrong after reboot \n ${error.toString()}")
           } finally {
               Audio.silence(context)
           }
        }
    }
}
