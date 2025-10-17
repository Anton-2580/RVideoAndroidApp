package features.home.api.domain

import features.home.api.R
import common.api.domain.ValuesSealed


sealed class ContentFilter(
    val resId: Int,
    val isPlurals: Boolean = false,
) {
    companion object {
        val valuesSealed = ValuesSealed(ContentFilter::class)
    }

    data object All: ContentFilter(R.string.all)
    data object Shorts: ContentFilter(R.string.shorts)
    data object Video: ContentFilter(R.string.video)
    data object NotWatched: ContentFilter(R.string.not_watched)
    data object Watched: ContentFilter(R.string.watched)
    data object RecentlyPublished: ContentFilter(R.string.recently_published)
    data object Translations: ContentFilter(R.string.translations)
    data object Playlists: ContentFilter(R.string.playlists)
    data class SomeDurationTime(val time: Int): ContentFilter(R.plurals.some_duration_time, isPlurals = true)
}
