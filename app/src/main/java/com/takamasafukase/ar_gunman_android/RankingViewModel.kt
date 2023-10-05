package com.takamasafukase.ar_gunman_android

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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