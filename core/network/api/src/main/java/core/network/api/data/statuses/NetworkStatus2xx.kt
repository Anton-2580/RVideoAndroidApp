package core.network.api.data.statuses

sealed class NetworkStatus2xx(
    title: String,
    message: String,
    code: Short,
): NetworkStatus(title, message, code) {
    data object OK: NetworkStatus2xx(
        title = "OK",
        message = "The request was successful.",
        code = 200,
    )
    data object Created: NetworkStatus2xx(
        title = "Created",
        message = "The request has been fulfilled and a new resource has been created.",
        code = 201,
    )
    data object NoContent: NetworkStatus2xx(
        title = "No Content",
        message = "The server successfully processed the request, but is not returning any content.",
        code = 204,
    )
}