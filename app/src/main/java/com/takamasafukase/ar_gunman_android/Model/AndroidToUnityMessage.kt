package com.takamasafukase.ar_gunman_android.Model

import kotlinx.serialization.Serializable

@Serializable
data class AndroidToUnityMessage(
    val eventType: AndroidToUnityMessageEventType,
    val weaponType: WeaponType,
)

@Serializable
enum class AndroidToUnityMessageEventType {
    SHOW_WEAPON,
    FIRE_WEAPON,
}

@Serializable
enum class WeaponType {
    PISTOL,
    BAZOOKA,
}