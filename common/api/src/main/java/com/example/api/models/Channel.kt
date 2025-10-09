package com.example.api.models

import com.example.api.Model


data class Channel(
    val description: String,
    val id: Int,
    val isBlocked: Boolean,
    val name: String,
    val photo: String?,
    val subscribers: Int,
    val likes: Int,
    val dislikes: Int,
    val slug: String,
): Model