package com.example.audioplayer.playback

import com.example.audioplayer.domain.model.Song
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.audioplayer.domain.model.LocalSong
//first lesson

interface PlaybackManager {

    val isPlaying: StateFlow<Boolean>
    val event: SharedFlow<String>
    fun toPrevious()
    fun play(song: Song)
    fun pause()
    fun stop()
    fun forwardToNext()
    fun playLocal(song: LocalSong)
}

