package com.example.api.errors

sealed class BaseErrors(
    open val message: String,
): Status {
    data object Serialization: BaseErrors("Serialization error")
}