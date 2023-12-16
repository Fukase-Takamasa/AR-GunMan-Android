package com.takamasafukase.ar_gunman_android.view.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ResultScreen(
    totalScore: Double,
    onReplay: () -> Unit,
    toHome: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("Result")
        Text("score: $totalScore")
        TextButton(onClick = {
            onReplay()
        }) {
            Text("REPLAY")
        }
        TextButton(onClick = {
            toHome()
        }) {
            Text("HOME")
        }
    }
}