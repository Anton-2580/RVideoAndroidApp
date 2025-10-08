package com.example.impl.models

import kotlinx.serialization.Serializable


@Serializable
data class HashtagSerializable(
    val hashtag: String,
    val id: Int,
    val video: Int,
)
