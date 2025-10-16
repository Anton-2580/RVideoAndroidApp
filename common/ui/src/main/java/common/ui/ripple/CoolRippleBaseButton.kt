package common.ui.ripple

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.launch


@Composable
internal fun CoolRippleBaseButton(
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    activeBorder: BorderStroke?,
    inactiveBorder: BorderStroke?,
    interactionSource: MutableInteractionSource,
    boundColor: Color,
    button: @Composable (State<BorderStroke>, Modifier) -> Unit,
) {
    val activeBorderColor = activeBorder?.brush?.toColor() ?: boundColor
    val activeBorderWidth = activeBorder?.width?.value ?: 1f
    val inactiveBorderColor = inactiveBorder?.brush?.toColor() ?: boundColor.copy(alpha = 0f)
    val inactiveBorderWidth = inactiveBorder?.width?.value ?: 0f

    val density = LocalDensity.current
    val scope = rememberCoroutineScope()
    val centerOffset = remember { mutableStateOf(Offset(0f, 0f)) }
    val size = remember { mutableStateOf(DpSize(200.dp, 200.dp)) }
    val animationState = remember { Animatable(0f) }


    val computedBorder = remember {
        derivedStateOf {
            BorderStroke(
                width = lerp(inactiveBorderWidth, activeBorderWidth, animationState.value).dp,
                color = lerp(inactiveBorderColor, activeBorderColor, animationState.value)
            )
        }
    }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> scope.launch {
                    centerOffset.value = interaction.pressPosition
                    animationState.animateTo(
                        targetValue = 1f,
                        animationSpec = getAnimationSpec()
                    )
                }
                is PressInteraction.Release, is PressInteraction.Cancel -> scope.launch {
                    animationState.animateTo(
                        targetValue = 0f,
                        animationSpec = getAnimationSpec()
                    )
                }
            }
        }
    }

    Box(
        modifier = modifier
            .size(size.value)
    ) {
        button(
            computedBorder,
            buttonModifier
                .onSizeChanged {
                    size.value = with(density) {
                        it.toSize().toDpSize()
                    }
                }
        )

        Canvas(
            modifier = Modifier
                .size(size.value)
        ) {
            val progress = animationState.value
            if (progress > 0f) {
                val radius = this.size.minDimension * (1 - progress)
                val alpha = (1f - progress).coerceIn(0f, 1f)
                drawCircle(
                    color = Color.White.copy(alpha = 0.3f * alpha),
                    radius = radius / 2,
                    center = centerOffset.value,
                    style = Stroke(width = 6.dp.toPx() * progress)
                )
            }
        }

    }
}

fun <T> getAnimationSpec(): TweenSpec<T> =
    TweenSpec(600, 0, FastOutSlowInEasing)

fun Brush.toColor(): Color? = when (this) {
    is SolidColor -> value
    else -> null
}