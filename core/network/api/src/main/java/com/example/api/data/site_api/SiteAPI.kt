package com.example.api.data.site_api

sealed class SiteAPI(
    protected open val rawUrl: String,
) {
    data object Registration: SiteAPI("registration/")

    val url: String
        get() = PATH_TO_API + rawUrl
    operator fun invoke() = url

    companion object {
        const val PATH_TO_API: String = "api/"
    }
}