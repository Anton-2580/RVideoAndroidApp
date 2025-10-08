package com.example.api.models

data class ManyItems<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>,
)