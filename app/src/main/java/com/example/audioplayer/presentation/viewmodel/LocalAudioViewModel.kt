package com.example.audioplayer.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioplayer.domain.model.LocalSong
import com.example.audioplayer.domain.repository.LocalAudioRepository
import com.example.audioplayer.playback.PlaybackManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LocalAudioViewModel @Inject constructor(
    private val playbackManager: PlaybackManager,
    private val repository: LocalAudioRepository
) : ViewModel() {
    var songs by mutableStateOf<List<LocalSong>>(emptyList())
        private set

    private val _localUiState = MutableStateFlow(
        LocalAudioState(
            songs = emptyList(),
            isLoading = true
        )
    )
    val localUiState = _localUiState.combine(
        playbackManager.isPlaying
    ) { state, isPlaying ->
        state.copy(
            isPlaying = isPlaying
        )
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = _localUiState.value
    )


    fun getLocalSongs() {
        viewModelScope.launch {
            songs = repository.getLocalSongs()
        }
    }

    fun forward() {
        playbackManager.forwardToNext()
    }

    fun previous() {
        playbackManager.toPrevious()
    }

    fun play(song: LocalSong) {
        _localUiState.update { state ->
            state.copy(selectedSong = song)
        }
        playbackManager.playLocal(song)
    }
    fun pause() {
        playbackManager.pause()
    }

    fun stop() {
        playbackManager.stop()
    }
}

