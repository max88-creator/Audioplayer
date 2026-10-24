package com.example.audioplayer.data.remote

import com.example.audioplayer.data.remote.model.Track
import com.example.audioplayer.data.remote.model.TrackSearchResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AudiusApi {
    @GET("tracks/search")
    suspend fun searchTracks (
    @Query("query") query: String,
    @Query("limit") limit: Int,
    @Query("offset") offset: Int
    ): TrackSearchResponse

    @GET("tracks/{track_id}/stream")
    suspend fun streamTrack(
        @Path("track_id") trackId: String
    ): ResponseBody
}

