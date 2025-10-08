package com.example.impl.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp


@Composable
fun getNewPaddingValues(
    innerPadding: PaddingValues,
    horizontalPadding: Dp,
) = PaddingValues(
    top = innerPadding.calculateTopPadding(),
    bottom = innerPadding.calculateBottomPadding(),
    start = innerPadding.calculateLeftPadding(LocalLayoutDirection.current) + horizontalPadding,
    end = innerPadding.calculateRightPadding(LocalLayoutDirection.current) + horizontalPadding,
)