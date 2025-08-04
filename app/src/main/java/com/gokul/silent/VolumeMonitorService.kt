package com.gokul.silent

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.Settings

class VolumeMonitorService : Service() {

    private lateinit var audioManager: AudioManager
    private lateinit var observer: VolumeObserver

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        observer = VolumeObserver(Handler(Looper.getMainLooper()), this)
        contentResolver.registerContentObserver(
            Settings.System.CONTENT_URI, true, observer
        )

        val notification = NotificationUtils.createNotification(this)
        startForeground(1, notification)
    }

    override fun onDestroy() {
        contentResolver.unregisterContentObserver(observer)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

