package com.example.api.models

data class Notification(
    val datetime: Double,
    val id: Int,
    val isRead: Boolean,
    val message: String
)