package core.player.api.domain

import core.player.api.presentation.states.VideoQuality

sealed class PlayerCommand {
    data object OpenCloseDescription: PlayerCommand()
    sealed class ToggleSizeVersion: PlayerCommand() {
        data object MiniVersion: ToggleSizeVersion()
        data object SemiFullVersion: ToggleSizeVersion()
        data object FullVersion: ToggleSizeVersion()
    }

    sealed class OpenBottomSheet: PlayerCommand() {
        data object Close: OpenBottomSheet()
        data object OpenSettings: OpenBottomSheet()
    }

    data object ToggleFullscreen: PlayerCommand()
    data object Close: PlayerCommand()
    data object ToggleVisible: PlayerCommand()
    data object VisibleOff: PlayerCommand()
    data object PlayPause: PlayerCommand()
    data object Pause: PlayerCommand()
    data object Previous: PlayerCommand()
    data object Next: PlayerCommand()
    data class Seek(val offsetMillis: Long): PlayerCommand()
    data class Speed(val speed: Float): PlayerCommand()
    data class Quality(val quality: VideoQuality): PlayerCommand()
    data object EnableAutoQuality: PlayerCommand()
    data object SeekForward: PlayerCommand()
    data object SeekBackward: PlayerCommand()
    data class ChangeSpeed(val speed: Float): PlayerCommand()
    data object ToggleLoop: PlayerCommand()
    data object ToggleSubtitles: PlayerCommand()
    data object ToggleAutoPlay: PlayerCommand()
    data object ToggleAutoNext: PlayerCommand()
}