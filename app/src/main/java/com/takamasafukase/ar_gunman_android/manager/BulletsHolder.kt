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

    fun getCanFire() : Boolean {
        return bulletsCountFlow.value > 0
    }
    fun getCanReload() : Boolean {
        return bulletsCountFlow.value <= 0
    }

    fun decreaseBulletsCount() {
        CoroutineScope(Dispatchers.Default).launch {
            bulletsCountFlow.emit(
                bulletsCountFlow.value - 1
            )
        }
    }

    fun refillBulletsCount(withNewWeaponType: WeaponType? = null) {
        CoroutineScope(Dispatchers.Default).launch {
            // 武器が変更された時は新しい武器の装弾数でリロード。変更がない場合は現在の武器の装弾数でリロード。
            val weaponType: WeaponType = withNewWeaponType ?: type
            bulletsCountFlow.emit(
                weaponType.bulletsCapacity
            )
        }
    }
}