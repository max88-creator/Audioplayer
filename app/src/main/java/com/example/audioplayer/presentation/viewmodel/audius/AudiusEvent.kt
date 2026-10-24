package com.example.audioplayer.presentation.viewmodel.audius

import com.example.audioplayer.data.remote.model.Track

sealed interface AudiusEvent {
    data class SelectSong(val track: Track): AudiusEvent
    data class Play(val track: Track): AudiusEvent
    data object Stop: AudiusEvent
}