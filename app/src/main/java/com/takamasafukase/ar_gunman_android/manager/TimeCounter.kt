package com.takamasafukase.ar_gunman_android.manager

import com.takamasafukase.ar_gunman_android.const.GameConst
import com.takamasafukase.ar_gunman_android.utility.TimeCountUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class TimeCounter(
    private val timeCountUtil: TimeCountUtil
) {
    private val countChangedFlow = MutableStateFlow(value = GameConst.timeCount)
    val countChanged: Flow<Double> = countChangedFlow.asStateFlow()

    private val countEndedFlow = MutableSharedFlow<Unit>()
    val countEnded: Flow<Unit> = countEndedFlow.asSharedFlow()

    private var timerJob: Job? = null

    init {
        CoroutineScope(Dispatchers.Default).launch {
            countChanged
                .filter { it <= 0 }
                .collect {
                    countEndedFlow.emit(Unit)
                }
        }
    }

    fun startTimer() {
        timerJob = CoroutineScope(Dispatchers.Main).launch {
            // 0.01秒間隔で更新されるタイマーを作成
            timeCountUtil.createFlowTimer(GameConst.timerUpdateInterval)
                .collect { remainingValue: Double ->
                    // 返却された残り時間の値を流す
                    countChangedFlow.value = remainingValue
                }
        }
    }

    fun disposeTimer() {
        timerJob?.cancel()
        timerJob = null
    }
}