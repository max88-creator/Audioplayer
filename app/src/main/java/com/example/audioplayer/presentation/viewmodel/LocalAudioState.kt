package com.example.audioplayer.presentation.viewmodel

import com.example.audioplayer.domain.model.LocalSong
import com.example.audioplayer.domain.model.Song
//second lesson
data class LocalAudioState(
    val songs: List<LocalSong>,
    val selectedSong: LocalSong? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPlaying: Boolean = false,
    val latestSongs: List<LocalSong> = songs,
    val hasUpdates: Boolean = false,
    val isCheckingUpdates: Boolean = false
)
