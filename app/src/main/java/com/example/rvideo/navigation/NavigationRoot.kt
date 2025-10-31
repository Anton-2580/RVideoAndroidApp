package com.example.rvideo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import common.impl.presentation.ScaffoldState
import core.navigation.api.domain.Navigator
import core.navigation.impl.domain.ContentGraph
import core.navigation.impl.domain.NavigatorImpl
import core.player.impl.presentation.states.VideoState
import features.home.impl.navigation.HomeGraph
import features.home.impl.navigation.homeGraph
import features.profile.impl.navigation.profileGraph
import features.shorts.impl.navigation.shortsGraph
import features.subscribes.impl.navigation.subscribesGraph


@Composable
fun NavigationRoot(
    navController: NavHostController,
    scaffoldState: MutableState<ScaffoldState>,
    videoState: MutableState<VideoState>,
) {
    val navigator: Navigator = remember { NavigatorImpl(navController) }  // because navController is not in DI

    NavHost(
        navController = navController,
        startDestination = ContentGraph,
    ) {
        navigation<ContentGraph>(
            startDestination = HomeGraph,
        ) {
            homeGraph(
                navController = navController,
                navigator = navigator,
                videoState = videoState,
                scaffoldState = scaffoldState,
            )
            shortsGraph(
                navController = navController,
                navigator = navigator,
                videoState = videoState,
                scaffoldState = scaffoldState,
            )
            subscribesGraph(
                navController = navController,
                navigator = navigator,
                videoState = videoState,
                scaffoldState = scaffoldState,
            )
            profileGraph(
                navigator = navigator,
                scaffoldState = scaffoldState,
            )
        }
    }
}