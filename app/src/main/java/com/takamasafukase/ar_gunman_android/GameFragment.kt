package com.takamasafukase.ar_gunman_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment


class GameFragment : Fragment() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        // XMLからViewをインフレート
//        val view = inflater.inflate(R.layout.fragment_game, container, false)
//
//        // Unityのビューを設定（必要に応じてカスタマイズ）
////        val unityView = view.findViewById<View>(R.id.unity)
//        // Unity関連の設定をここで行う
//
//        // ComposeViewを作成
//        val composeView = ComposeView(requireContext()).apply {
//            layoutParams = FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT
//            ).also {
//                // 必要に応じてレイアウトの調整
//            }
//
//            // Jetpack ComposeのUIをセット
//            setContent {
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
//
//        // ComposeViewをFrameLayoutに追加
//        (view as FrameLayout).addView(composeView)
//
//        return view
//    }

}