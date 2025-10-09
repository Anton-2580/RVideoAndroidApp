package com.example.api.events

sealed class BaseErrors(
    override val message: String,
): BaseEvents.Error(message), Status {
    data object Serialization: BaseErrors("Serialization error")
}