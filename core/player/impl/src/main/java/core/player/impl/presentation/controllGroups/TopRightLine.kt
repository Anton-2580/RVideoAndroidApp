package core.player.impl.presentation.controllGroups

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import common.ui.ripple.CoolRippleIconButton
import common.ui.theme.Settings
import core.player.api.domain.PlayerCommand
import core.player.impl.ui.Subtitles
import core.player.impl.ui.SubtitlesFill
import kotlin.math.abs


@Composable
fun TopRightLine(
    isSubtitles: Boolean,
    isAutoNext: Boolean,
    onCommand: (PlayerCommand) -> Unit,
    modifier: Modifier = Modifier,
    after: @Composable RowScope.() -> Unit = {},
    before: @Composable RowScope.() -> Unit = {},
) {
    val surface = MaterialTheme.colorScheme.surface
    val onSurface = MaterialTheme.colorScheme.onSurface
    val animationState = animateFloatAsState(
        targetValue = if (isAutoNext) 1f else 0f,
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        before()

        CoolRippleIconButton(
            onClick = { onCommand(PlayerCommand.ToggleAutoNext) },
            content = {
                Canvas(Modifier.fillMaxSize(0.9f)) {
                    val circleSize = size.height / 5
                    val circleCenter = Offset(
                        x = abs(size.width * animationState.value - circleSize),
                        y = center.y
                    )

                    drawRoundRect(
                        color = surface,
                        size = Size(size.width, 2 * circleSize),
                        topLeft = Offset(
                            x = 0f,
                            y = size.width / 2 - circleSize,
                        ),
                        cornerRadius = CornerRadius(16.dp.toPx()),
                    )
                    drawCircle(
                        color = onSurface,
                        radius = circleSize,
                        center = circleCenter,
                    )
                    val triangleSize = 6
                    drawPath(
                        color = surface,
                        path = Path().apply {
                            moveTo(x = circleCenter.x + triangleSize *4/3, y = circleCenter.y)
                            lineTo(x = circleCenter.x - triangleSize, y = circleCenter.y - triangleSize)
                            lineTo(x = circleCenter.x - triangleSize, y = circleCenter.y + triangleSize)
                            close()
                        }
                    )
                }
            },
        )
        CoolRippleIconButton(
            onClick = { onCommand(PlayerCommand.ToggleSubtitles) },
            content = { if (isSubtitles) SubtitlesFill() else Subtitles() },
        )
        CoolRippleIconButton(
            onClick = { onCommand(PlayerCommand.OpenBottomSheet.OpenSettings) },
            content = { Settings() },
        )

        after()
    }
}


@Preview
@Composable
private fun TopRightLinePreview() {
    TopRightLine(
        isSubtitles = false,
        isAutoNext = true,
        onCommand = {},
    )
}