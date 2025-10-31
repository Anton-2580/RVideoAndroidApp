package common.ui.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val TertiaryButtonColors: ButtonColors
    @Composable
    get() = ButtonColors(
        contentColor = MaterialTheme.colorScheme.onTertiary,
        containerColor = MaterialTheme.colorScheme.tertiary,
        disabledContentColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.5f),
        disabledContainerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)
)

val TransparentButtonColors: ButtonColors
    @Composable
    get() = ButtonColors(
        Color.Transparent,
        MaterialTheme.colorScheme.onSurface,
        Color.Transparent,
        MaterialTheme.colorScheme.onSurface.copy(0.5f),
    )