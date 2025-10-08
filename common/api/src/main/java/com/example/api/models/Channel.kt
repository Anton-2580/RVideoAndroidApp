package com.example.api.models

data class Channel(
    val description: String,
    val id: Int,
    val isBlocked: Boolean,
    val name: String,
    val photo: String?,
    val subscribers: Int
)