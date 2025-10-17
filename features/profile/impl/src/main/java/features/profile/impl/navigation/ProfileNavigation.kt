package features.profile.impl.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import core.navigation.api.domain.AppDestinations
import core.navigation.api.domain.ContentDestinations
import core.navigation.api.domain.Navigator
import core.navigation.impl.domain.ContentDestinationsSerializable
import features.profile.impl.presentation.ProfileScreen
import kotlinx.serialization.Serializable


@Serializable
data object ProfileGraph

fun NavGraphBuilder.profileGraph(
    navigator: Navigator,
    bottomBar: @Composable (AppDestinations) -> Unit = {},
) {
    navigation<ProfileGraph>(
        startDestination = ContentDestinationsSerializable.ProfilePage,
    ) {
        composable<ContentDestinationsSerializable.ProfilePage> {
            Scaffold(
                bottomBar = { bottomBar(ContentDestinations.ProfilePage) },
            ) { innerPadding ->
                ProfileScreen(innerPadding)
            }
        }
    }
}


