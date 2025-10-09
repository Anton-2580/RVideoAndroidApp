package com.example.impl.models

import com.example.api.Model
import com.example.api.models.Channel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ChannelSerializable(
    val description: String,
    val id: Int,
    @SerialName("is_blocked") val isBlocked: Boolean,
    val name: String,
    val photo: String?,
    val slug: String,
    val subscribers: Int,
    val likes: Int,
    val dislikes: Int,
): Model


fun ChannelSerializable.toDomain() = Channel(
    description = description,
    id = id,
    isBlocked = isBlocked,
    name = name,
    photo = photo,
    slug = slug,
    subscribers = subscribers,
    likes = likes,
    dislikes = dislikes,
)
fun Channel.toSerializable() = ChannelSerializable(
    description = description,
    id = id,
    isBlocked = isBlocked,
    name = name,
    photo = photo,
    subscribers = subscribers,
    slug = slug,
    likes = likes,
    dislikes = dislikes,
)