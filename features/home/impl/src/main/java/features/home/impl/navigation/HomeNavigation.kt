package features.home.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import common.impl.presentation.ScaffoldState
import core.navigation.api.domain.ContentDestinations
import core.navigation.api.domain.Navigator
import core.navigation.impl.domain.ContentDestinationsSerializable
import core.navigation.impl.presentation.contentBottomBar
import core.navigation.impl.presentation.utils.getParentEntry
import core.player.impl.presentation.states.VideoState
import features.home.impl.presentation.ChannelScreen
import features.home.impl.presentation.HomeScreen
import kotlinx.serialization.Serializable


@Serializable
data object HomeGraph

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    navigator: Navigator,
    videoState: MutableState<VideoState>,
    scaffoldState: MutableState<ScaffoldState>,
) {
    @Composable
    fun SelectedHomePage() {
        LaunchedEffect(Unit) {
            scaffoldState.value = scaffoldState.value.copy(bottomBar = contentBottomBar(
                navigator = navigator,
                selectDestination = ContentDestinations.HomePage,
            ) )
        }
    }

    navigation<HomeGraph>(
        startDestination = ContentDestinationsSerializable.HomePage,
    ) {
        composable<ContentDestinationsSerializable.HomePage> { backStackEntry ->
            SelectedHomePage()

            HomeScreen(
                padding = scaffoldState.value.padding,
                onVideoClick = { videoState.value = videoState.value.copy(id = it.id, slug = it.slug) },
                navigator = navigator,
                viewModel = hiltViewModel(getParentEntry(navController, backStackEntry)),
            )
        }

        composable<ContentDestinationsSerializable.ChannelPage> {
            SelectedHomePage()

            ChannelScreen(
                padding = scaffoldState.value.padding,
            )
        }
    }
}


