package com.example.audioplayer.domain.repository

import com.example.audioplayer.data.remote.model.Track

interface AudiusRepository {
    suspend fun searchTrack(
        query: String
    ): List<Track>
}

