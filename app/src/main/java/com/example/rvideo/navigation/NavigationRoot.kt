package com.example.rvideo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.api.Navigator
import com.example.impl.NavigatorImpl
import com.example.impl.navigation.HomeGraph
import com.example.impl.navigation.homeGraph


@Composable
fun NavigationRoot(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navigator: Navigator = NavigatorImpl(navController)  // because navController it not in DI

    NavHost(
        navController = navController,
        startDestination = HomeGraph,
        modifier = modifier
    ) {
        homeGraph(navigator)
    }
}