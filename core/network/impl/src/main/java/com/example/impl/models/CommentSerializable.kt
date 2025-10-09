package com.example.impl.models

import com.example.api.Model
import com.example.api.models.Channel
import com.example.api.models.Comment
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
