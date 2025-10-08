package com.example.impl.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class VideoSerializable(
    val browsing: Int,
    val channel: Int,
    val datetime: Double,
    val description: String,
    val id: Int,
    @SerialName("is_published") val isPublished: Boolean,
    val photo: String?,
    val slug: String,
    val title: String,
    val video: String,
)