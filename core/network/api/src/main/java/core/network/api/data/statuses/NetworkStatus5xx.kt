package core.network.api.data.statuses

sealed class NetworkStatus5xx(
    title: String,
    message: String,
    code: Short,
): NetworkStatus(title, message, code) {
    data object InternalServerError: NetworkStatus5xx(
        title = "Internal Server Error",
        message = "A generic error message indicating an unexpected condition prevented the server from fulfilling the request.",
        code = 500,
    )
    data object NotImplemented: NetworkStatus5xx(
        title = "Not Implemented",
        message = "The server does not support the functionality required to fulfill the request.",
        code = 501,
    )
    data object BadGateway: NetworkStatus5xx(
        title = "Bad Gateway",
        message = "The server, acting as a gateway or proxy, received an invalid response from an upstream server.",
        code = 502,
    )
    data object ServiceUnavailable: NetworkStatus5xx(
        title = "Service Unavailable",
        message = "The server is currently unable to handle the request due to temporary overloading or maintenance.",
        code = 503,
    )
    data object GatewayTimeout: NetworkStatus5xx(
        title = "Gateway Timeout",
        message = "The server, acting as a gateway or proxy, did not receive a timely response from an upstream server.",
        code = 504,
    )
}