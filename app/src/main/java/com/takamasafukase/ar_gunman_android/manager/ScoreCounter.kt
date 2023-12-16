package com.takamasafukase.ar_gunman_android.manager

import com.takamasafukase.ar_gunman_android.model.WeaponType
import com.takamasafukase.ar_gunman_android.utility.ScoreCalculator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScoreCounter(
    private val scoreCalculator: ScoreCalculator
) {
    private val currentTotalScoreFlow = MutableStateFlow(0.0)
    val currentTotalScore = currentTotalScoreFlow.asStateFlow()

    fun addScore(weaponType: WeaponType) {
        val currentTotalScore = scoreCalculator.getTotalScore(
            currentScore = currentTotalScoreFlow.value,
            weaponType = weaponType
        )
        CoroutineScope(Dispatchers.Default).launch {
            currentTotalScoreFlow.emit(currentTotalScore)
        }
    }
}