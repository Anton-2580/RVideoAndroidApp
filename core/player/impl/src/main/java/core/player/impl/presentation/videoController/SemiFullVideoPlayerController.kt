package core.player.impl.presentation.videoController

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
import core.player.api.domain.PlayerCommand
import core.player.impl.presentation.states.PlayerUiState
import core.player.impl.presentation.controllGroups.BottomLine
import core.player.impl.presentation.controllGroups.CenterLine
import core.player.impl.presentation.controllGroups.TopLeftLine
import core.player.impl.presentation.controllGroups.TopRightLine


@Composable
fun SemiFullVideoPlayerController(
    state: State<PlayerUiState>,
    size: State<DpSize>,
    seekForwardBackward: Int,
    onCommand: (PlayerCommand) -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    modifier: Modifier = Modifier,
    onHoldSpeed: (() -> Unit)? = null,
    onReleaseHold: (() -> Unit)? = null,
) {
    val seekingForward = remember { mutableIntStateOf(0) }
    val seekingBackward = remember { mutableIntStateOf(0) }

    BaseVideoPlayerController(
        state = state,
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
                state = state,
                onCommand = onCommand,
            )
        },
        bottomControllers = {
            BottomLine(
                uiState = state,
                onCommand = onCommand,
            )
        },
        topControllers = {
            TopLeftLine(onCommand = onCommand)

            TopRightLine(
                isSubtitles = state.value.subtitlesEnabled,
                isAutoNext = !state.value.isLooping,
                onCommand = onCommand,
            )
        },
    )
}

@Preview
@Composable
private fun SemiFullVideoPlayerControllerPreview() {
    val uiState = remember { mutableStateOf(PlayerUiState()) }
    val size = remember { mutableStateOf(DpSize(480.dp, 270.dp)) }
    SemiFullVideoPlayerController(
        state = uiState,
        size = size,
        seekForwardBackward = 10,
        onCommand = {},
        onHoldSpeed = {},
        onReleaseHold = {},
        onDrag = {},
        onDragEnd = {},
    )
}