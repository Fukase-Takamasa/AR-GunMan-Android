package com.takamasafukase.ar_gunman_android.view.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.takamasafukase.ar_gunman_android.R
import com.takamasafukase.ar_gunman_android.viewModel.SettingViewModel

@Composable
fun SettingScreen(
    viewModel: SettingViewModel,
    onClose: () -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.openUrlInBrowserEvent.collect {
            uriHandler.openUri(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.closePageEvent.collect {
            onClose()
        }
    }

    Surface(
        color = colorResource(id = R.color.goldLeaf),
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, top = 48.dp, end = 32.dp, bottom = 40.dp)
        ) {
            Text(
                text = "Settings",
                color = colorResource(id = R.color.blackSteel),
                fontSize = (screenHeight * 0.12).sp,
                fontWeight = FontWeight.Bold,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextButton(onClick = {
                    viewModel.onTapContactDeveloperButton()
                }) {
                    Text(
                        text = "Contact Developer",
                        color = colorResource(id = R.color.blackSteel),
                        fontSize = (screenHeight * 0.1).sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(textDecoration = TextDecoration.Underline)
                    )
                }
                TextButton(onClick = {
                    viewModel.onTapPrivacyPolicyButton()
                }) {
                    Text(
                        text = "Privacy Policy",
                        color = colorResource(id = R.color.blackSteel),
                        fontSize = (screenHeight * 0.1).sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(textDecoration = TextDecoration.Underline)
                    )
                }
            }
            TextButton(onClick = {
                viewModel.onTapBackButton()
            }) {
                Text(
                    text = "Back",
                    color = colorResource(id = R.color.blackSteel),
                    fontSize = (screenHeight * 0.09).sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
fun ResultScreenPreview() {
    SettingScreen(
        viewModel = SettingViewModel(),
        onClose = {},
    )
}