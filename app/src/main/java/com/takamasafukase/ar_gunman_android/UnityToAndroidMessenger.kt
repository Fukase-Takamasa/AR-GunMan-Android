package com.takamasafukase.ar_gunman_android

import android.util.Log

object UnityToAndroidMessenger {
    interface MessageReceiverFromUnity {
        fun onMessageReceivedFromUnity(message: String)
    }
    private var receiver: MessageReceiverFromUnity? = null

    fun sendMessage(message: String) {
        receiver?.onMessageReceivedFromUnity(message)
        Log.d("Android", "ログAndroid: UnityToAndroidMessenger.onMessageReceivedFromUnity message: $message")
    }
}