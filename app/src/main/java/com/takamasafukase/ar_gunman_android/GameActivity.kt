package com.takamasafukase.ar_gunman_android

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.ComposeView
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayer.UnitySendMessage

class GameActivity : ComponentActivity() {
    private lateinit var unityPlayer: UnityPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        unityPlayer = UnityPlayer(this)
//        window.clearFlags(1024)

        setContentView(R.layout.activity_game)

        // FrameLayoutにUnityViewを追加
        val frameLayout = findViewById<FrameLayout>(R.id.unity)
        frameLayout.addView(unityPlayer.view, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)

        //        findViewById<FrameLayout>(R.id.unity)?.addView(
//            unityPlayer, ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//        )

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