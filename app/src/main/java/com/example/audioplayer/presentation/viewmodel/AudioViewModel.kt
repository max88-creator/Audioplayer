package com.example.audioplayer.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioplayer.domain.model.Song
import com.example.audioplayer.domain.repository.AudioRepository
import com.example.audioplayer.domain.repository.RepositoryScenario
import com.example.audioplayer.domain.repository.RepositoryScenarioProvider
import com.example.audioplayer.playback.PlaybackManager
import com.example.audioplayer.utils.LOG_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val repository: AudioRepository,
    private val playbackManager: PlaybackManager,
    private val repositoryScenarioProvider: RepositoryScenarioProvider
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _uiState = MutableStateFlow(
        AudioUiState(
            songs = emptyList(), isLoading = true
        )
    )
    val uiState = _uiState.combine(
        playbackManager.isPlaying
    ) { state, isPlaying ->
        state.copy(
            isPlaying = isPlaying
        )
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = _uiState.value
    )


    suspend fun compareList() {
        val currentList = _uiState.value.songs
        try {
            val newList = repository.getSongs()
            val hasUpdate = currentList != newList
            _uiState.update { state ->
                if (hasUpdate) {
                    state.copy(
                        latestSongs = newList,
                        hasUpdates = true
                    )
                } else {
                    state.copy(
                        hasUpdates = false
                    )
                }
            }
        }catch (c: CancellationException) {
            throw c
        } catch (e: Exception) {
            _uiEvent.emit(UiEvent.ShowSnackBar("Error update song list"))
        }
    }

    fun onCompareClick() {
        if (!_uiState.value.isCheckingUpdates) {
            _uiState.update { state ->
                state.copy(
                    isCheckingUpdates = true
                )
            }
            viewModelScope.launch {
                try {
                    compareList()
                } finally {
                    _uiState.update { state ->
                        state.copy(
                            isCheckingUpdates = false
                        )
                    }
                }
            }
        }
    }

    init {
        getSongs()
        observePlaybackEvents()
    }

    fun getSongs() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }
            try {
                val songsList = repository.getSongs()
                _uiState.update { state ->
                    state.copy(
                        songs = songsList, isLoading = false, error = null
                    )
                }
            } catch (c: CancellationException) {
                throw c
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false, error = e.message ?: "Failed to load songs"
                    )
                }
            }
        }
    }


    fun updateScenario(repositoryScenario: RepositoryScenario) {
        repositoryScenarioProvider.setScenario(repositoryScenario)
    }

    private fun selectSong(song: Song) {
        _uiState.update { state ->
            state.copy(
                selectedSong = song
            )
        }
    }

    fun previous() {
        playbackManager.toPrevious()
    }

    fun forward() {
        playbackManager.forwardToNext()
    }
    private fun play(song: Song) {
        playbackManager.play(song)
    }

    fun pause() {
        playbackManager.pause()
    }
    private fun stop() {
        playbackManager.stop()
    }

    fun onEvent(event: AudioEvent) {
        when (event) {
            is AudioEvent.SelectSong -> selectSong(song = event.song)
            is AudioEvent.Play -> play(song = event.song)
            is AudioEvent.Stop -> stop()
        }
    }

    fun observePlaybackEvents() {
        viewModelScope.launch {
            playbackManager.event.collect { event ->
                _uiEvent.emit(UiEvent.ShowSnackBar(event))
                Log.d(LOG_KEY, event)
            }
        }
    }

    fun onUpdateClick() {
        val lastList = _uiState.value.latestSongs
        _uiState.update { state ->
            state.copy(
                songs = lastList,
                hasUpdates = false
            )
        }
    }
}



