package com.example.audioplayer.presentation.viewmodel.audius

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioplayer.data.remote.model.Track
import com.example.audioplayer.domain.repository.AudiusRepository
import com.example.audioplayer.playback.PlaybackManager
import com.example.audioplayer.utils.AudioResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudiusViewModel @Inject constructor(
    private val repository: AudiusRepository,
    private val playbackManager: PlaybackManager
) : ViewModel() {
    private val _searchResult = MutableStateFlow<AudioResult<List<Track>>>(AudioResult.Loading)
    val searchResult = _searchResult.asStateFlow()

    private val _uiAudiusState = MutableStateFlow(
        AudiusUiState(
            songs = emptyList(),
            isLoading = true
        )
    )

    val uiAudiusState = _uiAudiusState.combine(
        playbackManager.isPlaying
    ) { state, isPlaying ->
        state.copy(
            isPlaying = isPlaying
        )
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = _uiAudiusState.value
    )

    fun selectSong(track: Track) {
        _uiAudiusState.update { state ->
            state.copy(
                selectedSong = track
            )
        }
    }

    fun previous() {
        playbackManager.toPrevious()
    }

    fun forward() {
        playbackManager.forwardToNext()
    }

    fun pause() {
        playbackManager.pause()
    }

    fun stop() {
        playbackManager.stop()
    }

    fun play(track: Track) {
        playbackManager.playAudius(track)
    }

    fun onEvent(event: AudiusEvent) {
        when(event) {
            is AudiusEvent.SelectSong -> selectSong(track = event.track)
            is AudiusEvent.Play -> {
                selectSong(track = event.track)
                play(track = event.track)
            }
            is AudiusEvent.Stop -> {
                stop()
            }
        }
    }
    fun searchTracks(query: String) {
        viewModelScope.launch {
            try {
                _searchResult.value = AudioResult.Loading
                val result = repository.searchTrack(query)
                _searchResult.value = AudioResult.Success(result)

            } catch (e: Exception) {
                _searchResult.value = AudioResult.Error(e.message.toString())
            }
        }
    }
}



/*
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


 */