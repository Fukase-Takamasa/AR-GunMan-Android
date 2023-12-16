package com.takamasafukase.ar_gunman_android.manager

import com.takamasafukase.ar_gunman_android.R
import com.takamasafukase.ar_gunman_android.model.WeaponType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrentWeapon(
    private val initialType: WeaponType,
    private val audioManager: AudioManager,
) {
    private val weaponTypeFlow = MutableStateFlow(initialType)
    val weaponTypeChanged = weaponTypeFlow.asStateFlow()

    private var bulletsHolder = BulletsHolder(type = initialType)
    val bulletsCountChanged = bulletsHolder.bulletsCountChanged

    private val firedFlow = MutableSharedFlow<Unit>()
    val fired = firedFlow.asSharedFlow()

    fun fire() {
        if (!bulletsHolder.canFire) {
            // 弾切れ状態でバズーカ以外なら弾切れ音声を再生してreturn
            if (weaponTypeFlow.value != WeaponType.BAZOOKA) {
                audioManager.playSound(R.raw.pistol_out_bullets)
            }
            return
        }
        // 現在の武器の発砲音声を再生
        audioManager.playSound(weaponTypeFlow.value.firingSoundResourceId)
        // 弾数を1つ減らす
        bulletsHolder.decreaseBulletsCount()
        CoroutineScope(Dispatchers.Default).launch {
            // 発射されたことを通知
            firedFlow.emit(Unit)
        }
    }

    fun reload() {
        // 弾が0じゃない時はreturn
        if (!bulletsHolder.canReload) {
            return
        }
        if (weaponTypeFlow.value != WeaponType.BAZOOKA) {
            // バズーカじゃない場合はリロード音声を再生
            audioManager.playSound(R.raw.pistol_reload)
        }
        // 弾数を補充
        bulletsHolder.refillBulletsCount()
    }

    fun changeWeaponTypeTo(newType: WeaponType) {
        CoroutineScope(Dispatchers.Default).launch {
            // 新しいタイプを通知
            weaponTypeFlow.emit(newType)
        }
        // 武器のセット時の音声を再生
        audioManager.playSound(newType.setSoundResourceId)
        // 新しいタイプで弾倉を再度初期化（武器によって装弾数などが異なるため）
        bulletsHolder = BulletsHolder(type = newType)
    }
}