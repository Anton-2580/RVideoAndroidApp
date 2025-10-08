package com.example.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.api.Navigator
import com.example.impl.presentation.HomeScreen
import kotlinx.serialization.Serializable


@Serializable
data object HomeGraph

fun NavGraphBuilder.homeGraph(
    navigator: Navigator,
) {
    navigation<HomeGraph>(
        startDestination = SerializableHomeDestinations.HomePage,
    ) {
        composable<SerializableHomeDestinations.HomePage> {
            HomeScreen()
        }
    }
}


