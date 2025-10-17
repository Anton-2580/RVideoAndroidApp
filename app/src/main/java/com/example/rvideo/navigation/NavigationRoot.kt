package com.example.rvideo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import core.navigation.api.domain.AppDestinations
import core.navigation.api.domain.Navigator
import core.navigation.impl.domain.ContentBottomBarOnActions
import core.navigation.impl.domain.ContentDestinationsSerializable
import core.navigation.impl.domain.NavigatorImpl
import core.navigation.impl.presentation.ContentBottomBar
import features.home.impl.navigation.HomeGraph
import features.home.impl.navigation.homeGraph
import features.profile.impl.navigation.profileGraph
import features.shorts.impl.navigation.shortsGraph
import features.subscribes.impl.navigation.subscribesGraph


@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    val navigator: Navigator = remember { NavigatorImpl(navController) }  // because navController is not in DI

    val contentBottomBar: @Composable (AppDestinations) -> Unit = remember {{ destination ->
        ContentBottomBar(
            selectDestination = destination,
            contentBottomBarOnActions = ContentBottomBarOnActions(
                onHomeAction = { navigator.navigate(ContentDestinationsSerializable.HomePage) },
                onShortsAction = { navigator.navigate(ContentDestinationsSerializable.ShortsPage) },
                onSubscribeAction = { navigator.navigate(ContentDestinationsSerializable.SubscribesPage) },
                onProfileAction = { navigator.navigate(ContentDestinationsSerializable.ProfilePage) },
            ),
        )
    }}

    NavHost(
        navController = navController,
        startDestination = HomeGraph,
    ) {
        homeGraph(
            navigator = navigator,
            bottomBar = contentBottomBar,
        )
        shortsGraph(
            navigator = navigator,
            bottomBar = contentBottomBar,
        )
        subscribesGraph(
            navigator = navigator,
            bottomBar = contentBottomBar,
        )
        profileGraph(
            navigator = navigator,
            bottomBar = contentBottomBar,
        )
    }
}