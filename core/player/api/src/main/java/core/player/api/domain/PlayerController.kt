package core.player.api.domain

import kotlinx.coroutines.CoroutineScope

interface PlayerController<T, L, Media> {
    var lastSpeed: Float
    val seekForwardBackward: Int

    fun getUiState(prevUiState: T): T
    fun getUiState(): T
    fun release()
    fun pause()
    fun play()
    fun togglePlayPause()
    fun seekTo(millis: Long)
    fun seekForward()
    fun seekBackward()
    fun setSpeed(speed: Float)
    fun holdSpeed(multiplier: Float)
    fun resetSpeed()
    fun toggleLoop()
    fun toggleSubtitles()

    fun addListener(listener: L)
    fun removeListener(listener: L)
    fun getListener(scope: CoroutineScope, update: () -> Unit): L

    fun clearPlayList()
    fun getCurrentIndexInPlayList(): Int
    fun getLenPlayList(): Int
    fun getPlayListItem(index: Int): Media
    fun setPlayList(mediaItems: List<Media>)
}