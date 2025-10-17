package features.home.impl.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import core.navigation.api.domain.AppDestinations
import core.navigation.api.domain.ContentDestinations
import core.navigation.api.domain.Navigator
import core.navigation.impl.domain.ContentDestinationsSerializable
import features.home.impl.presentation.HomeScreen
import kotlinx.serialization.Serializable


@Serializable
data object HomeGraph

fun NavGraphBuilder.homeGraph(
    navigator: Navigator,
    bottomBar: @Composable (AppDestinations) -> Unit = {},
) {
    navigation<HomeGraph>(
        startDestination = ContentDestinationsSerializable.HomePage,
    ) {
        composable<ContentDestinationsSerializable.HomePage> {
            Scaffold(
                bottomBar = { bottomBar(ContentDestinations.HomePage) },
            ) { innerPadding ->
                HomeScreen(innerPadding)
            }
        }
    }
}


