package com.example.audioplayer.playback

import com.example.audioplayer.data.remote.model.Track
import com.example.audioplayer.domain.model.Song
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.audioplayer.domain.model.LocalSong

interface PlaybackManager {

    val isPlaying: StateFlow<Boolean>
    val event: SharedFlow<String>
    fun toPrevious()
    fun play(song: Song)
    fun pause()
    fun stop()
    fun forwardToNext()
    fun playLocal(song: LocalSong)
    fun playAudius(track: Track)
}

