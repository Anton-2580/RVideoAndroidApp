package common.api.events

sealed interface BaseEvents: Status {
    open class Error(val message: String): BaseEvents
    data object Loading: BaseEvents
    data object Successful: BaseEvents
}