package com.example.impl.models

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
)
