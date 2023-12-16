package com.takamasafukase.ar_gunman_android.view.result

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.takamasafukase.ar_gunman_android.R
import com.takamasafukase.ar_gunman_android.entity.Ranking
import com.takamasafukase.ar_gunman_android.model.WeaponType
import com.takamasafukase.ar_gunman_android.view.ranking.RankingListView
import com.takamasafukase.ar_gunman_android.viewModel.ResultViewModel

@Composable
fun ResultScreen(
    viewModel: ResultViewModel,
    totalScore: Double,
    onReplay: () -> Unit,
    toHome: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val state by viewModel.state.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 4.dp)
        ) {
            // 上部のタイトル部分
            TitleView()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 5.dp,
                        color = colorResource(id = R.color.customBrown1),
                        shape = RoundedCornerShape(size = 2.dp)
                    )
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width((screenWidth * 0.465).dp)
                ) {
                    RankingListView(list = state.rankings)
                    if (state.rankings.isEmpty()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                // TODO: あとでRankingListViewは枠線無しのリスト部分だけにした方が汎用性高いかも？
                                // ローディング時も同じ内側の枠線を表示しておく
                                .border(
                                    width = 7.dp,
                                    color = colorResource(id = R.color.goldLeaf),
                                    shape = RoundedCornerShape(size = 3.dp)
                                )
                        ) {
                            CircularProgressIndicator(color = colorResource(id = R.color.paper))
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .width((screenWidth * 0.465).dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .padding(bottom = 3.dp)
                                .border(
                                    width = 7.dp,
                                    color = colorResource(id = R.color.goldLeaf),
                                    shape = RoundedCornerShape(size = 3.dp)
                                )
                                .padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 4.dp)
                        ) {
                            Text(
                                text = "score",
                                color = colorResource(id = R.color.paper),
                                fontSize = 22.sp,
                            )
                            Text(
                                text = "%.3f".format(totalScore),
                                color = colorResource(id = R.color.paper),
                                fontSize = (screenHeight * 0.18).sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 3.dp)
                                .border(
                                    width = 7.dp,
                                    color = colorResource(id = R.color.goldLeaf),
                                    shape = RoundedCornerShape(size = 3.dp)
                                )
                                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = WeaponType.PISTOL.weaponIconResourceId),
                                contentDescription = "Weapon icon",
                                colorFilter = ColorFilter.tint(colorResource(id = R.color.paper)),
                                alpha = 1f,
                                modifier = Modifier
                            )
                            Column(
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxHeight()
                            ) {
                                TextButton(onClick = {
                                    onReplay()
                                }) {
                                    Text(
                                        text = "REPLAY",
                                        color = colorResource(id = R.color.paper),
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                                TextButton(onClick = {
                                    toHome()
                                }) {
                                    Text(
                                        text = "HOME",
                                        color = colorResource(id = R.color.paper),
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// リストビューの上のビュー
@Composable
fun TitleView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(
                    color = colorResource(id = R.color.customBrown1),
                    shape = RoundedCornerShape(5.dp)
                )
                .border(
                    width = 3.dp,
                    color = colorResource(id = R.color.goldLeaf),
                    shape = RoundedCornerShape(3.dp)
                )
        ) {
            Spacer(
                modifier = Modifier
                    .border(
                        width = 5.dp,
                        color = colorResource(id = R.color.customBrown1),
                        shape = RoundedCornerShape(2.dp)
                    )
                    .background(
                        color = colorResource(id = R.color.customBrown1),
                        shape = RoundedCornerShape(5.dp)
                    )
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(height = 30.dp, width = 328.dp)
                .background(
                    color = colorResource(id = R.color.goldLeaf),
                    shape = RoundedCornerShape(5.dp)
                )
                .align(Alignment.TopCenter)
        ) {
            Spacer(
                modifier = Modifier
                    .border(
                        width = 5.dp,
                        color = colorResource(id = R.color.customBrown1),
                        shape = RoundedCornerShape(2.dp)
                    )
                    .background(
                        color = colorResource(id = R.color.customBrown1),
                        shape = RoundedCornerShape(5.dp)
                    )
            )
            Text(
                "WORLD RANKING",
                color = colorResource(id = R.color.blackSteel),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .wrapContentSize(align = Alignment.Center, unbounded = true)
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
fun ResultScreenPreview() {
    ResultScreen(
        viewModel = ResultViewModel(app = Application()),
        totalScore = 87.654,
        onReplay = {},
        toHome = {},
    )
}