package core.navigation.impl.domain

import core.navigation.api.domain.AppDestinationsSerializable
import kotlinx.serialization.Serializable


@Serializable
sealed class ContentDestinationsSerializable: AppDestinationsSerializable {
    @Serializable
    data object HomePage: ContentDestinationsSerializable()
    @Serializable
    data object ShortsPage: ContentDestinationsSerializable()
    @Serializable
    data object SubscribesPage: ContentDestinationsSerializable()
    @Serializable
    data object ProfilePage: ContentDestinationsSerializable()
}