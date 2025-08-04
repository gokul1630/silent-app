package com.gokul.silent

import android.content.Context
import android.database.ContentObserver
import android.os.Handler

class VolumeObserver(handler: Handler, private val context: Context) : ContentObserver(handler) {

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        Audio.silence(context)
    }
}
