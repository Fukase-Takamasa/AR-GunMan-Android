package com.takamasafukase.ar_gunman_android.view.tutorial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.takamasafukase.ar_gunman_android.Model.WeaponType
import com.takamasafukase.ar_gunman_android.R
import com.takamasafukase.ar_gunman_android.utility.CustomDialog

@Composable
fun WeaponChangeScreen(
    onClose: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp

    CustomDialog(
        onDismissRequest = onClose,
        size = DpSize(
            width = screenWidth.dp,
            height = screenHeight.dp
        ),
        content = {
            Surface(
                color = Color.Transparent
            ) {
                Box {
                    WeaponListView()
                    TextButton(
                        modifier = Modifier
                            .align(Alignment.TopEnd),
                        onClick = {
                            onClose()
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(end = 24.dp)
                        ) {
                            Image(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close Icon",
                                colorFilter = ColorFilter.tint(colorResource(id = R.color.paper)),
                                modifier = Modifier
                                    .size(32.dp)
                            )
                            Text(
                                text = "CLOSE",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.paper)
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun WeaponListView() {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(WeaponType.values()) {
            WeaponListItem(it)
        }
    }
}

@Composable
fun WeaponListItem(type: WeaponType) {
    val imageResourceId = when (type) {
        WeaponType.PISTOL -> R.drawable.pistol
        WeaponType.BAZOOKA -> R.drawable.rocket_launcher
        WeaponType.RIFLE -> R.drawable.rifle
        WeaponType.SHOT_GUN -> R.drawable.shot_gun
        WeaponType.SNIPER_RIFLE -> R.drawable.sniper_rifle
        WeaponType.MINI_GUN -> R.drawable.mini_gun
    }
    Box {
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = "Weapon icon",
            colorFilter = ColorFilter.tint(colorResource(id = R.color.paper)),
            alpha = if (type == WeaponType.PISTOL) 1f else 0.5f,
            modifier = Modifier
                .size(
                    width = (LocalConfiguration.current.screenWidthDp * 0.38).dp,
                    height = (LocalConfiguration.current.screenHeightDp * 0.7).dp,
                )
                .align(Alignment.Center)
        )
        if (type != WeaponType.PISTOL) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(
                        color = Color.Red,
                    )
                    .align(Alignment.Center)
            ) {
                Text(
                    "COMING SOON",
                    color = colorResource(id = R.color.paper),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center, unbounded = true)
                        .padding(horizontal = 24.dp)
                )
            }
        }
    }
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
fun WeaponChangeScreenPreview() {
    WeaponChangeScreen {

    }
}