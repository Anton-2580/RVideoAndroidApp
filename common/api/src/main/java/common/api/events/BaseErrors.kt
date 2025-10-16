package common.api.events

sealed class BaseErrors(
    message: String,
): BaseEvents.Error(message), Status {
    data object Serialization: BaseErrors("Serialization error")
}