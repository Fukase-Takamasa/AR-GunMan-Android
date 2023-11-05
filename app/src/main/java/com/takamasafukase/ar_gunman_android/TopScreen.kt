package com.takamasafukase.ar_gunman_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopScreen(
    viewModel: TopViewModel,
    toGame: () -> Unit,
    toSetting: () -> Unit,
) {
    var isShowRankingDialog by remember { mutableStateOf(false) }
    var isShowTutorialDialog by remember { mutableStateOf(false) }
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val state = viewModel.state.collectAsState()
    
    val outputEvent = viewModel.outputEvent.collectAsState(initial = null)

    // ViewModelからのOutput方向のイベントを購読
    LaunchedEffect(outputEvent.value) {
        outputEvent.value?.let {
            when (it) {
                TopViewModel.OutputEvent.ShowGame -> {
                    // ゲーム画面へ遷移
                    toGame()
                }
                TopViewModel.OutputEvent.ShowRanking -> {
                    // ランキング画面（ダイアログ）を表示
                    isShowRankingDialog = true
                }
                TopViewModel.OutputEvent.ShowTutorial -> {
                    // チュートリアル画面（ダイアログ）を表示
                    isShowTutorialDialog = true
                }
                TopViewModel.OutputEvent.ShowSetting -> {
                    // 設定画面へ遷移
                    toSetting()
                }
            }
        }
    }

    // UIの構築
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.goldLeaf)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 60.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                SettingButton(
                    screenHeight = screenHeight,
                    onTap = {
                        viewModel.onTapSettingButton()
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
                        iconResourceId = state.value.startButtonImageResourceId,
                        onTap = {
                            viewModel.onTapStartButton()
                        }
                    )
                    CustomIconButton(
                        screenHeight = screenHeight,
                        title = "Ranking",
                        iconResourceId = state.value.rankingButtonImageResourceId,
                        onTap = {
                            viewModel.onTapRankingButton()
                        }
                    )
                    CustomIconButton(
                        screenHeight = screenHeight,
                        title = "HowToPlay",
                        iconResourceId = state.value.howToPlayButtonImageResourceId,
                        onTap = {
                            viewModel.onTapHowToPlayButton()
                        }
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                PistolImage()
            }
        }

        // ランキングダイアログ
        if (isShowRankingDialog) {
            val rankingViewModel = RankingViewModel()
            RankingScreen(
                viewModel = rankingViewModel,
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
            fontSize = (screenHeight * 0.2).sp, // iOSだと固定で100
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "-GunMan",
            fontSize = (screenHeight * 0.16).sp,  // iOSだと固定で80
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
            fontSize = (screenHeight * 0.056).sp, // iOSだと固定で28
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
    iconResourceId: Int,
    onTap: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TargetImage(
            resourceId = iconResourceId,
            screenHeight = screenHeight,
        )
        TextButton(onClick = {
            onTap()
        }) {
            Text(
                text = title,
                fontSize = (screenHeight * 0.1).sp, // iOSだと固定で50
                fontWeight = FontWeight.Bold,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                color = colorResource(id = R.color.blackSteel)
            )
        }
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

@Composable
fun TargetImage(resourceId: Int, screenHeight: Int) {
    val size = (screenHeight * 0.09).dp
    Image(
        painter = painterResource(id = resourceId),
        contentDescription = "Target icon",
        modifier = Modifier
            .size(size = size)
    )
}