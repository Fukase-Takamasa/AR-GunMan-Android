package com.takamasafukase.ar_gunman_android

import android.app.Application
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TopViewModel(audioManager: AudioManager) : ViewModel() {
    val audioManager = audioManager

    sealed class IconButtonType {
        object Start : IconButtonType()
        object Ranking : IconButtonType()
        object HowToPlay : IconButtonType()
    }

    private val _state = MutableStateFlow(
        TopViewState(
            startButtonImageResourceId = R.drawable.target_icon,
            rankingButtonImageResourceId = R.drawable.target_icon,
            howToPlayButtonImageResourceId = R.drawable.target_icon,
            isShowRankingDialog = false,
            isShowTutorialDialog = false,
        )
    )
    val state = _state.asStateFlow()

    private val _showGame = MutableSharedFlow<Unit>()
    val showGame = _showGame.asSharedFlow()
    private val _showSetting = MutableSharedFlow<Unit>()
    val showSetting = _showSetting.asSharedFlow()

    fun onTapStartButton() {
        switchButtonIconAndRevert(type = IconButtonType.Start)
    }

    fun onTapRankingButton() {
        switchButtonIconAndRevert(type = IconButtonType.Ranking)
    }

    fun onTapHowToPlayButton() {
        switchButtonIconAndRevert(type = IconButtonType.HowToPlay)
    }

    fun onTapSettingButton() {
        viewModelScope.launch {
            _showSetting.emit(Unit)
        }
    }

    fun onCloseRankingDialog() {
        _state.value = _state.value.copy(isShowRankingDialog = false)
    }

    fun onCloseTutorialDialog() {
        _state.value = _state.value.copy(isShowTutorialDialog = false)
    }

    private fun switchButtonIconAndRevert(type: IconButtonType) {
        // ウエスタン風な銃声の再生
        audioManager.playSound(R.raw.western_pistol_shoot)
        // 対象のボタンに弾痕の画像を表示
        when (type) {
            IconButtonType.Start -> {
                _state.value = _state.value.copy(
                    startButtonImageResourceId = R.drawable.bullets_hole
                )
            }
            IconButtonType.Ranking -> {
                _state.value = _state.value.copy(
                    rankingButtonImageResourceId = R.drawable.bullets_hole
                )
            }
            IconButtonType.HowToPlay -> {
                _state.value = _state.value.copy(
                    howToPlayButtonImageResourceId = R.drawable.bullets_hole
                )
            }
        }
        // 0.5秒後の処理
        Handler(Looper.getMainLooper()).postDelayed({
            // 画像を元の的に戻す
            _state.value = _state.value.copy(
                startButtonImageResourceId = R.drawable.target_icon,
                rankingButtonImageResourceId = R.drawable.target_icon,
                howToPlayButtonImageResourceId = R.drawable.target_icon,
            )
            // 対象のボタンごとの遷移指示を流す
            viewModelScope.launch {
                when (type) {
                    IconButtonType.Start -> {
                        _showGame.emit(Unit)
                    }
                    IconButtonType.Ranking -> {
                        _state.value = _state.value.copy(isShowRankingDialog = true)
                    }
                    IconButtonType.HowToPlay -> {
                        _state.value = _state.value.copy(isShowTutorialDialog = true)
                    }
                }
            }
        }, 500)
    }
}