package com.example.audioplayer.presentation.viewmodel.audius

import com.example.audioplayer.data.remote.model.Track
import com.example.audioplayer.domain.model.Song

data class AudiusUiState(
    val songs: List<Track>,
    val selectedSong: Track? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPlaying: Boolean = false,
)
