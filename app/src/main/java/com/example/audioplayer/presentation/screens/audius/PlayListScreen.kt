package com.example.audioplayer.presentation.screens.audius

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.audioplayer.presentation.screens.home_screen.ErrorHomeScreen
import com.example.audioplayer.presentation.screens.home_screen.HomeLoadingScreen
import com.example.audioplayer.presentation.viewmodel.audius.AudiusViewModel
import com.example.audioplayer.utils.AudioResult

@Composable
@Preview(showBackground = true)
fun PlayListScreen(
    viewModel: AudiusViewModel = hiltViewModel()
) {
    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()

    var input by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(55.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            TextField(
                value = input,
                onValueChange = {
                    input = it
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp)
            )
        }
        Spacer(modifier = Modifier.height(55.dp))
        when(val result = searchResult) {
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
                        .fillMaxWidth(0.8f)
                ) {
                    items(tracks) { item ->

                    }
                }
            }
        }
    }
}

