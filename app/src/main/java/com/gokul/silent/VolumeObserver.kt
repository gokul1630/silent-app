package com.gokul.silent

import android.content.Context
import android.database.ContentObserver
import android.os.Handler


class VolumeObserver(private val handler: Handler, private val context: Context) : ContentObserver(handler) {

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        handler.removeCallbacks(enforceRunnable)
        handler.postDelayed(enforceRunnable, 500L)
    }

    private val enforceRunnable = Runnable {
        Audio.silence(context)
    }
}
