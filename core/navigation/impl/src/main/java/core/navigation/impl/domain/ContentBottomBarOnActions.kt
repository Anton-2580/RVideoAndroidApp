package core.navigation.impl.domain

import androidx.compose.runtime.Immutable


@Immutable
data class ContentBottomBarOnActions(
    val onHomeAction: () -> Unit,
    val onShortsAction: () -> Unit,
    val onSubscribeAction: () -> Unit,
    val onProfileAction: () -> Unit,
)