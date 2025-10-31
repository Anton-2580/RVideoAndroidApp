package core.player.impl.presentation

import android.content.pm.ActivityInfo
import android.view.OrientationEventListener
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import common.api.domain.Result
import common.impl.presentation.BaseViewModel
import common.impl.presentation.ScaffoldState
import core.network.api.data.HttpClient
import core.network.api.data.site_api.ModelsApi
import core.network.impl.models.VideoWithChannelSerializable
import core.player.api.domain.PlayerCommand
import core.player.api.domain.PlayerController
import core.player.api.domain.VideoPlayer
import core.player.api.domain.VideoQualityController
import core.player.impl.presentation.controllItems.SettingsBottomSheet
import core.player.impl.presentation.states.PlayerUiState
import core.player.impl.presentation.states.VideoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import kotlin.math.abs


@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val httpClient: HttpClient,
    @param:Named("ExoVideoPlayer") val player: VideoPlayer<ExoPlayer, Player.Listener, MediaItem>,
    val playerController: PlayerController<PlayerUiState, Player.Listener, MediaItem>,
    val qualityController: VideoQualityController,
): BaseViewModel() {
    val videoTag = "VideoPlayer"
    private val mutableVideoState = mutableStateOf<VideoWithChannelSerializable?>(null)
    val videoState: State<VideoWithChannelSerializable?> = mutableVideoState

    private val mutableUiState: MutableState<PlayerUiState> = mutableStateOf(PlayerUiState())
    val uiState: State<PlayerUiState> = mutableUiState

    fun onPlayerCommand(playerCommand: PlayerCommand) {
        when (playerCommand) {
            PlayerCommand.Previous -> uiState.value.previousVideo?.let { getVideo(it) }
            PlayerCommand.Next -> uiState.value.nextVideo?.let { getVideo(it) }
            PlayerCommand.Close -> {
                playerController.pause()
                uiState.value.thisVideo.value = VideoState()
                updateUiState { it.copy(
                    visible = true,
                    sizeVersion = PlayerCommand.ToggleSizeVersion.SemiFullVersion,
                ) }
            }
            PlayerCommand.OpenCloseDescription -> updateUiState { it.copy(isOpenDescription = !uiState.value.isOpenDescription) }
            is PlayerCommand.ToggleSizeVersion -> updateUiState { it.copy(
                visible = true,
                sizeVersion = playerCommand,
                isFullscreen = playerCommand === PlayerCommand.ToggleSizeVersion.FullVersion,
            ) }
            is PlayerCommand.OpenBottomSheet -> updateUiState { it.copy(openBottomSheet = playerCommand) }
            PlayerCommand.ToggleVisible -> updateUiState { it.copy(visible = !uiState.value.visible) }
            PlayerCommand.VisibleOff -> updateUiState { it.copy(visible = false) }
            PlayerCommand.ToggleAutoPlay -> updateUiState { it.copy(autoPlay = !uiState.value.autoPlay) }
            PlayerCommand.ToggleFullscreen -> updateUiState {
                isTapFullscreenButton = true
                it.copy(isFullscreen = !uiState.value.isFullscreen)
            }

            PlayerCommand.ToggleAutoNext -> playerController.toggleLoop()
            PlayerCommand.EnableAutoQuality -> qualityController.setAutoQuality()
            is PlayerCommand.Quality -> qualityController.setQuality(playerCommand.quality.index)
            PlayerCommand.PlayPause -> playerController.togglePlayPause()
            PlayerCommand.Pause -> playerController.pause()
            is PlayerCommand.Seek -> playerController.seekTo(playerCommand.offsetMillis)
            is PlayerCommand.Speed -> playerController.setSpeed(playerCommand.speed)
            PlayerCommand.SeekForward -> playerController.seekForward()
            PlayerCommand.SeekBackward -> playerController.seekBackward()
            is PlayerCommand.ChangeSpeed -> playerController.setSpeed(speed = playerCommand.speed)
            PlayerCommand.ToggleLoop -> playerController.toggleLoop()
            PlayerCommand.ToggleSubtitles -> playerController.toggleSubtitles()
        }
    }
    private fun <T> updateState(
        state: MutableState<T>,
        block: (T) -> T,
    ) { state.value = block(state.value) }
    private fun updateUiState(block: (PlayerUiState) -> PlayerUiState) = updateState(mutableUiState, block)

    private var isTapFullscreenButton = false
    private var orientationEventListener: OrientationEventListener? = null
    fun updateFullscreen(
        scaffoldState: MutableState<ScaffoldState>,
    ) {
        if (orientationEventListener !== null) return

        fun toHorizontal(): Int {
            if (uiState.value.sizeVersion == PlayerCommand.ToggleSizeVersion.SemiFullVersion) {
                updateUiState { it.copy(
                    sizeVersion = PlayerCommand.ToggleSizeVersion.FullVersion,
                ) }
            }
            return ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }
        fun toVertical(): Int {
            updateUiState { it.copy(isFullscreen = false) }
            if (uiState.value.sizeVersion == PlayerCommand.ToggleSizeVersion.FullVersion) {
                updateUiState { it.copy(
                    sizeVersion = PlayerCommand.ToggleSizeVersion.SemiFullVersion,
                ) }
            }
            return ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        }

        val activity = scaffoldState.value.activity
        orientationEventListener = object: OrientationEventListener(activity) {
            private var lastAngle = 0

            override fun onOrientationChanged(p0: Int) {
                val newAngle = ((p0 + 44) - ((p0 + 44) % 90)).let {
                    when (it) {
                        90, 270 -> 90
                        0, 180, 360 -> 0
                        else -> it
                    }
                }
                if (isTapFullscreenButton) {
                    isTapFullscreenButton = false
                    lastAngle = newAngle
                    return
                }

                if (abs(newAngle - lastAngle) == 0) return
                lastAngle = newAngle

                val orientation = when (newAngle) {
                    0 -> toVertical()
                    90 -> toHorizontal()

                    else -> ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                }

                activity.requestedOrientation = orientation
            }
        }
        orientationEventListener?.enable()

        viewModelScope.launch {
            snapshotFlow { uiState.value.isFullscreen }.collect {
                activity.requestedOrientation =
                    if (it) toHorizontal()
                    else toVertical()
            }
        }
    }

    private val listeners = listOf(
        playerController.getListener(viewModelScope) {
            updateUiState { playerController.getUiState(it) }
            updateUiState { it.copy(
                quality = qualityController.getCurrentQuality(),
                allQuality = qualityController.getAvailableQualities(),
                isAutoQuality = qualityController.isAutoQuality(),
            ) }
        },
    )

    private fun updatingUiState() = listeners.forEach { playerController.addListener(it) }
    override fun onCleared() {
        listeners.forEach { playerController.removeListener(it) }
        orientationEventListener?.disable()
        player.release()
        super.onCleared()
    }
    init {
        createEventFlow(videoTag)
        updatingUiState()

        viewModelScope.launch {
            snapshotFlow { uiState.value.thisVideo.value }.collect {
                if (it.id === null && null === it.slug) {
                    onPlayerCommand(PlayerCommand.Close)
                } else {
                    Timber.tag("getVideo").d("init")
                    getVideo(it)
                }

                if (!it.isPlaying) {
                    onPlayerCommand(PlayerCommand.Pause)
                }
            }
        }
    }
    fun updateOpenBottomSheet(scaffoldState: MutableState<ScaffoldState>) {
        viewModelScope.launch {
            snapshotFlow { uiState.value.openBottomSheet }.collect { openBottomSheet ->
                val sheetContent: (@Composable ColumnScope.() -> Unit)? = when (openBottomSheet) {
                    PlayerCommand.OpenBottomSheet.OpenSettings -> { { SettingsBottomSheet(
                        playerUiState = uiState,
                        onCommand = { onPlayerCommand(it) },
                        scaffoldState = scaffoldState,
                    ) } }
                    PlayerCommand.OpenBottomSheet.Close -> null
                }

                scaffoldState.value = scaffoldState.value.copy(
                    sheetContent = sheetContent,
                    onDismissRequest = { onPlayerCommand(PlayerCommand.OpenBottomSheet.Close) }
                )
            }
        }
    }

    fun getVideo(video: VideoState) {
        (video.slug?.let { getVideo(slug = it) } !== null ||
                video.id?.let {   getVideo(id = it)   } !== null)
        uiState.value.thisVideo.value = video
    }
    fun getVideo(id: Int) { initVideo(url = ModelsApi.VideoWithChannels.id(id).url) }
    fun getVideo(slug: String) { initVideo(url = ModelsApi.VideoWithChannels.slug(slug).url) }
    private fun initVideo(url: String) {
        workWithData<Exception>(videoTag) {
            val result = httpClient.get<VideoWithChannelSerializable>(url)
            when (result) {
                is Result.Error -> result.data
                is Result.Success -> result.data
            }?.let {
                mutableVideoState.value = it
                it.photo?.let { photo ->
                    player.getPlayer().setMediaItem(MediaItem.fromUri(photo))
                }
                player.prepare(it.mpd.ifBlank { it.video })

                if (uiState.value.autoPlay) {
                    delay(500)
                    onPlayerCommand(PlayerCommand.PlayPause)
                }
            }
        }
    }
}
