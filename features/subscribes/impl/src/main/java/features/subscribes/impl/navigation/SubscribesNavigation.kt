package features.subscribes.impl.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import core.navigation.api.domain.AppDestinations
import core.navigation.api.domain.ContentDestinations
import core.navigation.api.domain.Navigator
import core.navigation.impl.domain.ContentDestinationsSerializable
import features.subscribes.impl.presentation.SubscribesScreen
import kotlinx.serialization.Serializable


@Serializable
data object ShortsGraph

fun NavGraphBuilder.subscribesGraph(
    navigator: Navigator,
    bottomBar: @Composable (AppDestinations) -> Unit = {},
) {
    navigation<ShortsGraph>(
        startDestination = ContentDestinationsSerializable.SubscribesPage,
    ) {
        composable<ContentDestinationsSerializable.SubscribesPage> {
            Scaffold(
                bottomBar = { bottomBar(ContentDestinations.SubscribesPage) },
            ) { innerPadding ->
                SubscribesScreen(innerPadding)
            }
        }
    }
}


