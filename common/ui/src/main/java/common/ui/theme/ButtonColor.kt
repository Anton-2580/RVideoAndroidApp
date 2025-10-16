package common.ui.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


val TertiaryButtonColors: ButtonColors
    @Composable
    get() = ButtonColors(
        contentColor = MaterialTheme.colorScheme.onTertiary,
        containerColor = MaterialTheme.colorScheme.tertiary,
        disabledContentColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.5f),
        disabledContainerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)
)