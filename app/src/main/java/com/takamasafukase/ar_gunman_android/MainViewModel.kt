package com.takamasafukase.ar_gunman_android

import android.os.Looper
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.os.Handler

class MainViewModel {
    private val _state = MutableStateFlow(MainViewState(listOf()))
    val state = _state.asStateFlow()

    private val db = Firebase.firestore


    fun getRankings() {
        db.collection("worldRanking")
            .orderBy("score", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val rankings = documents.toObjects(Ranking::class.java)
                _state.value = _state.value.copy(rankings = rankings)
            }
            .addOnFailureListener { error ->
                println("error: $error")
            }
    }

    fun getDummyRankings() {
        val list = listOf(
            Ranking(score = 98.765, user_name = "なまえ"),
            Ranking(score = 98.765, user_name = "なまえ"),
            Ranking(score = 98.765, user_name = "なまえ"),
            Ranking(score = 98.765, user_name = "なまえ"),
            Ranking(score = 98.765, user_name = "なまえ"),
            Ranking(score = 98.765, user_name = "なまえ"),
            Ranking(score = 98.765, user_name = "なまえ"),
            Ranking(score = 98.765, user_name = "なまえ"),
            Ranking(score = 98.765, user_name = "なまえ"),
            Ranking(score = 98.765, user_name = "なまえ"),
        )
        Handler(Looper.getMainLooper()).postDelayed({
            _state.value = _state.value.copy(rankings = list)
        }, 1000)
    }
}