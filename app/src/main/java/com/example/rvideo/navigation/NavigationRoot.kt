package com.example.rvideo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import core.navigation.api.Navigator
import core.navigation.impl.NavigatorImpl
import features.home.impl.navigation.HomeGraph
import features.home.impl.navigation.homeGraph


@Composable
fun NavigationRoot(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues.Zero,
) {
    val navigator: Navigator = NavigatorImpl(navController)  // because navController is not in DI

    NavHost(
        navController = navController,
        startDestination = HomeGraph,
        modifier = modifier
    ) {
        homeGraph(navigator, padding)
    }
}