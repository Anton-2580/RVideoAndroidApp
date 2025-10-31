package core.player.impl.presentation.controllGroups

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import common.ui.ripple.CoolRippleIconButton
import core.player.api.domain.PlayerCommand
import core.player.impl.presentation.states.PlayerUiState
import core.player.impl.presentation.controllItems.PlayPause
import core.player.impl.presentation.controllItems.Rewind
import core.player.impl.ui.Next


@Composable
fun CenterLine(
    seekingBackward: MutableIntState,
    seekingForward: MutableIntState,
    state: State<PlayerUiState>,
    onCommand: (PlayerCommand) -> Unit,
    modifier: Modifier = Modifier,
) {
    Rewind(
        rewindValue = seekingBackward,
        modifier = modifier
            .graphicsLayer(rotationY = 180f)
    )

    CoolRippleIconButton(
        enabled = state.value.previousVideo !== null,
        onClick = { onCommand(PlayerCommand.Previous) },
    ) {
        Next(modifier = Modifier.rotate(180f))
    }

    PlayPause(
        onClick = { onCommand(PlayerCommand.PlayPause) },
        isPlaying = state.value.isPlaying,
    )

    CoolRippleIconButton(
        enabled = state.value.nextVideo !== null,
        onClick = { onCommand(PlayerCommand.Next) },
    ) {
        Next()
    }

    Rewind(rewindValue = seekingForward)
}