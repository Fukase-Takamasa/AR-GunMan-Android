package com.takamasafukase.ar_gunman_android

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TopViewModel : ViewModel() {
    // ViewModel => View方向の通知イベントの種別
    sealed class OutputEvent {
        object ShowGame : OutputEvent()
        object ShowRanking : OutputEvent()
        object ShowTutorial : OutputEvent()
        object ShowSetting : OutputEvent()
    }

    private val _state = MutableStateFlow(
        TopViewState(
            startButtonImageResourceId = R.drawable.target_icon,
            rankingButtonImageResourceId = R.drawable.target_icon,
            howToPlayButtonImageResourceId = R.drawable.target_icon,
        )
    )
    val state = _state.asStateFlow()

    // ViewModel => View方向の通知イベント
    private val _outputEvent = MutableSharedFlow<OutputEvent>()
    val outputEvent = _outputEvent.asSharedFlow()

    fun onTapStartButton() {
        switchButtonIconAndRevert(type = OutputEvent.ShowGame)
    }

    fun onTapRankingButton() {
        switchButtonIconAndRevert(type = OutputEvent.ShowRanking)
    }

    fun onTapHowToPlayButton() {
        switchButtonIconAndRevert(type = OutputEvent.ShowTutorial)
    }

    fun onTapSettingButton() {
        viewModelScope.launch {
            _outputEvent.emit(OutputEvent.ShowSetting)
        }
    }

    private fun switchButtonIconAndRevert(type: OutputEvent) {
        // TODO: ウエスタン風な銃声の再生
        // 対象のボタンに弾痕の画像を表示
        when (type) {
            OutputEvent.ShowGame -> {
                _state.value = _state.value.copy(
                    startButtonImageResourceId = R.drawable.bullets_hole
                )
            }
            OutputEvent.ShowRanking -> {
                _state.value = _state.value.copy(
                    rankingButtonImageResourceId = R.drawable.bullets_hole
                )
            }
            OutputEvent.ShowTutorial -> {
                _state.value = _state.value.copy(
                    howToPlayButtonImageResourceId = R.drawable.bullets_hole
                )
            }
            else -> {}
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
                    OutputEvent.ShowGame -> {
                        _outputEvent.emit(OutputEvent.ShowGame)
                    }
                    OutputEvent.ShowRanking -> {
                        _outputEvent.emit(OutputEvent.ShowRanking)
                    }
                    OutputEvent.ShowTutorial -> {
                        _outputEvent.emit(OutputEvent.ShowTutorial)
                    }
                    else -> {}
                }
            }
        }, 500)
    }
}