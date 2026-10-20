package com.example.audioplayer.utils
//first lesson

sealed class AudioResult<T> {
    object Loading: AudioResult<Nothing>()
    class Success<T>(val data: T): AudioResult<T>()
    class Error<T>(val message: String): AudioResult<Nothing>()
}

