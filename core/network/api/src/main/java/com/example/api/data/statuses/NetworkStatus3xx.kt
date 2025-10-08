package com.example.api.data.statuses

sealed class NetworkStatus3xx(
    override val title: String,
    override val message: String,
    override val code: Short,
): NetworkStatus(title, message, code) {
    data object MovedPermanently: NetworkStatus3xx(
        title = "Moved Permanently",
        message = "The requested resource has been assigned a new permanent URI.",
        code = 301,
    )
    data object Found: NetworkStatus3xx(
        title = "Found",
        message = "The requested resource resides temporarily under a different URI.",
        code = 302,
    )
    data object NotModified: NetworkStatus3xx(
        title = "Not Modified",
        message = "The resource has not been modified since the last request.",
        code = 304,
    )
}