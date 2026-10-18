package com.example.audioplayer.domain.repository

import com.example.audioplayer.domain.model.Song

interface AudioRepository {
    suspend fun getSongs(): List<Song>
}