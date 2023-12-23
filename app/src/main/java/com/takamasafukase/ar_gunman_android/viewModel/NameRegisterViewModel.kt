package com.takamasafukase.ar_gunman_android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takamasafukase.ar_gunman_android.entity.Ranking
import com.takamasafukase.ar_gunman_android.repository.RankingRepository
import com.takamasafukase.ar_gunman_android.utility.DebugLogUtil
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
    private val closeDialogFlow = MutableSharedFlow<Unit>()
    val closeDialogEvent = closeDialogFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            params.rankingListFlow
                .filter { it.isNotEmpty() }
                .collect {
                    _state.value = _state.value.copy(
                        rankText = createTemporaryRankText(params)
                    )
                }
        }
    }

    fun onChangeNameText(text: String) {
        _state.value = _state.value.copy(nameInputText = text)
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
                // ダイアログを閉じる指示を流す
                viewModelScope.launch {
                    closeDialogFlow.emit(Unit)
                }
            }
        )
    }

    // 何位中/何位の表示テキストを作成
    private fun createTemporaryRankText(params: Params): String {
        return "${getTemporaryRank(params)} / ${params.rankingListFlow.value.size}"
    }

    // 取得したランキング順位の中から今回のスコア（まだ未登録）を差し込むと暫定何位になるかを計算して返却
    private fun getTemporaryRank(params: Params): Int {
        // スコアの高い順になっているリストの中から最初にtotalScoreよりも小さいランクのindex番号を取得
        // それがちょうど最終的に今回のtotalScoreを挿入した場合のランク（1始まり）になる
        val temporaryRank = params.rankingListFlow.value.indexOfFirst {
            it.score <= params.totalScore
        }
        return temporaryRank + 1
    }
}