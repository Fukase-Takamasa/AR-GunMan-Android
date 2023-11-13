package com.takamasafukase.ar_gunman_android

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayer.UnitySendMessage
import com.unity3d.player.UnityPlayerActivity

class GameActivity : UnityPlayerActivity() {
    private lateinit var audioManager: AudioManager
    private var unityPlayer: UnityPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        audioManager = AudioManager(context = application)
        unityPlayer = UnityPlayer(this)
        window.clearFlags(1024)

        findViewById<LinearLayout>(R.id.unity)?.addView(
            unityPlayer, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        unityPlayer?.requestFocus()

        Handler(Looper.getMainLooper()).postDelayed({
            UnitySendMessage("XR Origin", "ShowTallSphereToOrigin", "message from Android")
            Log.d("Android", "ログAndroid: UnitySendMessageしました！！！！")
        }, 5000)
    }

    // Notify Unity of the focus change.
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        unityPlayer?.windowFocusChanged(hasFocus)
    }

    override fun onResume() {
        super.onResume()
        unityPlayer?.resume()
    }
}