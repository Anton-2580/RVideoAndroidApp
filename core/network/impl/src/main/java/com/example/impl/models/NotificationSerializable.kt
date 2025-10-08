package com.example.impl.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NotificationSerializable(
    val datetime: Double,
    val id: Int,
    @SerialName("is_read") val isRead: Boolean,
    val message: String
)
