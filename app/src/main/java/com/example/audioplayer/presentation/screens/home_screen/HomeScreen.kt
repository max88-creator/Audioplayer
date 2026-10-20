package com.example.audioplayer.presentation.screens.home_screen

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.audioplayer.presentation.viewmodel.AudioEvent
import com.example.audioplayer.presentation.viewmodel.AudioViewModel
import com.example.audioplayer.presentation.viewmodel.UiEvent
import com.example.audioplayer.utils.LOG_KEY
//first lesson

@Composable
@Preview(showBackground = true)
fun HomeScreen(
    viewModel: AudioViewModel = hiltViewModel()
) {

    val snackBarState = remember { SnackbarHostState() }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val state = uiState.value

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarState.showSnackbar(event.errorMessage)
                    Log.d(LOG_KEY, event.errorMessage)
                }
            }
        }
    }

    when {
        state.isLoading -> HomeLoadingScreen()
        state.error != null -> ErrorHomeScreen(state.error)
        state.songs.isEmpty() -> EmptyList()
        else -> SuccessHomeScreen(
            songs = state.songs,
            song = state.selectedSong,
            snackBarState = snackBarState,
            hasUpdate = state.hasUpdates,
            isCheckingUpdates = state.isCheckingUpdates,
            isPlaying = state.isPlaying,
            onPreviousClick = {
                viewModel.previous()
            },
            onNextClick = {
                viewModel.forward()
            },
            onPauseClick = {
                viewModel.pause()
            },
            onSelectClick = { song ->
                viewModel.onEvent(
                    event = AudioEvent.SelectSong(song)
                )
            },
            onPlayClick = { song ->
                viewModel.onEvent(event = AudioEvent.Play(song))
            },
            onStopClick = {
                viewModel.onEvent(AudioEvent.Stop)
            },
            onUpdateClick = {
                viewModel.onUpdateClick()
            },
            onCompareClick = {
              viewModel.onCompareClick()
            }
        )
    }
}







