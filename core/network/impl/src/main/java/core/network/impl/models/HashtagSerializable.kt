package core.network.impl.models

import common.api.Model
import common.api.models.Hashtag
import kotlinx.serialization.Serializable


@Serializable
data class HashtagSerializable(
    val hashtag: String,
    val id: Int,
    val video: Int,
): Model


fun HashtagSerializable.toDomain() = Hashtag(
    hashtag = hashtag,
    id = id,
    video = video,
)
fun Hashtag.toSerializable() = HashtagSerializable(
    hashtag = hashtag,
    id = id,
    video = video,
)
