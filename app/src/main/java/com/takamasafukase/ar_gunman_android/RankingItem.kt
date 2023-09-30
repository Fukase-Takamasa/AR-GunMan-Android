package com.takamasafukase.ar_gunman_android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@Composable
fun RankingItem(rankIndex: Int, ranking: Ranking) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(height = Dp(60.0f))
            .background(Color.Transparent)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dp(16.0f), vertical = Dp(2.0f))
                .background(colorResource(id = R.color.customBrown1))
        )
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dp(30.0f), vertical = Dp(10.0f))
                .background(colorResource(id = R.color.customBrown2))
        )
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                rankIndex.toString(),
                color = colorResource(id = R.color.paper),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = Dp(24.0f), bottom = Dp(6.0f))
                    .size(width = Dp(32.0f), height = Dp(22.0f))
                    .background(colorResource(id = R.color.goldLeaf))
                    .wrapContentSize(align = Alignment.Center, unbounded = true)
            )
            Text(
                text = ranking.score.toString(),
                color = colorResource(id = R.color.paper),
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(start = Dp(8.0f), bottom = Dp(10.0f))
            )
            Text(
                text = ranking.user_name,
                color = colorResource(id = R.color.paper),
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = Dp(10.0f), bottom = Dp(10.0f))
            )
        }
    }
}

