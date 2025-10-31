package core.player.impl.presentation.videoController

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import common.ui.ripple.CoolRippleIconButton
import common.ui.theme.Close
import core.player.api.domain.PlayerCommand
import core.player.impl.presentation.states.PlayerUiState
import core.player.impl.presentation.controllItems.PlayPause
import core.player.impl.presentation.controllItems.Slider


@Composable
fun MiniVideoPlayerController(
    state: State<PlayerUiState>,
    size: State<DpSize>,
    onCommand: (PlayerCommand) -> Unit,
    onTap: () -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseVideoPlayerController(
        state = state,
        size = size,
        onCommand = onCommand,
        modifier = modifier,
        onTap = onTap,
        onDrag = onDrag,
        onDragEnd = onDragEnd,
        indent = 0.dp,
        topControllersModifier = Modifier.padding(16.dp),
        bottomControllers = {
            Slider(
                value = state.value.progress,
                onValueChange = {onCommand(
                    PlayerCommand.Seek((it * state.value.duration).toLong()))
                },
                trackHeight = 4.dp,
                thumbSize = 0.dp,
                modifier = Modifier
                    .align(Alignment.BottomCenter),
            )

        },
        topControllers = {
            PlayPause(
                onClick = { onCommand(PlayerCommand.PlayPause) },
                isPlaying = state.value.isPlaying,
                size = 20.dp,
            )
            CoolRippleIconButton(
                onClick = { onCommand(PlayerCommand.Close) },
            ) {
                Close()
            }
        },
    )
}

@Preview
@Composable
private fun MiniVideoPlayerControllerPreview() {
    val uiState = remember { mutableStateOf(PlayerUiState()) }
    val size = remember { mutableStateOf(DpSize(480.dp, 270.dp)) }
    MiniVideoPlayerController(
        state = uiState,
        size = size,
        onCommand = {},
        onTap = {},
        onDrag = {},
        onDragEnd = {},
    )
}