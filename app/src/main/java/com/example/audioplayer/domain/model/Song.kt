package com.example.audioplayer.domain.model

data class Song(
    val id: Int,
    val title: String,
    val artist: String,
    val url: String = "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"
)

