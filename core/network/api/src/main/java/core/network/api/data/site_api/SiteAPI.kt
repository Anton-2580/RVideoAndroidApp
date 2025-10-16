package core.network.api.data.site_api

sealed class SiteAPI(
    private val rawUrl: String,
) {
    data object Registration: SiteAPI("registration/")

    val url: String
        get() = PATH_TO_API + rawUrl
    override fun toString() = url

    companion object {
        const val PATH_TO_API: String = "api/"
    }
}