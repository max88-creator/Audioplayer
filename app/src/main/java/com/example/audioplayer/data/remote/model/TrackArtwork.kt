package com.example.audioplayer.data.remote.model

import com.google.gson.annotations.SerializedName

data class TrackArtwork(
    @SerializedName("150x150") val size150: String?,
    @SerializedName("480x480") val size480: String?,
    @SerializedName("1000x1000") val size1000: String?
)
