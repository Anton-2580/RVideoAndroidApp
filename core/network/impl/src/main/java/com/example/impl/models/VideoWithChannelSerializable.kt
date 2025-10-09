package com.example.impl.models

import com.example.api.Model
import com.example.api.models.VideoWithChannel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class VideoWithChannelSerializable(
    val browsing: Int,
    val channel: ChannelSerializable,
    val datetime: Double,
    val description: String,
    val id: Int,
    @SerialName("is_published") val isPublished: Boolean,
    val photo: String?,
    val slug: String,
    val title: String,
    val video: String,
): Model


fun VideoWithChannelSerializable.toDomain() = VideoWithChannel(
    browsing = browsing,
    channel = channel.toDomain(),
    datetime = datetime,
    description = description,
    id = id,
    isPublished = isPublished,
    photo = photo,
    slug = slug,
    title = title,
    video = video,
)
fun VideoWithChannel.toSerializable() = VideoWithChannelSerializable(
    browsing = browsing,
    channel = channel.toSerializable(),
    datetime = datetime,
    description = description,
    id = id,
    isPublished = isPublished,
    photo = photo,
    slug = slug,
    title = title,
    video = video,
)