package common.ui.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toIntSize
import androidx.compose.ui.unit.toSize


@Composable
fun Modifier.shimmerLoading(
    colors: List<Color>,
    durationMillis: Int = 3000,
): Modifier {
    val transition = rememberInfiniteTransition()
    var size by remember { mutableStateOf(IntSize.Zero) }

    val translateAnimation by transition.animateFloat(
        initialValue = -size.width.toFloat(),
        targetValue = size.width.toFloat() + 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    return this.then(
        Modifier.drawWithCache {
            onDrawWithContent {
                size = this.size.toIntSize()

                val brush = Brush.linearGradient(
                    colors = colors,
                    start = Offset(x = translateAnimation, y = translateAnimation),
                    end = Offset(x = translateAnimation + size.width / 2, y = translateAnimation + size.width / 4)
                )

                drawRect(
                    brush = brush,
                    size = size.toSize()
                )
            }
        }
    )
}


@Composable
fun Modifier.shimmerLoading(
    color: Color,
    shimmerColor: Color,
    durationMillis: Int = 3000,
): Modifier = this.shimmerLoading(
    colors = listOf(
        color,
        shimmerColor,
        color,
    ),
    durationMillis = durationMillis,
)