package com.takamasafukase.ar_gunman_android.repository

import android.os.Handler
import android.os.Looper
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.takamasafukase.ar_gunman_android.entity.Ranking

class RankingRepository {
    private val db = Firebase.firestore

    fun getRankings(
        onData: (List<Ranking>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("worldRanking")
            .orderBy("score", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                onData(
                    documents.toObjects(Ranking::class.java)
                )
            }
            .addOnFailureListener { error ->
                onError(error)
            }
    }

    fun getDummyRankings(
        onData: (List<Ranking>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            onData(
                (1..150).map { Ranking(score = 98.765, user_name = "ユーザー名") }
            )
        }, 1000)
    }
}