package com.example.rvideo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.rvideo.navigation.NavigationRoot
import common.impl.presentation.ScaffoldState
import common.ui.theme.RVideoTheme
import core.player.api.domain.PlayerCommand
import core.player.impl.presentation.VideoPlayerView
import core.player.impl.presentation.VideoPlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Timber.Forest.plant(Timber.DebugTree())
        }

        setContent {
            val navController = rememberNavController()
            val sheetState = rememberModalBottomSheetState()
            val scaffoldState = remember(this) { mutableStateOf(
                value = ScaffoldState(activity = this)
            ) }
            var scaffoldValue by scaffoldState
            val videoPlayerViewModel: VideoPlayerViewModel = hiltViewModel()
            val videoState = videoPlayerViewModel.uiState.value.thisVideo

            RVideoTheme(darkTheme = true, dynamicColor = false) {
                Scaffold(
                    bottomBar = scaffoldValue.bottomBar,
                ) { paddingValues ->
                    LaunchedEffect(paddingValues, scaffoldValue.padding) {
                        scaffoldValue = scaffoldValue.copy(padding = paddingValues)
                    }

                    NavigationRoot(
                        navController = navController,
                        scaffoldState = scaffoldState,
                        videoState = videoState,
                    )
                }
                VideoPlayer(scaffoldState, videoPlayerViewModel)

                scaffoldValue.sheetContent?.let {
                    ModalBottomSheet(
                        sheetState = sheetState,
                        onDismissRequest = {
                            scaffoldValue = scaffoldValue.copy(sheetContent = null)
                            scaffoldValue.onDismissRequest()
                        },
                        content = it,
                    )
                }
            }
        }
    }
}

@Composable
private fun VideoPlayer(
    scaffoldState: MutableState<ScaffoldState>,
    viewModel: VideoPlayerViewModel,
) {
    val padding =
        if (viewModel.uiState.value.sizeVersion == PlayerCommand.ToggleSizeVersion.FullVersion) PaddingValues()
        else PaddingValues(top = scaffoldState.value.padding.calculateTopPadding())

    VideoPlayerView(
        scaffoldState = scaffoldState,
        bottomMiniPlayerOffset = scaffoldState.value.padding.calculateBottomPadding()*2,
        viewModel = viewModel,
        modifier = Modifier
            .padding(padding)
            .zIndex(1f)
    )

}
