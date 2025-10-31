package core.player.impl.presentation.controllItems

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import common.ui.ripple.CoolRippleIconButton


@Composable
fun PlayPause(
    onClick: () -> Unit,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
) {
    val color = LocalContentColor.current
    val progress by animateFloatAsState(
        targetValue = if (isPlaying) 1f else 0f,
        animationSpec = tween(durationMillis = 100, easing = LinearEasing)
    )

    CoolRippleIconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Canvas(modifier = modifier.size(size)) {
            val halfHeight = this.size.height / 2
            val halfWidth = this.size.width * 3 / 8
            val space = 10

            val path = if (progress > 0f) {
                 Path().apply {
                    moveTo(x = center.x - halfWidth, y = center.y - halfHeight)
                    lineTo(x = center.x - halfWidth, y = center.y + halfHeight)
                    lineTo(x = center.x - space, y = center.y + halfHeight)
                    lineTo(x = center.x - space, y = center.y - halfHeight)
                    close()

                    moveTo(
                        x = center.x + halfWidth * progress - space * (1 - progress),
                        y = center.y - halfHeight,
                    )
                    lineTo(x = center.x + halfWidth, y = center.y)
                    lineTo(x = center.x + space, y = center.y)
                    lineTo(
                        x = center.x + space * progress - space * (1 - progress),
                        y = center.y - halfHeight
                    )
                    close()

                    moveTo(x = center.x + halfWidth, y = center.y)
                    lineTo(
                        x = center.x + halfWidth * progress - space * (1 - progress),
                        y = center.y + halfHeight,
                    )
                    lineTo(
                        x = center.x + space * progress - space * (1 - progress),
                        y = center.y + halfHeight,
                    )
                    lineTo(x = center.x + space, y = center.y)
                    close()
                }
            } else {
                Path().apply {
                    moveTo(x = center.x - halfWidth,     y = center.y - halfHeight)
                    lineTo(x = center.x - halfWidth,     y = center.y + halfHeight)
                    lineTo(x = center.x + halfWidth, y = center.y)
                    close()
                }
            }

            path.fillType = PathFillType.NonZero
            drawPath(
                path = path,
                color = color,
                style = Fill,
            )
        }
    }
}