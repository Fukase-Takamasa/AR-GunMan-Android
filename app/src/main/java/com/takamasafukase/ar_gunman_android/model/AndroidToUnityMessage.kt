package com.takamasafukase.ar_gunman_android.model

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