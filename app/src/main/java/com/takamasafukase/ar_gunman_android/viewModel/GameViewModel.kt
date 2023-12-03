package com.takamasafukase.ar_gunman_android.viewModel

import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import com.takamasafukase.ar_gunman_android.manager.AudioManager
import com.takamasafukase.ar_gunman_android.manager.MotionDetector
import com.takamasafukase.ar_gunman_android.R
import com.takamasafukase.ar_gunman_android.UnityToAndroidMessenger
import com.unity3d.player.UnityPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GameViewState(
    val isLoading: Boolean,
)

class GameViewModel(
    sensorManager: SensorManager,
    private val audioManager: AudioManager,
) : ViewModel(), UnityToAndroidMessenger.MessageReceiverFromUnity {
    private lateinit var motionDetector: MotionDetector
    private val _state = MutableStateFlow(GameViewState(true))
    val state = _state.asStateFlow()

    init {
        showLoadingToHideUnityLogoSplash()
        handleMotionDetector(sensorManager = sensorManager)
    }

    fun onTapWeaponChangeButton() {

    }

    override fun onMessageReceivedFromUnity(message: String) {
        Log.d("Android", "ログAndroid: GameVM onMessageReceivedFromUnity message: $message")
    }

    // Unityビューを起動後にUnityロゴのスプラッシュが出るので、その間は黒背景とインジケータで隠す
    private fun showLoadingToHideUnityLogoSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            _state.value = _state.value.copy(isLoading = false)
        }, 2500)
    }

    private fun handleMotionDetector(sensorManager: SensorManager) {
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
}