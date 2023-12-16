package com.takamasafukase.ar_gunman_android.view.weaponChange

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.takamasafukase.ar_gunman_android.model.WeaponType
import com.takamasafukase.ar_gunman_android.R


@Composable
fun WeaponListItem(
    type: WeaponType,
    onTapItem: () -> Unit,
) {
    // TODO: あとでSwift版みたいにenumに紐づけてどこかのファイルに置いて、メソッドで取得できる様にしたい
    val imageResourceId = when (type) {
        WeaponType.PISTOL -> R.drawable.pistol
        WeaponType.BAZOOKA -> R.drawable.rocket_launcher
        WeaponType.RIFLE -> R.drawable.rifle
        WeaponType.SHOT_GUN -> R.drawable.shot_gun
        WeaponType.SNIPER_RIFLE -> R.drawable.sniper_rifle
        WeaponType.MINI_GUN -> R.drawable.mini_gun
    }
    Box {
        IconButton(
            modifier = Modifier,
            onClick = {
                onTapItem()
            }
        ) {
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
}
