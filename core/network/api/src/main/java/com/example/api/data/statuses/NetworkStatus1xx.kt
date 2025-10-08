package com.example.api.data.statuses

sealed class NetworkStatus1xx(
    override val title: String,
    override val message: String,
    override val code: Short,
): NetworkStatus(title, message, code) {
    data object Continue: NetworkStatus1xx(
        title = "Continue",
        message = "The client should continue with its request.",
        code = 100,
    )
    data object SwitchingProtocols: NetworkStatus1xx(
        title = "Switching Protocols",
        message = "The server is switching protocols as requested by the client. ",
        code = 101,
    )
}