package com.example.audioplayer.data.repository

import com.example.audioplayer.data.remote.AudiusApi
import com.example.audioplayer.data.remote.model.Track
import com.example.audioplayer.domain.repository.AudiusRepository
import javax.inject.Inject

class AudiusRepositoryImpl @Inject constructor(
    private val audiusApi: AudiusApi
) : AudiusRepository {

    override suspend fun searchTrack(
        query: String,
        limit: Int,
        offset: Int
    ): List<Track> {
        return audiusApi.searchTracks(
            query = query,
            limit = limit,
            offset = offset
        ).data
    }
}

