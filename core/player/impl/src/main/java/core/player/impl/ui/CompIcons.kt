package core.player.impl.ui

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun Next(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Next,
        contentDescription = "Next Video",
        modifier = modifier
    )
}

@Composable
fun Fullscreen(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Fullscreen,
        contentDescription = "Next Video",
        modifier = modifier
    )
}

@Composable
fun FullscreenExit(modifier: Modifier = Modifier) {
    Icon(
        imageVector = FullscreenExit,
        contentDescription = "Next Video",
        modifier = modifier
    )
}

@Composable
fun Subtitles(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Subtitles,
        contentDescription = "Subtitles",
        modifier = modifier,
    )
}

@Composable
fun SubtitlesFill(modifier: Modifier = Modifier) {
    Icon(
        imageVector = SubtitlesFill,
        contentDescription = "SubtitlesFill",
        modifier = modifier,
    )
}

@Composable
fun PlaySpeed(modifier: Modifier = Modifier) {
    Icon(
        imageVector = PlaySpeed,
        contentDescription = "PlaySpeed",
        modifier = modifier,
    )
}

@Composable
fun Quality(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Quality,
        contentDescription = "Quality",
        modifier = modifier,
    )
}