package com.takamasafukase.ar_gunman_android.manager

import android.content.Context
import android.media.MediaPlayer

class AudioManager(context: Context) {
    private lateinit var mediaPlayer: MediaPlayer
    private val myContext: Context = context

    fun playSound(resId: Int) {
        mediaPlayer = MediaPlayer.create(myContext, resId)
        mediaPlayer.isLooping = false
        mediaPlayer.start()
    }
}