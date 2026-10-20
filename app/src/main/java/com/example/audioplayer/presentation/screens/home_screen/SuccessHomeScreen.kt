package com.example.audioplayer.presentation.screens.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audioplayer.domain.model.Song
import com.example.audioplayer.ui.theme.Purple40
import com.example.audioplayer.utils.SongItem
//first lesson

@Composable
fun SuccessHomeScreen(
    songs: List<Song>,
    song: Song?,
    snackBarState: SnackbarHostState,
    hasUpdate: Boolean,
    isCheckingUpdates: Boolean,
    isPlaying: Boolean,
    onPauseClick: () -> Unit,
    onSelectClick: (Song) -> Unit,
    onPlayClick: (Song) -> Unit,
    onStopClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onCompareClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarState,
                modifier = Modifier,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(55.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(75.dp)
                    .background(
                        color = Purple40,
                        shape = RoundedCornerShape(7.dp)
                    )
                    .align(Alignment.CenterHorizontally)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 25.dp, top = 12.dp)
                ) {
                    Text(
                        text = "Selected: ",
                        fontSize = 21.sp,
                        fontWeight = FontWeight(800),
                        color = Color.Black
                    )

                    Text(
                        text = song?.let {
                            "${it.artist} - ${it.title}"
                        } ?: "Nothing selected"
                    )
                }
            }
            Spacer(modifier = Modifier.height(35.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        onUpdateClick()
                    },
                    enabled = hasUpdate

                ) {
                    Text(
                        text = "Update"
                    )
                }
                Spacer(modifier = Modifier.width(25.dp))
                Button(
                    onClick = {
                        onCompareClick()
                    },
                    enabled = !isCheckingUpdates
                ) {
                    Text(
                        text = if (isCheckingUpdates) {
                            "Checking…"
                        } else {
                            "Compare"
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(35.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 9.dp)
            ) {
                items(songs) { item ->
                    SongItem(
                        artist = item.artist,
                        title = item.title,
                        isPlaying = isPlaying && song?.id ==
                                item.id,
                        onPreviousClick = {
                          onPreviousClick()
                        },
                        onPauseClick = {
                            onPauseClick()
                        },
                        onSelectedClick = {
                            onSelectClick(item)
                            onPlayClick(item)
                        },
                        onStopClick = {
                            onStopClick()
                        },
                        onNextClick = {
                            onNextClick()
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}





