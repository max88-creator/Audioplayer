package com.example.audioplayer.domain.repository

import com.example.audioplayer.domain.model.LocalSong
//second lesson
interface LocalAudioRepository {
    suspend fun getLocalSongs(): List<LocalSong>
}