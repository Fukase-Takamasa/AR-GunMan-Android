package com.takamasafukase.ar_gunman_android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GameScreen(
    onClose: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("Game")
        TextButton(onClick = {
            onClose()
        }) {
            Text("戻る")
        }
    }
}