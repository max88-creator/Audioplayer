package com.example.audioplayer.presentation.screens.audius

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.audioplayer.R
import com.example.audioplayer.presentation.screens.home_screen.ErrorHomeScreen
import com.example.audioplayer.presentation.screens.home_screen.HomeLoadingScreen
import com.example.audioplayer.presentation.viewmodel.audius.AudiusEvent
import com.example.audioplayer.presentation.viewmodel.audius.AudiusViewModel
import com.example.audioplayer.ui.theme.DarkPrimary
import com.example.audioplayer.ui.theme.LightGray
import com.example.audioplayer.utils.AudioResult

@Composable
@Preview(showBackground = true)
fun PlayListScreen(
    viewModel: AudiusViewModel = hiltViewModel()
) {
    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()

    val uiState = viewModel.uiAudiusState.collectAsStateWithLifecycle()
    val state = uiState.value

    var input by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(55.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            SearchTextField(
                isLoading = state.isLoading,
                value = input,
                onValueChange = { input = it },
                onSearchTracks = {
                    viewModel.searchTracks(
                        query = input
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(55.dp))
        when (val result = searchResult) {
            is AudioResult.Loading -> {
                HomeLoadingScreen()
            }

            is AudioResult.Error -> {
                ErrorHomeScreen(result.message)
            }

            is AudioResult.Success -> {
                val tracks = result.data
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    items(tracks) { item ->
                        AudiusTrackItem(
                            title = item.title,
                            artist = item.user.name,
                            artworkUrl = item.artwork?.size150,
                            onPreviousClick = {
                                viewModel.previous()
                            },
                            onPlayClick = {
                                viewModel.onEvent(event = AudiusEvent.Play(item))
                            },
                            onNextClick = {
                                viewModel.forward()
                            },
                            isPlaying = state.isPlaying && state.selectedSong?.id == item.id,
                            onSelectedClick = {
                                viewModel.onEvent(event = AudiusEvent.SelectSong(item))
                            },
                            onPauseClick = {
                                viewModel.pause()
                            },
                            onStopClick = {
                                viewModel.stop()
                            }
                        )
                        Spacer(modifier = Modifier.height(9.dp))
                    }
                }
            }
        }
    }
}

