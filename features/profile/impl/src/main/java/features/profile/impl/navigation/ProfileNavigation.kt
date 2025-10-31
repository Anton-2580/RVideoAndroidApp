package features.profile.impl.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import common.impl.presentation.ScaffoldState
import core.navigation.api.domain.ContentDestinations
import core.navigation.api.domain.Navigator
import core.navigation.impl.domain.ContentDestinationsSerializable
import core.navigation.impl.presentation.contentBottomBar
import features.profile.impl.presentation.ProfileScreen
import kotlinx.serialization.Serializable


@Serializable
data object ProfileGraph

fun NavGraphBuilder.profileGraph(
    navigator: Navigator,
    scaffoldState: MutableState<ScaffoldState>,
) {
    navigation<ProfileGraph>(
        startDestination = ContentDestinationsSerializable.ProfilePage,
    ) {
        composable<ContentDestinationsSerializable.ProfilePage> {
            LaunchedEffect(Unit) {
                scaffoldState.value = scaffoldState.value.copy(bottomBar = contentBottomBar(
                    navigator = navigator,
                    selectDestination = ContentDestinations.ProfilePage
                ) )
            }

            ProfileScreen(
                padding = scaffoldState.value.padding,
            )
        }
    }
}


