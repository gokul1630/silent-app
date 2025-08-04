package com.gokul.silent

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

object NotificationUtils {
    private const val CHANNEL_ID = "volume_monitor_channel"

    fun createNotification(context: Context): Notification {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Volume Monitor",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            setSound(null, null)
            enableVibration(false)
            setShowBadge(false)
        }


        manager.createNotificationChannel(channel)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("")
            .setContentText("")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(android.R.drawable.ic_dialog_map)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }
}
