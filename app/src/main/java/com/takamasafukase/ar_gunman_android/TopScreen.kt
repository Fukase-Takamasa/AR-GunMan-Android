package com.takamasafukase.ar_gunman_android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource

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
            TextButton(onClick = {
                toSetting()
            }) {
                Text("Settings")
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
        }

        if (isShowRankingDialog) {
            val viewModel = RankingViewModel()
            RankingScreen(
                viewModel = viewModel,
                onClose = {
                    isShowRankingDialog = false
                }
            )
        }

        if (isShowTutorialDialog) {
            TutorialScreen(
                onClose = {
                    isShowTutorialDialog = false
                }
            )
        }
    }
}