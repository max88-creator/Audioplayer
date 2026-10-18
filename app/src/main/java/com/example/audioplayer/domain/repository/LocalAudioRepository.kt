package com.example.audioplayer.domain.repository

import com.example.audioplayer.domain.model.LocalSong

interface LocalAudioRepository {
    suspend fun getLocalSongs(): List<LocalSong>
}