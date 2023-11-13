package com.takamasafukase.ar_gunman_android

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayer.UnitySendMessage
import com.unity3d.player.UnityPlayerActivity

class GameActivity : UnityPlayerActivity() {
    private var unityPlayer: UnityPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        unityPlayer = UnityPlayer(this)
        window.clearFlags(1024)

//        // FrameLayoutの作成と設定
//        val frameLayout = FrameLayout(this).apply {
//            layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//        }
//        setContentView(frameLayout)
//
//        // UnityPlayerの追加
//        unityPlayer?.let {
//            frameLayout.addView(it, ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            ))
//        }

//        // ComposeViewの作成と設定
//        val composeView = ComposeView(this).apply {
//            layoutParams = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT
//            )
//            background = null // 透明背景
//
//            // Jetpack ComposeのUIをセット
//            setContent {
//                // ライフサイクルオーナーの設定
//                ViewTreeLifecycleOwner.set(this, this@GameActivity)
//                GameScreen(
//                    toWeaponChange = {
//
//                    },
//                    toResult = {
//
//                    }
//                )
//            }
//        }
//        frameLayout.addView(composeView)

        // ComposeViewの作成と設定
        val composeView = ComposeView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            background = null // 透明背景

            // Jetpack ComposeのUIをセット
            setContent {
                GameScreen(
                    toWeaponChange = {

                    },
                    toResult = {

                    }
                )
            }
        }

        // ActivityのルートビューにComposeViewを追加
        findViewById<LinearLayout>(R.id.unity)?.addView(composeView)

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