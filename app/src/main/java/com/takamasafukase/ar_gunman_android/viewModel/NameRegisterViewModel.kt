package com.takamasafukase.ar_gunman_android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takamasafukase.ar_gunman_android.entity.Ranking
import com.takamasafukase.ar_gunman_android.repository.RankingRepository
import com.takamasafukase.ar_gunman_android.utility.DebugLogUtil
import com.takamasafukase.ar_gunman_android.utility.RankingUtil
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

data class NameRegisterViewState(
    val rankText: String,
    val totalScore: Double,
    val nameInputText: String,
    val isShowLoadingOnRegisterButton: Boolean,
)

class NameRegisterViewModel(
    private val rankingRepository: RankingRepository,
    private val rankingUtil: RankingUtil,
    private val params: Params,
): ViewModel() {
    data class Params(
        val totalScore: Double,
        val rankingListFlow: StateFlow<List<Ranking>>,
    )

    private val _state = MutableStateFlow(value = NameRegisterViewState(
        rankText = "  /  ",
        totalScore = params.totalScore,
        nameInputText = "",
        isShowLoadingOnRegisterButton = false,
    ))
    val state = _state.asStateFlow()
    private val closeDialogFlow = MutableSharedFlow<Ranking?>()
    val closeDialogEvent = closeDialogFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            params.rankingListFlow
                .filter { it.isNotEmpty() }
                .collect {
                    _state.value = _state.value.copy(
                        rankText = rankingUtil.createTemporaryRankText(
                            rankingList = params.rankingListFlow.value,
                            score = params.totalScore,
                        )
                    )
                }
        }
    }

    fun onChangeNameText(text: String) {
        _state.value = _state.value.copy(nameInputText = text)
    }

    fun onTapNoThanksButton() {
        viewModelScope.launch {
            closeDialogFlow.emit(null)
        }
    }

    fun onTapRegisterButton() {
        // ボタン上にインジケータ表示
        _state.value = _state.value.copy(
            isShowLoadingOnRegisterButton = true
        )

        // 入力された名前とスコアで新しいランキングを作成
        val newRanking = Ranking(
            user_name = _state.value.nameInputText,
            score = params.totalScore,
        )

        // 登録POST
        rankingRepository.registerRanking(
            ranking = newRanking,
            onCompleted = {
                // 今回登録したランキングデータと一緒にダイアログを閉じる指示を流す
                viewModelScope.launch {
                    closeDialogFlow.emit(newRanking)
                }
            }
        )
    }
}