package com.takamasafukase.ar_gunman_android.viewModel

import com.takamasafukase.ar_gunman_android.entity.Ranking
import com.takamasafukase.ar_gunman_android.repository.RankingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class RankingViewState(
    val rankings: List<Ranking>,
)

class RankingViewModel {
    private val _state = MutableStateFlow(RankingViewState(listOf()))
    val state = _state.asStateFlow()
    private val rankingRepository = RankingRepository()

    init {
        getRankings()
    }

    private fun getRankings() {
        rankingRepository.getDummyRankings(
            onData = {
                _state.value = _state.value.copy(rankings = it)
            },
            onError = {}
        )
    }
}