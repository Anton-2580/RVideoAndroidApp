package core.player.api.domain

interface VideoPlayer<T, L, Media> {
    fun prepare(url: String)
    fun getPlayer(): T
    fun play()
    fun pause()
    fun release()
    fun isPlaying(): Boolean
    fun isLooping(): Boolean
    fun isMuted(): Boolean
    fun getCurrentPosition(): Long
    fun getDuration(): Long
    fun getSpeed(): Float
    fun getVolume(): Int
    fun seekTo(millis: Long)
    fun setSpeed(speed: Float)
    fun enableLooping()
    fun disableLooping()

    fun addListener(listener: L)
    fun removeListener(listener: L)

    fun clearPlayList()
    fun getCurrentIndexInPlayList(): Int
    fun getLenPlayList(): Int
    fun getPlayListItem(index: Int): Media
    fun setPlayList(mediaItems: List<Media>)
}