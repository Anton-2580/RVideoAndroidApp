package common.impl.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun getNewPaddingValues(
    innerPadding: PaddingValues,
    horizontalPadding: Dp = 0.dp,
    verticalPadding: Dp = 0.dp,
) = listOf(
    innerPadding.calculateTopPadding() + verticalPadding,
    innerPadding.calculateBottomPadding() + verticalPadding,
    innerPadding.calculateLeftPadding(LocalLayoutDirection.current) + horizontalPadding,
    innerPadding.calculateRightPadding(LocalLayoutDirection.current) + horizontalPadding,
)