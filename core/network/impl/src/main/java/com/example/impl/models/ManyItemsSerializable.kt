package com.example.impl.models

import com.example.api.Model
import com.example.api.models.ManyItems
import kotlinx.serialization.Serializable


@Serializable
data class ManyItemsSerializable<T: Model>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>,
): Model


fun <T: Model> ManyItemsSerializable<T>.toDomain() = ManyItems(
    count = count,
    next = next,
    previous = previous,
    results = results,
)
fun <T: Model> ManyItems<T>.toSerializable() = ManyItemsSerializable(
    count = count,
    next = next,
    previous = previous,
    results = results,
)