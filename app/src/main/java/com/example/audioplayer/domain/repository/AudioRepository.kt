package com.example.audioplayer.domain.repository

import com.example.audioplayer.domain.model.Song
//first lesson
interface AudioRepository {
    suspend fun getSongs(): List<Song>
}