package core.player.impl.presentation.videoController

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import core.network.impl.models.VideoWithChannelSerializable
import core.player.api.domain.PlayerCommand
import core.player.impl.presentation.states.PlayerUiState
import core.player.impl.presentation.controllGroups.BottomLine
import core.player.impl.presentation.controllGroups.CenterLine
import core.player.impl.presentation.controllGroups.TopLeftLine
import core.player.impl.presentation.controllGroups.TopRightLine


@Composable
fun FullVideoPlayerController(
    uiState: State<PlayerUiState>,
    videoState: State<VideoWithChannelSerializable?>,
    size: State<DpSize>,
    seekForwardBackward: Int,
    onCommand: (PlayerCommand) -> Unit,
    modifier: Modifier = Modifier,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    onHoldSpeed: (() -> Unit)? = null,
    onReleaseHold: (() -> Unit)? = null,
) {
    val seekingForward = remember { mutableIntStateOf(0) }
    val seekingBackward = remember { mutableIntStateOf(0) }

    BaseVideoPlayerController(
        state = uiState,
        size = size,
        onCommand = onCommand,
        onHoldSpeed = onHoldSpeed,
        onReleaseHold = onReleaseHold,
        onDrag = onDrag,
        onDragEnd = onDragEnd,
        modifier = modifier,
        seekForwardBackward = seekForwardBackward,
        seekingForward = seekingForward,
        seekingBackward = seekingBackward,
        centerRowControllers = {
            CenterLine(
                seekingForward = seekingForward,
                seekingBackward = seekingBackward,
                state = uiState,
                onCommand = onCommand,
            )
        },
        bottomControllers = {
            BottomLine(
                uiState = uiState,
                onCommand = onCommand,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                }
            }
        },
        topControllers = {
            TopLeftLine(onCommand = onCommand) {
                Text(
                    text = videoState.value?.title ?: "",
                    modifier = Modifier
                        .clickable(
                            onClick = { onCommand(PlayerCommand.OpenCloseDescription) },
                        )
                )
            }

            TopRightLine(
                isSubtitles = uiState.value.subtitlesEnabled,
                isAutoNext = !uiState.value.isLooping,
                onCommand = onCommand,
            )
        },
    )
}

@Preview
@Composable
private fun FullVideoPlayerControllerPreview() {
    val uiState = remember { mutableStateOf(PlayerUiState()) }
    val videoState = remember { mutableStateOf(null) }
    val size = remember { mutableStateOf(DpSize(480.dp, 270.dp)) }
    FullVideoPlayerController(
        uiState = uiState,
        videoState = videoState,
        size = size,
        seekForwardBackward = 10,
        onCommand = {},
        onHoldSpeed = {},
        onReleaseHold = {},
        onDrag = {},
        onDragEnd = {},
    )
}