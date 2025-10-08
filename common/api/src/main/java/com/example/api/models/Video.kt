package com.example.api.models

data class Video(
    val browsing: Int,
    val channel: Int,
    val datetime: Double,
    val description: String,
    val id: Int,
    val isPublished: Boolean,
    val photo: String?,
    val slug: String,
    val title: String,
    val video: String,
)