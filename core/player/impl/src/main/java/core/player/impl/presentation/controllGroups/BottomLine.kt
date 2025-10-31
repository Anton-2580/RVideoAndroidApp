package core.player.impl.presentation.controllGroups

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.ui.ripple.CoolRippleIconButton
import core.player.api.domain.PlayerCommand
import core.player.impl.presentation.states.PlayerUiState
import core.player.impl.presentation.controllItems.Slider
import core.player.impl.ui.Fullscreen
import core.player.impl.ui.FullscreenExit


@Composable
fun BoxScope.BottomLine(
    uiState: State<PlayerUiState>,
    onCommand: (PlayerCommand) -> Unit,
    modifier: Modifier = Modifier,
    after: @Composable () -> Unit = {},
) {
    Column(
        modifier = modifier
            .align(Alignment.BottomCenter)
    ) {
        CoolRippleIconButton(
            onClick = { onCommand(PlayerCommand.ToggleFullscreen) },
            modifier = Modifier.align(Alignment.End)
        ) {
            if (uiState.value.isFullscreen) FullscreenExit()
            else Fullscreen()
        }

        Slider(
            value = uiState.value.progress,
            onValueChange = {onCommand(
                PlayerCommand.Seek((it * uiState.value.duration).toLong()))
            },
        )
        after()
    }
}