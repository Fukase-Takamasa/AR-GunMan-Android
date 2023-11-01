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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.goldLeaf)
    ) {
        Column {
            Row {
                SettingButton(onTap = {
                    toSetting()
                })
                TitleText()
            }
            Row {
                Column(
                    modifier = Modifier
                ) {
                    CustomIconButton(
                        title = "Start",
                        onTap = {
                            toGame()
                        }
                    )
                    CustomIconButton(
                        title = "Ranking",
                        onTap = {
                            isShowRankingDialog = true
                        }
                    )
                    CustomIconButton(
                        title = "HowToPlay",
                        onTap = {
                            isShowTutorialDialog = true
                        }
                    )
                }
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
fun TitleText() {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "AR",
            fontSize = 100.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "-GunMan",
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun SettingButton(
    onTap: () -> Unit,
) {
    TextButton(onClick = {
        onTap()
    }) {
        Text(
            text = "Settings",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(textDecoration = TextDecoration.Underline),
            color = colorResource(id = R.color.blackSteel)
        )
    }
}

@Composable
fun CustomIconButton(
    title: String,
    onTap: () -> Unit,
//    icon: 
) {
    TextButton(onClick = {
        onTap()
    }) {
        Text(
            text = title,
            fontSize = 50.sp,
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