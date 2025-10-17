package core.network.impl.models

import common.api.domain.Model
import common.api.domain.models.VideoWithChannel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class VideoWithChannelSerializable(
    val browsing: Int,
    val likes: Int,
    val dislikes: Int,
    val channel: ChannelSerializable,
    val datetime: Double,
    val description: String,
    val id: Int,
    @SerialName("is_published") val isPublished: Boolean,
    val photo: String?,
    val slug: String,
    val title: String,
    val video: String,
    val mpd: String = "",
    val m3u8: String = "",
    @SerialName("rewind_frames") val rewindFrames: String = "",
): Model


fun VideoWithChannelSerializable.toDomain() = VideoWithChannel(
    browsing = browsing,
    likes = likes,
    dislikes = dislikes,
    channel = channel.toDomain(),
    datetime = datetime,
    description = description,
    id = id,
    isPublished = isPublished,
    photo = photo,
    slug = slug,
    title = title,
    video = video,
    mpd = mpd,
    m3u8 = m3u8,
    rewindFrames = rewindFrames,
)
fun VideoWithChannel.toSerializable() = VideoWithChannelSerializable(
    browsing = browsing,
    likes = likes,
    dislikes = dislikes,
    channel = channel.toSerializable(),
    datetime = datetime,
    description = description,
    id = id,
    isPublished = isPublished,
    photo = photo,
    slug = slug,
    title = title,
    video = video,
    mpd = mpd,
    m3u8 = m3u8,
    rewindFrames = rewindFrames,
)