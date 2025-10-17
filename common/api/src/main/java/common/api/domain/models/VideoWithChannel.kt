package common.api.domain.models

import common.api.domain.Model


data class VideoWithChannel(
    val browsing: Int,
    val likes: Int,
    val dislikes: Int,
    val channel: Channel,
    val datetime: Double,
    val description: String,
    val id: Int,
    val isPublished: Boolean,
    val photo: String?,
    val slug: String,
    val title: String,
    val video: String,
    val mpd: String = "",
    val m3u8: String = "",
    val rewindFrames: String = "",
): Model