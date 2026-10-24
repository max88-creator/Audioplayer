package com.example.audioplayer.data.remote

import com.example.audioplayer.data.remote.model.Track
import com.example.audioplayer.data.remote.model.TrackSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AudiusApi {
    @GET("tracks/search")
    suspend fun searchTracks (
    @Query("query") query: String,
    @Query("limit") limit: Int,
    @Query("offset") offset: Int
    ): List<Track>
}

