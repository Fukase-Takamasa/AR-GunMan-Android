package com.takamasafukase.ar_gunman_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.takamasafukase.ar_gunman_android.ui.theme.ARGunManAndroidTheme

class MainActivity : ComponentActivity() {
    private lateinit var audioManager: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        audioManager = AudioManager(context = application)

        setContent {
            ARGunManAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    MyApp()
                }
            }
        }
    }

    @Composable
    fun MyApp() {
        val navController = rememberNavController()

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
//            activity(route = "game") {
//                activityClass = GameActivity::class
//            }
            composable(route = "game") {
                GameScreen(
                    toWeaponChange = {

                    },
                    toResult = {

                    }
                )
            }
            composable("result") {
                ResultScreen(
                    onReplay = {
                        navController.navigate("game")
                    },
                    toHome = {
                        navController.navigate("top")
                    }
                )
            }
        }
    }
}