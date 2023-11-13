package com.takamasafukase.ar_gunman_android

import android.util.Log
import com.unity3d.player.UnityPlayer.UnitySendMessage

object UnityPlayerToAndroid {
    var receiver: ICallback? = null

    fun sendMessage(message: String) {
        receiver?.receiveMessage(message)
        Log.d("Android", "ログAndroid: sendMessageが呼ばれた message: $message")
    }

    interface ICallback {
        fun receiveMessage(message: String)
    }
}