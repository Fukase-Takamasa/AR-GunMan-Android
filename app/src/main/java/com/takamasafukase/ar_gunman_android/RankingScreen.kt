package com.takamasafukase.ar_gunman_android

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RankingScreen(viewModel: RankingViewModel, onClose: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black.copy(alpha = 0.8f)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(
                        width = Dp((screenWidth * 0.6).toFloat()),
                        height = Dp((screenHeight * 0.8).toFloat())
                    )
            ) {
                BackgroundBorderView()
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                ) {
                    TitleView(onClose)
                    RankingListView(list = state.rankings)
                }
                if (state.rankings.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(color = colorResource(id = R.color.paper))
                    }
                }
            }
        }
    }
}

// リストビューの上のビュー
@Composable
fun TitleView(onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .height(30.dp)
            .background(
                color = colorResource(id = R.color.goldLeaf),
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        Text(
            "WORLD RANKING",
            color = colorResource(id = R.color.blackSteel),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.Center, unbounded = true)
        )
        IconButton(
            onClick = {
                onClose()
            },
            modifier = Modifier
                .width(60.dp)
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = ""
            )
        }
    }
}

// リストビューの背後に重なっている枠線だけのビュー
@Composable
fun BackgroundBorderView() {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp)
            .border(
                width = 5.dp,
                color = colorResource(id = R.color.customBrown1),
                shape = RoundedCornerShape(2.dp)
            )
    )
}

// リストビューの部分
@Composable
fun RankingListView(list: List<Ranking>) {
    LazyColumn(
        modifier = Modifier
            .border(
                width = 7.dp,
                color = colorResource(id = R.color.goldLeaf),
                shape = RoundedCornerShape(size = 3.dp)
            )
    ) {
        itemsIndexed(list) { index, ranking ->
            if (index == 0) {
                Spacer(modifier = Modifier.height(10.dp))
            }
            RankingItem(
                rankIndex = index + 1,
                ranking = ranking
            )
        }
    }
}