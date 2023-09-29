package com.takamasafukase.ar_gunman_android

import com.google.firebase.firestore.DocumentId

data class Ranking(
    @DocumentId val id: String = "",
    val user_name: String,
    val score: Double,
)


