package com.takamasafukase.ar_gunman_android.utility

import android.util.Log

class DebugLogUtil {
    companion object {
        fun print(text: String) {
            Log.d("Android", "ログAndroid: $text")
        }
    }
}