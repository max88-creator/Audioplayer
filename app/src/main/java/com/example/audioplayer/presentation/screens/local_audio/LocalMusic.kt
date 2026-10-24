package com.example.audioplayer.presentation.screens.local_audio

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.audioplayer.presentation.viewmodel.LocalAudioViewModel
import com.example.audioplayer.ui.theme.LightBlue
import com.example.audioplayer.utils.SongItem
@Composable
fun LocalMusic(
    viewModel: LocalAudioViewModel = hiltViewModel()
) {

    val uiState = viewModel.localUiState.collectAsStateWithLifecycle()
    val state = uiState.value

    val context = LocalContext.current
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_AUDIO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted -> hasPermission = granted }

    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            viewModel.getLocalSongs()
        } else {
            permissionLauncher.launch(permission)
        }
    }

    val songs = viewModel.songs
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(55.dp))
        if (hasPermission) {
            Text(
                text = "Доступ к аудиофайлам разрешён",
                fontSize = 21.sp,
                fontWeight = FontWeight(700),
                color = LightBlue
            )
        } else {
            Text("Нет доступа к аудиофайлам")
            Button(
                onClick = { permissionLauncher.launch(permission) }
            ) {
                Text("Разрешить доступ")
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "Найдено треков: ${songs.size}")
        Spacer(modifier = Modifier.height(55.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 23.dp)
        ) {
            items(songs) { item ->
                SongItem(
                    artist = item.artist,
                    title = item.title,
                    isPlaying = state.isPlaying &&
                            state.selectedSong?.id?.toLong()
                            == item.id,
                    onPauseClick = {
                        viewModel.pause()
                    },
                    onSelectedClick = {
                        viewModel.play(item)
                    },
                    onStopClick = {
                        viewModel.stop()
                    },
                    onPreviousClick = {
                        viewModel.previous()
                    },
                    onNextClick = {
                        viewModel.forward()
                    },
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

