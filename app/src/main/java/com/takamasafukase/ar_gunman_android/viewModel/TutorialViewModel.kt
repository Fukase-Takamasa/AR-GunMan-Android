package com.takamasafukase.ar_gunman_android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TutorialViewModel: ViewModel() {
    private val currentPageFlow = MutableStateFlow(value = 0)
    val scrollPageToEventFlow = MutableStateFlow(value = 0)
    val scrollPageToEvent = scrollPageToEventFlow.asStateFlow()
    val closeDialogEventFlow = MutableSharedFlow<Unit>()
    val closeDialogEvent = closeDialogEventFlow.asSharedFlow()
    private val buttonTextFlow = MutableStateFlow(value = "NEXT")
    val buttonText = buttonTextFlow.asStateFlow()

    fun onTapButton() {
        viewModelScope.launch {
            if (currentPageFlow.value == 2) {
                closeDialogEventFlow.emit(Unit)
            }else {
                scrollPageToEventFlow.emit(currentPageFlow.value + 1)
            }
        }
    }

    fun onPageChanged(currentPage: Int) {
        currentPageFlow.value = currentPage

        val buttonText = if (currentPageFlow.value == 2) "OK" else "NEXT"
        buttonTextFlow.value = buttonText
    }
}