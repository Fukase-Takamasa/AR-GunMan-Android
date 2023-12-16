package com.takamasafukase.ar_gunman_android.model

import kotlinx.serialization.Serializable

@Serializable
data class UnityToAndroidMessage(
    val eventType: UnityToAndroidMessageEventType,
)

@Serializable(with = UnityToAndroidMessageEventTypeSerializer::class)
enum class UnityToAndroidMessageEventType {
    TARGET_HIT,
}