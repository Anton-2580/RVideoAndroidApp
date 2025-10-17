package core.network.impl.models

import common.api.domain.Model
import common.api.domain.models.Channel
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
): Model


fun ChannelSerializable.toDomain() = Channel(
    description = description,
    id = id,
    isBlocked = isBlocked,
    name = name,
    photo = photo,
    slug = slug,
    subscribers = subscribers,
)
fun Channel.toSerializable() = ChannelSerializable(
    description = description,
    id = id,
    isBlocked = isBlocked,
    name = name,
    photo = photo,
    subscribers = subscribers,
    slug = slug,
)