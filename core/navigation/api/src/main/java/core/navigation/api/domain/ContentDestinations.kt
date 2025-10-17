package core.navigation.api.domain


sealed class ContentDestinations: AppDestinations {
    data object HomePage: ContentDestinations()
    data object ShortsPage: ContentDestinations()
    data object SubscribesPage: ContentDestinations()
    data object ProfilePage: ContentDestinations()
}