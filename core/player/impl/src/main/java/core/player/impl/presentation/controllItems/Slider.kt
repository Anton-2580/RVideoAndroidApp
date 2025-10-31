package core.player.impl.presentation.controllItems

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import common.ui.theme.Gray80
import common.ui.theme.Green


@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    trackHeight: Dp = 9.dp,
    thumbSize: Dp = 15.dp,
    activeTrackColor: Color = Green,
    inactiveTrackColor: Color = Gray80,
    thumbColor: Color = LocalContentColor.current,
    thumbBorderColor: Color? = null,
    cornerRadius: Dp? = null,
    valueRange: ClosedRange<Float> = 0f..1f,
) {
    val density = LocalDensity.current
    val trackHeightPx = with(density) { trackHeight.toPx() }
    val thumbRadiusPx = with(density) { thumbSize.toPx() / 2f }
    val cornerRadiusPx = cornerRadius?.let {
        with(density) { it.toPx() }
    } ?: (trackHeightPx / 2)

    val dragOffset = remember { mutableFloatStateOf(0f) }
    val animatedValue = animateFloatAsState(
        targetValue = value,
        label = "SliderValue"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(trackHeight)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        if (!event.changes.any { it.pressed }) break

                        event.changes.forEach { change ->
                            dragOffset.floatValue = change.position.x
                            val newValue = calculateValueFromOffset(
                                offset = dragOffset.floatValue,
                                size = size.toSize(),
                                valueRange = valueRange,
                            )
                            onValueChange(newValue)
                            change.consume()
                        }
                    }
                }
            }
    ) {
        val width = size.width
        val trackY = center.y - trackHeightPx / 2

        drawRoundRect(
            color = inactiveTrackColor,
            topLeft = Offset(width * animatedValue.value, trackY),
            size = Size(width * (1 - animatedValue.value), trackHeightPx),
            cornerRadius = CornerRadius(
                x = cornerRadiusPx,
                y = cornerRadiusPx,
            )
        )

        drawRoundRect(
            color = activeTrackColor,
            topLeft = Offset(0f, trackY),
            size = Size(width * animatedValue.value, trackHeightPx),
            cornerRadius = CornerRadius(
                x = cornerRadiusPx,
                y = cornerRadiusPx,
            )
        )

        drawCircle(
            color = thumbColor,
            radius = thumbRadiusPx,
            center = Offset(width * animatedValue.value, center.y)
        )

        if (thumbBorderColor != null) {
            drawCircle(
                color = thumbBorderColor,
                radius = thumbRadiusPx + 1f,
                center = Offset(width * animatedValue.value, center.y),
                style = Stroke(width = 2f)
            )
        }
    }
}


private fun calculateValueFromOffset(
    offset: Float,
    size: Size,
    valueRange: ClosedRange<Float>,
): Float {
    val progress = (offset / size.width).coerceIn(0f, 1f)

    return valueRange.start + progress * (valueRange.endInclusive - valueRange.start)
}