package com.gokul.silent

import android.content.Context
import android.media.AudioManager
import android.util.Log
import kotlin.math.roundToInt

class Audio {
    companion object {

        private val streamsToClamp = intArrayOf(
            AudioManager.STREAM_MUSIC,
        )

        fun silence(context: Context) {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

            streamsToClamp.forEach { streamsToClamp ->
                mute(context, streamsToClamp,audioManager)
            }
        }

        fun mute(context: Context, streamType: Int, audioManager: AudioManager) {
            val max = audioManager.getStreamMaxVolume(streamType)
            val current = audioManager.getStreamVolume(streamType)

            val volumeRatio = getVolumeLimitRatio(context)
//            1/2 of volume
            val limit = (max * volumeRatio).roundToInt().coerceAtLeast(1)

            Log.d("silence:", "max: $max, current: $current, ratio: $volumeRatio ")

            if(current > limit) {
                audioManager.setStreamVolume(
                    streamType,
                    limit,
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE
                )
            }
        }
    }
}

fun getVolumeLimitRatio(context: Context): Float {
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return prefs.getFloat("volume_limit_ratio", 0.5f)
}
