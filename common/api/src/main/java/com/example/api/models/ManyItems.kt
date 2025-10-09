package com.example.api.models

import com.example.api.Model


data class ManyItems<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>,
): Model