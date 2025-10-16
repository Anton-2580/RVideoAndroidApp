package core.network.impl.models

import common.api.Model
import common.api.models.Video
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class VideoSerializable(
    val browsing: Int,
    val likes: Int,
    val dislikes: Int,
    val channel: Int,
    val datetime: Double,
    val description: String,
    val id: Int,
    @SerialName("is_published") val isPublished: Boolean,
    val photo: String?,
    val slug: String,
    val title: String,
    val video: String,
): Model



fun VideoSerializable.toDomain() = Video(
    browsing = browsing,
    likes = likes,
    dislikes = dislikes,
    channel = channel,
    datetime = datetime,
    description = description,
    id = id,
    isPublished = isPublished,
    photo = photo,
    slug = slug,
    title = title,
    video = video,
)
fun Video.toSerializable() = VideoSerializable(
    browsing = browsing,
    likes = likes,
    dislikes = dislikes,
    channel = channel,
    datetime = datetime,
    description = description,
    id = id,
    isPublished = isPublished,
    photo = photo,
    slug = slug,
    title = title,
    video = video,
)