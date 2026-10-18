package com.example.audioplayer.presentation.viewmodel

sealed interface UiEvent {
    data class ShowSnackBar(val errorMessage: String): UiEvent
}

