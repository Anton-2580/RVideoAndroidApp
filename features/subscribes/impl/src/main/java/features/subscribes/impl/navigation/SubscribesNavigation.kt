package features.subscribes.impl.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import common.impl.presentation.ScaffoldState
import core.navigation.api.domain.ContentDestinations
import core.navigation.api.domain.Navigator
import core.navigation.impl.domain.ContentDestinationsSerializable
import core.navigation.impl.presentation.contentBottomBar
import core.player.impl.presentation.states.VideoState
import features.subscribes.impl.presentation.SubscribesScreen
import kotlinx.serialization.Serializable


@Serializable
data object SubscribesGraph

fun NavGraphBuilder.subscribesGraph(
    navController: NavHostController,
    navigator: Navigator,
    videoState: MutableState<VideoState>,
    scaffoldState: MutableState<ScaffoldState>,
) {
    navigation<SubscribesGraph>(
        startDestination = ContentDestinationsSerializable.SubscribesPage,
    ) {
        composable<ContentDestinationsSerializable.SubscribesPage> {
            LaunchedEffect(Unit) {
                scaffoldState.value = scaffoldState.value.copy(bottomBar = contentBottomBar(
                    navigator = navigator,
                    selectDestination = ContentDestinations.SubscribesPage,
                ) )
            }

            SubscribesScreen(
                padding = scaffoldState.value.padding,
            )
        }
    }
}


