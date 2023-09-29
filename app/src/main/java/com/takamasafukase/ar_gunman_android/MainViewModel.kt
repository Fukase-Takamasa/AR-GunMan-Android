package com.takamasafukase.ar_gunman_android

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel {
    private val _state = MutableStateFlow(MainViewState(count = 0))
    val state = _state.asStateFlow()

    fun onTapIncrementButton() {
        _state.value = _state.value.copy(count = _state.value.count + 1)
    }
}