package core.network.api.data.site_api

sealed class ModelsApi(
    rawUrl: String,
): SiteAPI(rawUrl) {
    data object Channel: ApiResourceWithSlug("Channel")
    data object Video: ApiResourceWithSlug("Video")
    data object VideoWithChannels: ApiResourceWithSlug("VideoWithChannels")

    data object Comment: ApiResource("Comment")
    data object Hashtag: ApiResource("Hashtag")
    data object Rating: ApiResource("Rating")
    data object Subscribe: ApiResource("Subscribe")
    data object Notification: ApiResource("Notification")
}

sealed class ApiResource(private val name: String): ModelsApi("$name/") {
    data class Item(val path: String): ApiResource(path)

    fun id(id: Int) = Item("$name/$id/")
}
sealed class ApiResourceWithSlug(private val name: String): ApiResource(name) {
    fun slug(slug: String) = Item("$name/$slug/")
}