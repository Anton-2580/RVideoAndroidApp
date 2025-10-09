package com.example.api.models

import com.example.api.Model


data class Comment(
    val answer: Int,
    val author: Int,
    val created: String,
    val id: Int,
    val isParent: Boolean,
    val notify: Boolean,
    val text: String,
    val video: Int?,
): Model