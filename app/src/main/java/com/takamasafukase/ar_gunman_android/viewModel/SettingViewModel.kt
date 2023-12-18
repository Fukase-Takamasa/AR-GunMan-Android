package com.takamasafukase.ar_gunman_android.viewModel

import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takamasafukase.ar_gunman_android.const.UrlConst
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SettingViewModel: ViewModel() {
    private val openUrlInBrowserFlow = MutableSharedFlow<String>()
    val openUrlInBrowserEvent = openUrlInBrowserFlow.asSharedFlow()
    private val closePageFlow = MutableSharedFlow<Unit>()
    val closePageEvent = closePageFlow.asSharedFlow()

    fun onTapContactDeveloperButton() {
        viewModelScope.launch {
            openUrlInBrowserFlow.emit(UrlConst.developerContactUrl)
        }
    }

    fun onTapPrivacyPolicyButton() {
        viewModelScope.launch {
            openUrlInBrowserFlow.emit(UrlConst.privacyPolicyUrl)
        }
    }

    fun onTapBackButton() {
        viewModelScope.launch {
            closePageFlow.emit(Unit)
        }
    }
}