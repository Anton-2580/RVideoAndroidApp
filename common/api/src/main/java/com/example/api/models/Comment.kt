package com.example.api.models

data class Comment(
    val answer: Int,
    val author: Int,
    val created: String,
    val id: Int,
    val isParent: Boolean,
    val notify: Boolean,
    val text: String,
    val video: Int?,
)