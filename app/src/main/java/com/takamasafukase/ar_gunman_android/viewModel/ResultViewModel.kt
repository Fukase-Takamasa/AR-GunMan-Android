package com.takamasafukase.ar_gunman_android.viewModel

import android.app.Application
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.takamasafukase.ar_gunman_android.R
import com.takamasafukase.ar_gunman_android.entity.Ranking
import com.takamasafukase.ar_gunman_android.manager.AudioManager
import com.takamasafukase.ar_gunman_android.repository.RankingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class ResultViewState(
    val rankings: List<Ranking>,
    val isShowNameRegisterDialog: Boolean,
    val isShowButtons: Boolean,
)

class ResultViewModel(
    app: Application,
    private val audioManager: AudioManager,
) : AndroidViewModel(app) {
    private val _state = MutableStateFlow(
        ResultViewState(
            rankings = listOf(),
            isShowNameRegisterDialog = false,
            isShowButtons = false,
        )
    )
    val state = _state.asStateFlow()
    private val rankingRepository = RankingRepository()
    private val rankingListFlow = MutableStateFlow<List<Ranking>>(value = listOf())
    val rankingListEvent = rankingListFlow.asStateFlow()

    init {
        getRankings()

        viewModelScope.launch {
            _state
                .map { it.rankings }
                .collect {
                    rankingListFlow.value = it
                }
        }
    }

    fun onViewDidAppear() {
        // 結果画面と名前登録ダイアログの出現音声を再生
        audioManager.playSound(R.raw.ranking_appear)

        Handler(Looper.getMainLooper()).postDelayed({
            // 0.5秒後に名前登録ダイアログを表示させる指示を流す
            _state.value = _state.value.copy(isShowNameRegisterDialog = true)
        }, 500)
    }

    fun onCloseNameRegisterDialog() {
        _state.value = _state.value.copy(isShowNameRegisterDialog = false)

        Handler(Looper.getMainLooper()).postDelayed({
            // 0.1秒後にボタンの出現アニメーションを開始させる
            _state.value = _state.value.copy(isShowButtons = true)
        }, 100)
    }

    private fun getRankings() {
        rankingRepository.getRankings(
            onData = {
                _state.value = _state.value.copy(rankings = it)
            },
            onError = {
                // Broadcastでエラーを通知して最上階層でアラートダイアログ表示させる
                val intent = Intent("ERROR_EVENT")
                intent.putExtra("errorMessage", it.message)
                LocalBroadcastManager
                    .getInstance(getApplication<Application>())
                    .sendBroadcast(intent)
            }
        )
    }
}