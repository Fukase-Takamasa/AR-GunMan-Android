package com.takamasafukase.ar_gunman_android

import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.ViewModel
import com.unity3d.player.UnityPlayer

class GameViewModel(
    sensorManager: SensorManager,
    private val audioManager: AudioManager,
) : ViewModel(), UnityToAndroidMessenger.MessageReceiverFromUnity {
    private var motionDetector: MotionDetector

    init {
        motionDetector = MotionDetector(
            sensorManager = sensorManager,
            onDetectPistolFiringMotion = {
                Log.d("Android", "ログAndroid: onDetectPistolFiringMotion")
                audioManager.playSound(R.raw.pistol_fire)
                // Unityへ通知を送る
                UnityPlayer.UnitySendMessage("XR Origin", "ShootBullet", "")
            },
            onDetectPistolReloadingMotion = {
                Log.d("Android", "ログAndroid: onDetectPistolReloadingMotion")
                audioManager.playSound(R.raw.pistol_reload)
                // TODO: リロード後の処理
            }
        )
    }

    fun onTapWeaponChangeButton() {

    }

    override fun onMessageReceivedFromUnity(message: String) {
        Log.d("Android", "ログAndroid: GameVM onMessageReceivedFromUnity message: $message")
    }
}