package com.takamasafukase.ar_gunman_android.view.tutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.takamasafukase.ar_gunman_android.utility.CustomDialog

@Composable
fun TutorialScreen(
    onClose: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp

    CustomDialog(
        onDismissRequest = onClose,
        size = DpSize(
            width = Dp((screenWidth * 0.6).toFloat()),
            height = Dp((screenHeight * 0.8).toFloat())
        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Text("Tutorial")
                TextButton(onClick = {
                    onClose()
                }) {
                    Text("戻る")
                }
            }
        }
    )
}