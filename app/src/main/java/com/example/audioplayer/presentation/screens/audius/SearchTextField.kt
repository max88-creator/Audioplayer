package com.example.audioplayer.presentation.screens.audius

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.audioplayer.R
import com.example.audioplayer.ui.theme.DarkPrimary

@Composable
fun SearchTextField(
    isLoading: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    onSearchTracks: () -> Unit
) {
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(15.dp)),
        trailingIcon = {
            IconButton(
                onClick = {
                    onSearchTracks()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "search",
                    tint = if (!isLoading) {
                        DarkPrimary
                    } else {
                        Color.Black
                    }
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}