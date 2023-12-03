package com.takamasafukase.ar_gunman_android.view.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingScreen(
    toDeveloperContact: () -> Unit,
    toPrivacyPolicy: () -> Unit,
    onClose: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("Settings")
        TextButton(onClick = {
            toDeveloperContact()
        }) {
            Text("Contact Developer")
        }
        TextButton(onClick = {
            toPrivacyPolicy()
        }) {
            Text("Privacy Policy")
        }
        TextButton(onClick = {
            onClose()
        }) {
            Text("Back")
        }
    }
}