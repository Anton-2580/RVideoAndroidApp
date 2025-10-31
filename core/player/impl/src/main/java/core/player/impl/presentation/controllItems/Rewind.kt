package core.player.impl.presentation.controllItems

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
internal fun Rewind(
    rewindValue: MutableIntState,
    modifier: Modifier = Modifier,
) {
    val animationState = remember { Animatable(0f) }
    val targetValue = remember { 40f }
    val strokeWidth = remember { 2f }
    val color = LocalContentColor.current

    LaunchedEffect(rewindValue.intValue) {
        if (rewindValue.intValue != 0) {
            animationState.snapTo(targetValue = 0f)
            animationState.animateTo(
                targetValue = targetValue,
                animationSpec = tween(
                    durationMillis = 400,
                ),
            )
        } else {
            delay(400)
            animationState.snapTo(targetValue = 0f)
        }
    }
    LaunchedEffect(animationState.isRunning) {
        if (!animationState.isRunning && rewindValue.intValue != 0) {
            rewindValue.intValue = 0
        }
    }

    Box(Modifier.size(20.dp)) {
        AnimatedVisibility(
            visible = rewindValue.intValue != 0,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Canvas(
                modifier = modifier
                    .fillMaxSize()
            ) {
                drawPoints(
                    points = listOf(
                        Offset(animationState.value, center.y - 10),
                        Offset(animationState.value + 10, center.y),
                        Offset(animationState.value, center.y + 10),
                        Offset(animationState.value + 10, center.y),
                    ),
                    pointMode = PointMode.Lines,
                    color = color,
                    strokeWidth = strokeWidth,
                )
            }
        }
    }
}