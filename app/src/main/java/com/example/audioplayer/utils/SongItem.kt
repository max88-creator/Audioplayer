package com.example.audioplayer.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audioplayer.R
import com.example.audioplayer.ui.theme.DarkPrimary
import com.example.audioplayer.ui.theme.PurpleGrey40
import com.example.audioplayer.ui.theme.SongName
import com.example.audioplayer.ui.theme.SwipeBoxColor

@Composable
fun SongItem(
    artist: String,
    title: String,
    isPlaying: Boolean,
    onPreviousClick: () -> Unit,
    onPauseClick: () -> Unit,
    onSelectedClick: () -> Unit,
    onStopClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)

            .background(
                color = PurpleGrey40.copy(alpha = 0.7f),
                shape = RoundedCornerShape(7.dp)
            )
    ) {
        Column() {
            Spacer(modifier = Modifier.height(12.dp))
            Row() {
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "artist: ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color.Black
                )
                Text(
                    text = artist,
                    fontSize = 17.sp,
                    fontWeight = FontWeight(750),
                    color = SongName
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row() {
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "title: ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color.Black
                )
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight(750),
                    color = SongName
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row() {
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_skip_previous),
                    contentDescription = "previous button",
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            onPreviousClick()
                        },
                    tint = DarkPrimary
                )
                if (!isPlaying) {
                    Icon(
                        painter = painterResource(R.drawable.ic_play),
                        contentDescription = "play button",
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                onSelectedClick()
                            },
                        tint = DarkPrimary
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_pause),
                        contentDescription = "pause button",
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                onPauseClick()
                            },
                        tint = DarkPrimary
                    )
                }
                Spacer(modifier = Modifier.width(17.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_stop),
                    contentDescription = "stop button",
                    modifier = Modifier
                        .size(33.dp)
                        .clickable {
                            onStopClick()
                        },
                    tint = SwipeBoxColor
                )
                Spacer(modifier = Modifier.width(17.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_skip_next),
                    contentDescription = "forward button",
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            onNextClick()
                        },
                    tint = DarkPrimary
                )
            }
        }
    }
}

