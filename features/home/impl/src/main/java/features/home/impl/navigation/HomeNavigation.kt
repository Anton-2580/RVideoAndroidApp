package features.home.impl.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import core.navigation.api.Navigator
import features.home.impl.presentation.HomeScreen
import kotlinx.serialization.Serializable


@Serializable
data object HomeGraph

fun NavGraphBuilder.homeGraph(
    navigator: Navigator,
    padding: PaddingValues = PaddingValues.Zero,
) {
    navigation<HomeGraph>(
        startDestination = SerializableHomeDestinations.HomePage,
    ) {
        composable<SerializableHomeDestinations.HomePage> {
            HomeScreen(padding)
        }
    }
}


