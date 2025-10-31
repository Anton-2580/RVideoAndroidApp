package core.player.impl.presentation.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import core.player.api.domain.PlayerCommand
import core.player.api.presentation.states.VideoQuality

@Stable
data class PlayerUiState(
    val sizeVersion: PlayerCommand.ToggleSizeVersion = PlayerCommand.ToggleSizeVersion.SemiFullVersion,
    val openBottomSheet: PlayerCommand.OpenBottomSheet = PlayerCommand.OpenBottomSheet.Close,

    val isOpenDescription: Boolean = false,
    val visible: Boolean = true,
    val autoPlay: Boolean = true,
    val isFullscreen: Boolean = false,
    val isPlaying: Boolean = false,
    val isLooping: Boolean = true,
    val isMuted: Boolean = false,
    val subtitlesEnabled: Boolean = false,
    val isAutoQuality: Boolean = true,

    val duration: Long = 0,
    val position: Long = 0,
    val progress: Float = 0f,
    val speed: Float = 1f,
    val volume: Int = 100,

    val thisVideo: MutableState<VideoState> = mutableStateOf(VideoState()),
    val nextVideo: VideoState? = null,
    val previousVideo: VideoState? = null,

    val quality: VideoQuality? = null,
    val allQuality: List<VideoQuality> = emptyList(),
)