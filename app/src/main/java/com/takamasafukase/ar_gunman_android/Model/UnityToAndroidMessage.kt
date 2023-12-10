package com.takamasafukase.ar_gunman_android.Model

import kotlinx.serialization.Serializable

@Serializable
data class UnityToAndroidMessage(
    val eventType: UnityToAndroidMessageEventType,
)

@Serializable(with = UnityToAndroidMessageEventTypeSerializer::class)
enum class UnityToAndroidMessageEventType {
    TARGET_HIT,
}