package core.player.impl.presentation.controllGroups

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import common.ui.ripple.CoolRippleIconButton
import common.ui.theme.Arrow
import core.player.api.domain.PlayerCommand


@Composable
fun TopLeftLine(
    onCommand: (PlayerCommand) -> Unit,
    after: @Composable RowScope.() -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CoolRippleIconButton(
            onClick = { onCommand(PlayerCommand.ToggleSizeVersion.MiniVersion) },
        ) {
            Arrow(
                modifier = Modifier
                    .rotate(90f)
            )
        }
        after()
    }
}