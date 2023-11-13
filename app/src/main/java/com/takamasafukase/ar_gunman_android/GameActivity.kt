package com.takamasafukase.ar_gunman_android

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayer.UnitySendMessage

class GameActivity : ComponentActivity() {
    private lateinit var unityPlayer: UnityPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        unityPlayer = UnityPlayer(this)

        setContentView(R.layout.activity_game)

        // FrameLayoutにUnityViewを追加
        val frameLayout = findViewById<FrameLayout>(R.id.unity)
        frameLayout.addView(unityPlayer.view, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)

        // ComposeViewを作成してFrameLayoutに追加
        val composeView = ComposeView(this).apply {
            setContent {
                GameScreen(
                    toWeaponChange = {

                    },
                    toResult = {

                    }
                )
            }
        }
        frameLayout.addView(composeView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))

        unityPlayer.requestFocus()
        // UnityPlayerにフォーカスを合わせる

        Handler(Looper.getMainLooper()).postDelayed({
            UnitySendMessage("XR Origin", "ShowTallSphereToOrigin", "message from Android")
            Log.d("Android", "ログAndroid: UnitySendMessageしました！！！！")
        }, 5000)
    }

    // Notify Unity of the focus change.
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        unityPlayer.windowFocusChanged(hasFocus)
    }

    override fun onResume() {
        super.onResume()
        unityPlayer.resume()
    }
}