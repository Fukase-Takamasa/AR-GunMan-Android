package com.takamasafukase.ar_gunman_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import com.takamasafukase.ar_gunman_android.ui.theme.ARGunManAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MainViewModel()
        viewModel.getDummyRankings()

        setContent {

            ARGunManAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(
                    width = Dp((screenWidth * 0.6).toFloat()),
                    height = Dp((screenHeight * 0.8).toFloat())
                )
        ) {
            RankingListView(
                list = state.rankings
            )
        }
    }
}

@Composable
fun RankingListView(list: List<Ranking>) {
    LazyColumn {
        itemsIndexed(list) { index, ranking ->
            RankingItem(rankIndex = index + 1, ranking = ranking)
        }
    }
}