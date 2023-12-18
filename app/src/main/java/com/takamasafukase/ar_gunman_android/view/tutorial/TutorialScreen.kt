package com.takamasafukase.ar_gunman_android.view.tutorial

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.takamasafukase.ar_gunman_android.R
import com.takamasafukase.ar_gunman_android.const.TutorialConst
import com.takamasafukase.ar_gunman_android.utility.CustomDialog

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TutorialScreen(
    onClose: () -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val pagerViewHeight = screenHeight * 0.65
    val pageTitleTextHeight = screenHeight * 0.084
    val pageDescriptionTextHeight = screenHeight * 0.096
    val pageIndicatorHeight = screenHeight * 0.072
    val buttonHeight = screenHeight * 0.15
    val dialogHeight = pagerViewHeight + pageIndicatorHeight + buttonHeight
    val dialogWidth = pagerViewHeight * 1.33
    val pagerState = rememberPagerState()

    CustomDialog(
        onDismissRequest = onClose,
        size = DpSize(
            width = dialogWidth.dp,
            height = dialogHeight.dp,
        ),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.Transparent)
            ) {
                HorizontalPager(
                    pageCount = 3,
                    state = pagerState,
                    modifier = Modifier
                        .height((screenHeight * 0.6).dp)
                        .border(
                            width = 5.dp,
                            color = colorResource(id = R.color.goldLeaf),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(20.dp)
                        )
                        // 見た目上の角丸に合わせて、コンテンツ描画の切り取り領域も同じ角丸の値に設定する
                        // これをやらないとスクロールした時に角丸部分に中身のコンテンツがはみ出して見えてしまう。
                        .clip(RoundedCornerShape(20.dp))
                ) { pageIndex ->
                    val content = TutorialConst.pageContents[pageIndex]
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 16.dp)
                    ) {
                        Image(
                            // TODO: あとでAnimatedImageSwitcherに変えてパラパラアニメーションする
                            painter = painterResource(id = content.imageResourceIds[0]),
                            contentDescription = "Tutorial image",
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 4.dp)
                        )
                        Text(
                            text = content.titleText,
                            color = colorResource(id = R.color.blackSteel),
                            fontSize = (screenHeight * 0.06).sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .height(pageTitleTextHeight.dp)
                        )
                        Text(
                            text = content.descriptionText,
                            color = colorResource(id = R.color.blackSteel),
                            fontSize = (screenHeight * 0.04).sp,
                            modifier = Modifier
                                .height(pageDescriptionTextHeight.dp)
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .size(
                            width = 60.dp,
                            height = pageIndicatorHeight.dp
                        )
                )
                TextButton(
                    onClick = {
                        if (pagerState.currentPage == 2) {
                            onClose()
                        } else {
//                        pagerState.scrollToPage(pagerState.currentPage + 1)
                        }
                    },
                ) {
                    Box(
                        modifier = Modifier
                            .size(
                                width = (buttonHeight * 2.3).dp,
                                height = buttonHeight.dp
                            )
                            .background(
                                color = colorResource(id = R.color.goldLeaf),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = colorResource(id = R.color.customBrown1),
                                shape = RoundedCornerShape(20.dp)
                            )
                    ) {
                        val buttonText = if (pagerState.currentPage == 2) "OK" else "NEXT"
                        Text(
                            text = buttonText,
                            color = colorResource(id = R.color.blackSteel),
                            fontSize = (screenHeight * 0.06).sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    )
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
fun TutorialScreenPreview() {
    TutorialScreen(
        onClose = {},
    )
}