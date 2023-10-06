package com.takamasafukase.ar_gunman_android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TopScreen(
    toGame: () -> Unit,
    toRanking: () -> Unit,
    toTutorial: () -> Unit,
    toSetting: () -> Unit,
) {
    Column() {
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
                toRanking()
            }) {
                Text("Ranking")
            }
            TextButton(onClick = {
                toTutorial()
            }) {
                Text("HowToPlay")
            }
        }
    }
}