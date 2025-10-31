package features.shorts.impl.navigation

import androidx.compose.runtime.DisposableEffect
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
import features.shorts.impl.presentation.ShortsScreen
import kotlinx.serialization.Serializable


@Serializable
data object ShortsGraph

fun NavGraphBuilder.shortsGraph(
    navController: NavHostController,
    navigator: Navigator,
    videoState: MutableState<VideoState>,
    scaffoldState: MutableState<ScaffoldState>,
) {
    navigation<ShortsGraph>(
        startDestination = ContentDestinationsSerializable.ShortsPage,
    ) {
        composable<ContentDestinationsSerializable.ShortsPage> {
            DisposableEffect(Unit) {
                scaffoldState.value = scaffoldState.value.copy(bottomBar = contentBottomBar(
                    navigator = navigator,
                    selectDestination = ContentDestinations.ShortsPage,
                ) )

                videoState.value = videoState.value.copy(isPlaying = false)
                onDispose { videoState.value = videoState.value.copy(isPlaying = true) }
            }

            ShortsScreen(
                padding = scaffoldState.value.padding,
            )
        }
    }
}


