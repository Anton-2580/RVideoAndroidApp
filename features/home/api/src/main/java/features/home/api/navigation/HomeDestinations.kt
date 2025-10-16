package features.home.api.navigation

import core.navigation.api.AppDestinations


sealed class HomeDestinations: AppDestinations {
    data object HomePage: HomeDestinations()
}
