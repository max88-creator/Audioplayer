package com.example.audioplayer.presentation.screens.home_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.audioplayer.ui.theme.SwipeBoxColor

@Composable
fun ErrorHomeScreen(
    error: String?
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (error != null) {
            Text(
                text = error,
                style = TextStyle(
                    color = SwipeBoxColor,
                    fontSize = 25.sp,
                    fontWeight = FontWeight(800)
                )
            )
        }
    }
}

