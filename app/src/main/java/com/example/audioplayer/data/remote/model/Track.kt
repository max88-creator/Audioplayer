package com.example.audioplayer.data.remote.model
//third lesson
data class Track(
    val id: String,
    val title: String,
    val user: TrackUser,
    val artwork: TrackArtwork?,
    val duration: Int,
)
