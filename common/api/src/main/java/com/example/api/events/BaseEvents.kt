package com.example.api.events

sealed interface BaseEvents: Status {
    open class Error(open val message: String): BaseEvents
    data object Loading: BaseEvents
    data object Successful: BaseEvents
}