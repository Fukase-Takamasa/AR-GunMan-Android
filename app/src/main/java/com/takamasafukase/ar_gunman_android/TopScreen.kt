package com.takamasafukase.ar_gunman_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TopScreen(
    toSetting: () -> Unit,
    toGame: () -> Unit,
) {
    var isShowRankingDialog by remember { mutableStateOf(false) }
    var isShowTutorialDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.goldLeaf)
    ) {
        Column {
            Row() {
                TextButton(onClick = {
                    toSetting()
                }) {
                    Text("Settings")
                }
                Text("AR-GunMan")
            }
            Row() {
                Column(
                    modifier = Modifier
                ) {
                    TextButton(onClick = {
                        toGame()
                    }) {
                        Text("Start")
                    }
                    TextButton(onClick = {
//                    toRanking()
                        isShowRankingDialog = true
                    }) {
                        Text("Ranking")
                    }
                    TextButton(onClick = {
                        isShowTutorialDialog = true
                    }) {
                        Text("HowToPlay")
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.top_page_gun_icon),
                    contentDescription = "Automatic Pistol Icon",
                    modifier = Modifier
                        .size(width = 300.dp, height = 200.dp)
                )
            }
        }

        // ランキングダイアログ
        if (isShowRankingDialog) {
            val viewModel = RankingViewModel()
            RankingScreen(
                viewModel = viewModel,
                onClose = {
                    isShowRankingDialog = false
                }
            )
        }

        // チュートリアルダイアログ
        if (isShowTutorialDialog) {
            TutorialScreen(
                onClose = {
                    isShowTutorialDialog = false
                }
            )
        }
    }
}

//@Composable
//fun TitleText() {
//
//}
//
//@Composable
//fun CustomIconButton(
//    onTap: () -> Unit,
//    icon:
//) {
//
//}
//
//@Composable
//fun PistolImage() {
//
//}