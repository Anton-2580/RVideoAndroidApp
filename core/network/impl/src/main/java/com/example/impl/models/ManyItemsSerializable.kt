package com.example.impl.models

import kotlinx.serialization.Serializable


@Serializable
data class ManyItemsSerializable<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>,
)