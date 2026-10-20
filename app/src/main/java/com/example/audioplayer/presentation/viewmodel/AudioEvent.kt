package com.example.audioplayer.presentation.viewmodel

import com.example.audioplayer.domain.model.Song
//first lesson

sealed interface AudioEvent {
    data class SelectSong(val song: Song): AudioEvent
    data class Play(val song: Song): AudioEvent
    data object Stop: AudioEvent
}


