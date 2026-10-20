package com.example.audioplayer.presentation.viewmodel

import com.example.audioplayer.domain.model.Song
//first lesson

data class AudioUiState(
    val songs: List<Song>,
    val selectedSong: Song? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPlaying: Boolean = false,
    val latestSongs: List<Song> = songs,
    val hasUpdates: Boolean = false,
    val isCheckingUpdates: Boolean = false
)

