package com.takamasafukase.ar_gunman_android

import android.util.Log
import java.lang.ref.WeakReference

object UnityToAndroidMessenger {
    interface MessageReceiverFromUnity {
        fun onMessageReceivedFromUnity(message: String)
    }
    var receiver: WeakReference<MessageReceiverFromUnity>? = null

    fun sendMessage(message: String) {
        receiver?.get()?.onMessageReceivedFromUnity(message)
        Log.d("Android", "ログAndroid: UnityToAndroidMessenger.onMessageReceivedFromUnity message: $message")
    }
}