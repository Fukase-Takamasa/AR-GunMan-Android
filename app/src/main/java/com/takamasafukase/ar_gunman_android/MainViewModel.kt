package com.takamasafukase.ar_gunman_android

import android.app.DownloadManager.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel {
    private val _state = MutableStateFlow(MainViewState(count = 0, listOf()))
    val state = _state.asStateFlow()

    private val db = Firebase.firestore

    fun onTapIncrementButton() {
        _state.value = _state.value.copy(count = _state.value.count + 1)
    }

    fun getRankings() {
        db.collection("worldRanking")
            .orderBy("score", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val rankings = documents.toObjects(Ranking::class.java)
                _state.value = _state.value.copy(rankings = rankings)
            }
            .addOnFailureListener { error ->
                println("error: $error")
            }
    }
}