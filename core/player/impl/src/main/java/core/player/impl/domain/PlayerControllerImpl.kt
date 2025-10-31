package core.player.impl.domain

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import core.player.api.domain.PlayerController
import core.player.api.domain.VideoPlayer
import core.player.impl.presentation.states.PlayerUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PlayerControllerImpl(
    private val player: VideoPlayer<ExoPlayer, Player.Listener, MediaItem>,
): PlayerController<PlayerUiState, Player.Listener, MediaItem> {
    override var lastSpeed: Float = 1f
    override val seekForwardBackward = 10000

    override fun getUiState(prevUiState: PlayerUiState): PlayerUiState = prevUiState.copy(
        isPlaying = player.isPlaying(),
        isLooping = player.isLooping(),
        isMuted = player.isMuted(),
        duration = player.getDuration(),
        position = player.getCurrentPosition(),
        progress = player.getCurrentPosition() / player.getDuration().toFloat(),
        speed = player.getSpeed(),
        volume = player.getVolume()
    )
    override fun getUiState(): PlayerUiState = getUiState(PlayerUiState())

    override fun release() = player.release()
    override fun pause() = player.pause()
    override fun play() = player.play()
    override fun togglePlayPause() {
        if (player.isPlaying()) player.pause()
        else player.play()
    }

    override fun seekTo(millis: Long) = player.seekTo(millis)
    override fun seekForward() = player.seekTo(player.getCurrentPosition() + seekForwardBackward)
    override fun seekBackward() = player.seekTo(player.getCurrentPosition() - seekForwardBackward)
    override fun setSpeed(speed: Float) = player.setSpeed(speed)
    override fun resetSpeed() = player.setSpeed(1f)

    override fun holdSpeed(multiplier: Float) {
        lastSpeed = player.getSpeed()
        player.setSpeed(multiplier)
    }

    override fun toggleLoop() {
        if (player.isLooping()) player.disableLooping()
        else player.enableLooping()
    }

    override fun toggleSubtitles() {
        // TODO
    }

    override fun addListener(listener: Player.Listener) = player.addListener(listener)
    override fun removeListener(listener: Player.Listener) = player.removeListener(listener)

    override fun getListener(
        scope: CoroutineScope,
        update: () -> Unit,
    ) = object : Player.Listener {
        private var job: Job? = null
        override fun onEvents(player: Player, events: Player.Events) {
            update()

            if (events.contains(Player.EVENT_IS_LOADING_CHANGED)) {
                job?.cancel()
                job = scope.launch {
                    while (player.isPlaying) {
                        delay(100)
                        update()
                    }
                }
            }
        }
    }

    override fun clearPlayList() = player.clearPlayList()
    override fun getCurrentIndexInPlayList(): Int = player.getCurrentIndexInPlayList()
    override fun getLenPlayList(): Int = player.getLenPlayList()
    override fun getPlayListItem(index: Int): MediaItem = player.getPlayListItem(index)
    override fun setPlayList(mediaItems: List<MediaItem>) = player.setPlayList(mediaItems)
}