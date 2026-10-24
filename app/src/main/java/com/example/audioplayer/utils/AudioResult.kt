package com.example.audioplayer.utils

sealed class AudioResult< out T> {
    object Loading: AudioResult<Nothing>()
    class Success<T>(val data: T): AudioResult<T>()
    class Error(val message: String): AudioResult<Nothing>()
}

