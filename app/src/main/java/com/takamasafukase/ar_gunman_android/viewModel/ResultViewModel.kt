package com.takamasafukase.ar_gunman_android.viewModel

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.takamasafukase.ar_gunman_android.R
import com.takamasafukase.ar_gunman_android.entity.Ranking
import com.takamasafukase.ar_gunman_android.manager.AudioManager
import com.takamasafukase.ar_gunman_android.repository.RankingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ResultViewState(
    val rankings: List<Ranking>,
)

class ResultViewModel(
    app: Application,
    private val audioManager: AudioManager,
) : AndroidViewModel(app) {
    private val _state = MutableStateFlow(ResultViewState(listOf()))
    val state = _state.asStateFlow()
    private val rankingRepository = RankingRepository()

    init {
        getRankings()
    }

    fun onViewDidAppear() {
        // 結果画面と名前登録ダイアログの出現音声を再生
        audioManager.playSound(R.raw.ranking_appear)
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