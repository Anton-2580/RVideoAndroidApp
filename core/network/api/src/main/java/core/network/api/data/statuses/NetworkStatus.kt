package core.network.api.data.statuses

import common.api.ValuesSealed
import common.api.events.Status


sealed class NetworkStatus(
    val title: String,
    val message: String,
    val code: Short,
): Status {
    override fun toString() = "${this::class.simpleName}(code=$code, title=$title, message=$message)"
    companion object {
        val valuesSealed = ValuesSealed(NetworkStatus::class)
    }

    data object Unknown: NetworkStatus("Unknown", "Unknown status", 0)
    data object NoInternet: NetworkStatus("No Internet", "No Internet", 1)
    data object Serialization: NetworkStatus("Serialization", "Serialization Internet", 2)

    class Error(
        val status: NetworkStatus,
        message: String,
        title: String = "Unknown error",
    ): NetworkStatus(title, message, 3)
}