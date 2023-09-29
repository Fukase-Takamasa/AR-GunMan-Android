package com.takamasafukase.ar_gunman_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.takamasafukase.ar_gunman_android.ui.theme.ARGunManAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MainViewModel()

        setContent {
            val rankings = rememberRankings()

            ARGunManAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    MainScreen(viewModel = viewModel)
                    RankingListView(rankings)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Has pulsado ${state.count} vezes.")
        Button(onClick = {
            viewModel.onTapIncrementButton()
        }) {
            Text(text = "Pulsa me para contar")
        }
    }
}

@Composable
fun RankingListView(list: List<Ranking>) {
    LazyColumn {
        items(list) { ranking ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = ranking.user_name)
                Text(text = ranking.score.toString())
            }
            Spacer(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun rememberRankings(): List<Ranking> {
    val rankings = remember { mutableStateListOf<Ranking>() }

    DisposableEffect(Unit) {
        val registration = Firebase.firestore
            .collection("worldRanking")
            .orderBy("score")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    println("error: $error")
                    return@addSnapshotListener
                }
                for (doc in snapshot.documentChanges) {
                    val ranking = doc.document.toObject(Ranking::class.java)
                    when (doc.type) {
                        DocumentChange.Type.ADDED -> {
                            rankings.add(ranking)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val index = rankings.indexOfFirst { it.id == ranking.id }
                            rankings[index] = ranking
                        }
                        DocumentChange.Type.REMOVED -> {
                            rankings.removeIf { it.id == ranking.id }
                        }
                    }
                }
            }
        onDispose {
            registration.remove()
        }
    }

    return rankings.toList()
}