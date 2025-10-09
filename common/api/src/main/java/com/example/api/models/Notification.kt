package com.example.api.models

import com.example.api.Model


data class Notification(
    val datetime: Double,
    val id: Int,
    val isRead: Boolean,
    val message: String
): Model