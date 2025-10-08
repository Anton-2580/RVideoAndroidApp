package com.example.api.data.statuses

sealed class NetworkStatus4xx(
    override val title: String,
    override val message: String,
    override val code: Short,
): NetworkStatus(title, message, code) {
    data object Conflict: NetworkStatus4xx(
        title = "Conflict",
        message = "",
        code = 409,
    )
    data object PayloadTooLarge: NetworkStatus4xx(
        title = "PayloadTooLarge",
        message = "",
        code = 413,
    )
    data object TooManyRequest: NetworkStatus4xx(
        title = "TooManyRequest",
        message = "",
        code = 429,
    )
    data object BadRequest: NetworkStatus4xx(
        title = "Bad Request",
        message = "The server cannot process the request due to a client error (e.g., malformed request syntax)." ,
        code = 400,
    )
    data object Unauthorized: NetworkStatus4xx(
        title = "Unauthorized",
        message = "Authentication is required and has failed or has not yet been provided.",
        code = 401,
    )
    data object Forbidden: NetworkStatus4xx(
        title = "Forbidden",
        message = "The server understood the request but refuses to authorize it.",
        code = 403,
    )
    data object NotFound: NetworkStatus4xx(
        title = "Not Found",
        message = "The server cannot find the requested resource.",
        code = 404,
    )
    data object MethodNotAllowed: NetworkStatus4xx(
        title = "Method Not Allowed",
        message = "The request method is known by the server but is not supported by the target resource.",
        code = 405,
    )
    data object RequestTimeout: NetworkStatus4xx(
        title = "Request Timeout",
        message = "The server timed out waiting for the request.",
        code = 408,
    )
    data object TooManyRequests: NetworkStatus4xx(
        title = "Request Timeout",
        message = "The user has sent too many requests in a given amount of time.",
        code = 429,
    )
}