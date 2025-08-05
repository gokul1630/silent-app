package com.gokul.silent

import android.content.Context
import android.media.AudioManager
import android.util.Log

class Audio {
    companion object {
        fun silence(context: Context) {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

//            val focusRequest = AudioFocusRequest.Builder(AudioManager.STREAM_MUSIC)
//                .setOnAudioFocusChangeListener { focusChange ->
//                    when(focusChange){
//                        AudioManager.AUDIOFOCUS_LOSS, AudioManager.AUDIOFOCUS_LOSS_TRANSIENT, AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
//                            Log.d("silence", "gained focus")
//                            mute(audioManager)
//                        }
//                    }
//                }
//                .build()
//            audioManager.requestAudioFocus(focusRequest)

            mute(audioManager)
        }

        fun mute(audioManager: AudioManager) {
            val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

//            3/4 of volume
            val limit = (max * 0.5).toInt()

            Log.d("silence:", "max $max, current $current")


            audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                limit,
                AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE
            )
        }
    }
}