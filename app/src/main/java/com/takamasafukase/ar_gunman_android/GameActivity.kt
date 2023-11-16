package com.takamasafukase.ar_gunman_android

import android.hardware.SensorManager
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import com.unity3d.player.UnityPlayer

class GameActivity : ComponentActivity() {
    private var unityPlayer: UnityPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        unityPlayer = UnityPlayer(this)

        setContentView(R.layout.activity_game)

        // FrameLayoutにUnityViewを追加
        val frameLayout = findViewById<FrameLayout>(R.id.unity)
        frameLayout.addView(unityPlayer?.rootView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)

        // ComposeViewを作成してFrameLayoutに追加
        val composeView = ComposeView(this).apply {
            setContent {
                GameScreen(
                    viewModel = GameViewModel(
                        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager,
                    ),
                    toWeaponChange = {

                    },
                    toResult = {

                    }
                )
            }
        }
        frameLayout.addView(composeView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))

        // UnityPlayerにフォーカスを合わせる
        unityPlayer?.requestFocus()
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