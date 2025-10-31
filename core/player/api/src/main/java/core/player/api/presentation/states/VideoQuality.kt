package core.player.api.presentation.states

data class VideoQuality(
    val index: Int,
    val width: Int,
    val height: Int,
    val bitrate: Int,
    val isSelected: Boolean,
)