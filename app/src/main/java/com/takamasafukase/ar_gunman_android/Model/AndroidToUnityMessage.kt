package com.takamasafukase.ar_gunman_android.Model

import kotlinx.serialization.Serializable

@Serializable
data class AndroidToUnityMessage(
    val eventType: AndroidToUnityMessageEventType,
    val weaponType: WeaponType,
)

@Serializable(with = AndroidToUnityMessageEventTypeSerializer::class)
enum class AndroidToUnityMessageEventType {
    SHOW_WEAPON,
    FIRE_WEAPON,
}

@Serializable(with = WeaponTypeSerializer::class)
enum class WeaponType {
    PISTOL,
    BAZOOKA,
    RIFLE,
    SHOT_GUN,
    SNIPER_RIFLE,
    MINI_GUN,
}