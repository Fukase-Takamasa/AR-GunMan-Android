package com.takamasafukase.ar_gunman_android.manager

import com.takamasafukase.ar_gunman_android.model.WeaponType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 弾倉（マガジン）の役割を果たす
// 弾の数の増減管理や発射可否、リロード可否を管理
// クラス名はMagazineよりもBulletsHolderの方がミリオタじゃなくても誰でも分かるのでこちらにしている
class BulletsHolder(
    private val type: WeaponType
) {
    private val bulletsCountFlow = MutableStateFlow(type.bulletsCapacity)
    val bulletsCountChanged = bulletsCountFlow.asStateFlow()

    val canFire: Boolean = (bulletsCountFlow.value > 0)
    val canReload: Boolean = (bulletsCountFlow.value <= 0)

    fun decreaseBulletsCount() {
        CoroutineScope(Dispatchers.Default).launch {
            bulletsCountFlow.emit(
                bulletsCountFlow.value - 1
            )
        }
    }

    fun refillBulletsCount() {
        CoroutineScope(Dispatchers.Default).launch {
            bulletsCountFlow.emit(
                type.bulletsCapacity
            )
        }
    }
}