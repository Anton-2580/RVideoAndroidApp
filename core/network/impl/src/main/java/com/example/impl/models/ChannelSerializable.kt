package com.example.impl.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ChannelSerializable(
    val description: String,
    val id: Int,
    @SerialName("is_blocked") val isBlocked: Boolean,
    val name: String,
    val photo: String?,
    val subscribers: Int,
)