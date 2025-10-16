package core.network.impl.models

import common.api.Model
import common.api.models.Comment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CommentSerializable(
    val answer: Int,
    val author: Int,
    val created: String,
    val id: Int,
    @SerialName("is_parent") val isParent: Boolean,
    val notify: Boolean,
    val text: String,
    val video: Int?,
): Model


fun CommentSerializable.toDomain() = Comment(
    answer = answer,
    author = author,
    created = created,
    id = id,
    isParent = isParent,
    notify = notify,
    text = text,
    video = video,
)
fun Comment.toSerializable() = CommentSerializable(
    answer = answer,
    author = author,
    created = created,
    id = id,
    isParent = isParent,
    notify = notify,
    text = text,
    video = video,
)
