package com.takamasafukase.ar_gunman_android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ResultScreen(
    onReplay: () -> Unit,
    toHome: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("Result")
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