package com.takamasafukase.ar_gunman_android

import androidx.lifecycle.ViewModel

class GameViewModel(private val motionDetector: MotionDetector) : ViewModel() {

    init {
        motionDetector
    }

    fun onTapWeaponChangeButton() {

    }
}