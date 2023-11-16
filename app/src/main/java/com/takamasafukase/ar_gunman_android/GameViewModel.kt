package com.takamasafukase.ar_gunman_android

import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.ViewModel
import com.unity3d.player.UnityPlayer

class GameViewModel(
    sensorManager: SensorManager
) : ViewModel() {
    private var motionDetector: MotionDetector

    init {
        motionDetector = MotionDetector(
            sensorManager = sensorManager,
            onDetectPistolFiringMotion = {
                Log.d("Android", "ログAndroid: onDetectPistolFiringMotion")
                // Unityへ通知を送る
                UnityPlayer.UnitySendMessage("XR Origin", "ShootBullet", "")
            },
            onDetectPistolReloadingMotion = {
                Log.d("Android", "ログAndroid: onDetectPistolReloadingMotion")
                // TODO: リロード後の処理
            }
        )
    }

    fun onTapWeaponChangeButton() {

    }
}