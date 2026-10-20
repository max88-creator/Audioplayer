package com.example.audioplayer.presentation.viewmodel
//first lesson

sealed interface UiEvent {
    data class ShowSnackBar(val errorMessage: String): UiEvent
}

