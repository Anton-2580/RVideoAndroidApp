package com.example.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun MiniLogo(modifier: Modifier = Modifier) {
    Image(
        imageVector = MiniLogo,
        contentDescription = "MiniLogo",
        modifier = modifier,
    )
}

@Composable
fun FullLogo(modifier: Modifier = Modifier) {
    Image(
        imageVector = FullLogo,
        contentDescription = "FullLogo",
        modifier = modifier,
    )
}

@Composable
fun Bell(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Bell,
        contentDescription = "Bell",
        modifier = modifier,
    )
}

@Composable
fun Search(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Search,
        contentDescription = "Search",
        modifier = modifier,
    )
}