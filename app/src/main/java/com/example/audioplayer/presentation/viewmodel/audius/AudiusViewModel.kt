package com.example.audioplayer.presentation.viewmodel.audius

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioplayer.data.remote.model.Track
import com.example.audioplayer.domain.repository.AudiusRepository
import com.example.audioplayer.utils.AudioResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudiusViewModel @Inject constructor(
    private val repository: AudiusRepository
) : ViewModel() {
    private val _searchResult = MutableStateFlow<AudioResult<List<Track>>>(AudioResult.Loading)
    val searchResult = _searchResult.asStateFlow()

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

