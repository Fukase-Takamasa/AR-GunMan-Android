package com.takamasafukase.ar_gunman_android.utility

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorAlertDialog(
    onDismissRequest: () -> Unit,
    title: String = "WHOOPS, SOMETHING WENT WRONG!",
    message: String? = null,
    closeButtonText: String = "CLOSE",
    retryHandler: (() -> Unit)? = null,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(title)
        },
        text = {
            if (message != null) {
                Text(message)
            }
        },
        // 閉じるボタン
        confirmButton = {
            TextButton(
                onClick = onDismissRequest,
            ) {
                Text(closeButtonText)
            }
        },
        // リトライボタン
        dismissButton = {
            // リトライ時の処理が渡された時だけリトライボタンを表示する
            if (retryHandler != null) {
                TextButton(
                    onClick = retryHandler,
                ) {
                    Text("RETRY")
                }
            }
        },
        contentColor = Color.Black,
    )
}