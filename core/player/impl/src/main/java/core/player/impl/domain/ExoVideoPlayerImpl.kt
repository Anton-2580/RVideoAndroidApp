package core.player.impl.domain

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import core.player.api.domain.VideoPlayer


class ExoVideoPlayerImpl(
    private val player: ExoPlayer,
): VideoPlayer<ExoPlayer, Player.Listener, MediaItem> {
    override fun prepare(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
    }

    override fun getPlayer(): ExoPlayer = player

    override fun play() = player.play()
    override fun pause() = player.pause()
    override fun release() = player.release()

    override fun isPlaying(): Boolean = player.isPlaying
    override fun isLooping(): Boolean = player.repeatMode == Player.REPEAT_MODE_ONE
    override fun isMuted(): Boolean = player.isDeviceMuted
    override fun getCurrentPosition(): Long = player.currentPosition
    override fun getDuration(): Long = player.duration
    override fun getSpeed(): Float = player.playbackParameters.speed
    override fun getVolume(): Int = player.deviceVolume

    override fun seekTo(millis: Long) = player.seekTo(millis)
    override fun setSpeed(speed: Float) = player.setPlaybackSpeed(speed)
    override fun enableLooping() { player.repeatMode = Player.REPEAT_MODE_ONE }
    override fun disableLooping() { player.repeatMode = Player.REPEAT_MODE_OFF }

    override fun addListener(listener: Player.Listener) = player.addListener(listener)
    override fun removeListener(listener: Player.Listener) = player.removeListener(listener)

    override fun clearPlayList() = player.clearMediaItems()
    override fun getLenPlayList(): Int = player.mediaItemCount
    override fun getCurrentIndexInPlayList(): Int = player.currentMediaItemIndex
    override fun getPlayListItem(index: Int) = player.getMediaItemAt(index)
    override fun setPlayList(mediaItems: List<MediaItem>) { player.setMediaItems(mediaItems) }
}