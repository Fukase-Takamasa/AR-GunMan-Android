package com.takamasafukase.ar_gunman_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopScreen(
    toSetting: () -> Unit,
    toGame: () -> Unit,
) {
    var isShowRankingDialog by remember { mutableStateOf(false) }
    var isShowTutorialDialog by remember { mutableStateOf(false) }
    val screenHeight = LocalConfiguration.current.screenHeightDp

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.goldLeaf)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 60.dp)
//                .padding(40.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                SettingButton(
                    screenHeight = screenHeight,
                    onTap = {
                        toSetting()
                    })
                Spacer(modifier = Modifier.weight(1f))
                TitleText(screenHeight = screenHeight)
            }
            Row {
                Column(
                    modifier = Modifier
                ) {
                    CustomIconButton(
                        screenHeight = screenHeight,
                        title = "Start",
                        onTap = {
                            toGame()
                        }
                    )
                    CustomIconButton(
                        screenHeight = screenHeight,
                        title = "Ranking",
                        onTap = {
                            isShowRankingDialog = true
                        }
                    )
                    CustomIconButton(
                        screenHeight = screenHeight,
                        title = "HowToPlay",
                        onTap = {
                            isShowTutorialDialog = true
                        }
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                PistolImage()
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

@Composable
fun TitleText(screenHeight: Int) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "AR",
//            fontSize = 100.sp,
            fontSize = (screenHeight * 0.2).sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "-GunMan",
//            fontSize = 80.sp,
            fontSize = (screenHeight * 0.16).sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun SettingButton(
    screenHeight: Int,
    onTap: () -> Unit,
) {
    TextButton(onClick = {
        onTap()
    }) {
        Text(
            text = "Settings",
//            fontSize = 28.sp,
            fontSize = (screenHeight * 0.056).sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(textDecoration = TextDecoration.Underline),
            color = colorResource(id = R.color.blackSteel)
        )
    }
}

@Composable
fun CustomIconButton(
    screenHeight: Int,
    title: String,
    onTap: () -> Unit,
//    icon: 
) {
    TextButton(onClick = {
        onTap()
    }) {
        Text(
            text = title,
//            fontSize = 50.sp,
            fontSize = (screenHeight * 0.1).sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(textDecoration = TextDecoration.Underline),
            color = colorResource(id = R.color.blackSteel)
        )
    }
}

@Composable
fun PistolImage() {
    Image(
        painter = painterResource(id = R.drawable.top_page_gun_icon),
        contentDescription = "Automatic Pistol Icon",
        modifier = Modifier
            .size(width = 300.dp, height = 200.dp)
    )
}