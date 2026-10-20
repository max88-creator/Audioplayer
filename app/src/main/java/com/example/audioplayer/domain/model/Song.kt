package com.example.audioplayer.domain.model

data class Song(
    val id: Int,
    val title: String,
    val artist: String,
    val url: String = ""
)

