package com.takamasafukase.ar_gunman_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(
    toWeaponChange: () -> Unit,
    toResult: () -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(width = (screenWidth / 7.5).dp, height = (screenHeight / 8).dp)
                    .offset(x = (screenWidth / 20).dp, y = (screenHeight / 6).dp)
                    .background(color = colorResource(id = R.color.goldLeaf))
            ) {
                Text(
                    text = "30:00",
                    color = colorResource(id = R.color.blackSteel),
                    fontSize = (screenHeight * 0.09).sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.pistol_sight),
                colorFilter = ColorFilter.tint(Color.Red),
                contentDescription = "Pistol sight",
                modifier = Modifier
                    .size(size = (screenHeight / 4).dp)
            )
            Image(
                painter = painterResource(id = R.drawable.bullets_7),
                contentDescription = "Pistol bullets",
                modifier = Modifier
                    .size(width = (screenWidth / 4.28).dp, height = (screenHeight / 5.71).dp)
                    .align(Alignment.BottomStart)
                    .offset(x = (screenWidth / 45).dp, y = (-(screenHeight / 12)).dp)
            )
            Button(
                onClick = {
                    toWeaponChange()
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(size = (screenHeight / 4).dp)
                    .offset(x = -(screenWidth / 15).dp, y = (screenHeight / 6).dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.weapon_switch_icon),
                    contentDescription = "Weapon change icon",
                    modifier = Modifier
                )
            }
        }
    }
}