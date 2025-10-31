package common.impl.presentation

import android.app.Activity
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable


@Stable
data class ScaffoldState(
    val activity: Activity,
    val padding: PaddingValues = PaddingValues.Companion.Zero,
    val bottomBar: @Composable () -> Unit = {},
    val sheetContent: (@Composable ColumnScope.() -> Unit)? = null,
    val onDismissRequest: () -> Unit = {},
)