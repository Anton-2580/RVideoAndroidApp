package features.shorts.impl.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import core.navigation.api.domain.AppDestinations
import core.navigation.api.domain.ContentDestinations
import core.navigation.api.domain.Navigator
import core.navigation.impl.domain.ContentDestinationsSerializable
import features.shorts.impl.presentation.ShortsScreen
import kotlinx.serialization.Serializable


@Serializable
data object ShortsGraph

fun NavGraphBuilder.shortsGraph(
    navigator: Navigator,
    bottomBar: @Composable (AppDestinations) -> Unit = {},
) {
    navigation<ShortsGraph>(
        startDestination = ContentDestinationsSerializable.ShortsPage,
    ) {
        composable<ContentDestinationsSerializable.ShortsPage> {
            Scaffold(
                bottomBar = { bottomBar(ContentDestinations.ShortsPage) },
            ) { innerPadding ->
                ShortsScreen(innerPadding)
            }
        }
    }
}


