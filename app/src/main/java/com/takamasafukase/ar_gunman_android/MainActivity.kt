package com.takamasafukase.ar_gunman_android

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.takamasafukase.ar_gunman_android.manager.AudioManager
import com.takamasafukase.ar_gunman_android.view.game.GameActivity
import com.takamasafukase.ar_gunman_android.viewModel.TopViewModel
import com.takamasafukase.ar_gunman_android.ui.theme.ARGunManAndroidTheme
import com.takamasafukase.ar_gunman_android.utility.ErrorAlertDialog
import com.takamasafukase.ar_gunman_android.view.result.ResultScreen
import com.takamasafukase.ar_gunman_android.view.setting.SettingScreen
import com.takamasafukase.ar_gunman_android.view.top.TopScreen
import com.takamasafukase.ar_gunman_android.viewModel.ResultViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val audioManager = AudioManager(context = application)

        setContent {
            ARGunManAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    RootCompose(audioManager = audioManager)
                }
            }
        }
    }
}

@Composable
fun RootCompose(
    audioManager: AudioManager
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    var receivedErrorMessage by remember { mutableStateOf<String?>(null) }

    NavHost(
        navController = navController,
        startDestination = "top",
    ) {
        composable("top") {
            val viewModel = TopViewModel(audioManager = audioManager)
            TopScreen(
                viewModel = viewModel,
                toSetting = {
                    navController.navigate("setting")
                },
                toGame = {
                    navController.navigate("game")
                },
            )
        }
        composable("setting") {
            SettingScreen(
                toDeveloperContact = {
                    // TODO: WebViewの表示
                },
                toPrivacyPolicy = {
                    // TODO: WebViewの表示
                },
                onClose = {
                    navController.navigate("top")
                }
            )
        }
        activity(route = "game") {
            activityClass = GameActivity::class
        }
        composable(
            route = "result/{totalScore}",
            arguments = listOf(
                navArgument("totalScore") {
                    type = NavType.StringType
                }
            )
        ) {
            val totalScore = it.arguments?.getString("totalScore") ?: "0.0"
            ResultScreen(
                viewModel = ResultViewModel(
                    app = Application(),
                    audioManager = audioManager,
                ),
                totalScore = totalScore.toDouble(),
                onReplay = {
                    navController.navigate("game")
                },
                toHome = {
                    navController.navigate("top")
                }
            )
        }
    }

    // 未表示のエラーメッセージがあればアラートで表示
    if (receivedErrorMessage != null) {
        ErrorAlertDialog(
            onDismissRequest = {
                // 閉じる時にエラーメッセージをリセットする
                receivedErrorMessage = null
            },
            message = receivedErrorMessage
        )
    }

    val errorNotificationHandler = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val errorMessage = intent?.getStringExtra("errorMessage")
            if (errorMessage != null) {
                // 通知で受け取ったエラーメッセージをアラートで表示させる為に格納
                receivedErrorMessage = errorMessage
            }
        }
    }
    val navigationNotificationHandler = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("Android", "ログAndroid: MainActivity onReceive navigationNotificationHandler")
            val destinationNameText = intent?.getStringExtra("destination")
            if (destinationNameText != null) {
                if (destinationNameText == "result") {
                    val totalScore = intent.getStringExtra("totalScore")
                    Log.d("Android", "ログAndroid: MainActivity onReceive navController.navigate($destinationNameText/$totalScore)を実行します")
                    // 受け取ったスコアと一緒に遷移指示を流す
                    navController.navigate("$destinationNameText/$totalScore")
                }else {
                    Log.d("Android", "ログAndroid: MainActivity onReceive navController.navigate($destinationNameText)を実行します")
                    // 通知で受け取ったdestinationに遷移
                    navController.navigate(destinationNameText)
                }
            }
        }
    }
    DisposableEffect(Unit) {
        // 通知受信時の処理を登録
        LocalBroadcastManager.getInstance(context).registerReceiver(
            errorNotificationHandler, IntentFilter("ERROR_EVENT")
        )
        LocalBroadcastManager.getInstance(context).registerReceiver(
            navigationNotificationHandler, IntentFilter("NAVIGATION_EVENT")
        )

        // onDisposeで通知受信時の処理を解除
        onDispose {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(
                errorNotificationHandler
            )
            LocalBroadcastManager.getInstance(context).unregisterReceiver(
                navigationNotificationHandler
            )
        }
    }
}