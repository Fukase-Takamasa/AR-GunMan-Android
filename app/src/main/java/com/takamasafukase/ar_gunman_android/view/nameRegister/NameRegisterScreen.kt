package com.takamasafukase.ar_gunman_android.view.nameRegister

import android.app.Application
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.takamasafukase.ar_gunman_android.utility.CustomDialog
import com.takamasafukase.ar_gunman_android.R
import com.takamasafukase.ar_gunman_android.entity.Ranking
import com.takamasafukase.ar_gunman_android.repository.RankingRepository
import com.takamasafukase.ar_gunman_android.utility.CustomTextField
import com.takamasafukase.ar_gunman_android.utility.RankingUtil
import com.takamasafukase.ar_gunman_android.viewModel.NameRegisterViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun NameRegisterScreen(
    viewModel: NameRegisterViewModel,
    onClose: (registeredRanking: Ranking?) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.closeDialogEvent.collect { registeredRanking ->
            onClose(registeredRanking)
        }
    }

    CustomDialog(
        onDismissRequest = {
            onClose(null)
        },
        size = DpSize(
            width = (screenWidth * 0.54).dp,
            height = (screenHeight * 0.64).dp,
        ),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.paper),
                        shape = RoundedCornerShape(2)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 5.2.dp)
                        .background(
                            color = colorResource(id = R.color.goldLeaf),
                            shape = RoundedCornerShape(2)
                        )
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.paper),
                            shape = RoundedCornerShape(2)
                        )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(top = 12.dp,)
                    ) {
                        Text(
                            text = "CONGRATULATIONS!",
                            color = colorResource(id = R.color.paper),
                            fontSize = (screenHeight * 0.058).sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "YOU'RE RANKED AT",
                                color = colorResource(id = R.color.paper),
                                fontSize = (screenHeight * 0.046).sp,
                            )
                            Text(
                                text = " ${state.value.rankText} ",
                                color = colorResource(id = R.color.customBrown1),
                                fontSize = (screenHeight * 0.058).sp,
                            )
                            Text(
                                text = "IN",
                                color = colorResource(id = R.color.paper),
                                fontSize = (screenHeight * 0.046).sp,
                            )
                        }
                        Text(
                            text = "THE WORLD!",
                            color = colorResource(id = R.color.paper),
                            fontSize = (screenHeight * 0.046).sp,
                        )
                        Text(
                            text = "SCORE: ${"%.3f".format(state.value.totalScore)}",
                            color = colorResource(id = R.color.paper),
                            fontSize = (screenHeight * 0.074).sp,
                            fontWeight = FontWeight.Black,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
                        ) {
                            Text(
                                text = "NAME:",
                                color = colorResource(id = R.color.paper),
                                fontSize = (screenHeight * 0.046).sp,
                            )
                            CustomTextField(
                                value = state.value.nameInputText,
                                onValueChange = {
                                    viewModel.onChangeNameText(it)
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = colorResource(id = R.color.paper),
                                    backgroundColor = colorResource(id = R.color.customBrown1),
                                    cursorColor = colorResource(id = R.color.paper),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                ),
                                cursorBrush = SolidColor(colorResource(id = R.color.paper)),
                                textStyle = TextStyle(
                                    color = colorResource(id = R.color.paper)
                                ),
                                shape = RoundedCornerShape(12),
                                singleLine = true,
                                trailingIcon = {
                                    if (state.value.nameInputText.isNotEmpty()) {
                                        IconButton(
                                            onClick = {
                                                viewModel.onChangeNameText("")
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.Close,
                                                contentDescription = null,
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .padding(start = 8.dp),
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(color = colorResource(id = R.color.blackSteel))
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            TextButton(
                                onClick = {
                                    viewModel.onTapNoThanksButton()
                                },
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    text = "NO, THANKS",
                                    color = Color.DarkGray,
                                    fontSize = (screenHeight * 0.048).sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .wrapContentSize(align = Alignment.Center, unbounded = true)
                                        .padding(bottom = 4.dp)
                                )
                            }
                            Spacer(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                                    .background(color = colorResource(id = R.color.blackSteel))
                            )
                            TextButton(
                                onClick = {
                                    viewModel.onTapRegisterButton()
                                },
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                            ) {
                                val buttonColor = if (state.value.nameInputText.isEmpty())
                                    Color.LightGray
                                else
                                    colorResource(id = R.color.blackSteel)

                                if (state.value.isShowLoadingOnRegisterButton) {
                                    CircularProgressIndicator(
                                        color = colorResource(id = R.color.paper),
                                        modifier = Modifier
                                            .padding(bottom = 4.dp)
                                            .size(28.dp)
                                    )
                                }else {
                                    Text(
                                        text = "REGISTER!",
                                        color = buttonColor,
                                        fontSize = (screenHeight * 0.064).sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .wrapContentSize(
                                                align = Alignment.Center,
                                                unbounded = true
                                            )
                                            .padding(bottom = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
fun NameRegisterScreenPreview() {
    NameRegisterScreen(
        viewModel = NameRegisterViewModel(
            app = Application(),
            rankingRepository = RankingRepository(),
            rankingUtil = RankingUtil(),
            params = NameRegisterViewModel.Params(
                totalScore = 98.765,
                rankingListFlow = MutableStateFlow(
                    listOf()
                )
            )
        ),
        onClose = {},
    )
}