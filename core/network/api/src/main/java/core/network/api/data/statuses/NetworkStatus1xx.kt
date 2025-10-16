package core.network.api.data.statuses

sealed class NetworkStatus1xx(
    title: String,
    message: String,
    code: Short,
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