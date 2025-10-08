package com.example.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.ui.R


val MiniLogo: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.full_mini_logo)

val FullLogo: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.full_logo)

val MiniLogo1: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.logo_1)

val Bell: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.bell)

val Search: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.search)
