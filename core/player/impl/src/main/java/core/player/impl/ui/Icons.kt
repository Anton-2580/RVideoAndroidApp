package core.player.impl.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import core.player.impl.R


val Next: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.next)

val Fullscreen: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.fullscreen)

val FullscreenExit: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.fullscreen_exit)

val Subtitles: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.subtitles)

val SubtitlesFill: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.subtitles_fill)

val PlaySpeed: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.play_speed)

val Quality: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.quality)