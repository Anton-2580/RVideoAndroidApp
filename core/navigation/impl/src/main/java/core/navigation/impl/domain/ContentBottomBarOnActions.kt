package core.navigation.impl.domain

data class ContentBottomBarOnActions(
    val onHomeAction: () -> Unit,
    val onShortsAction: () -> Unit,
    val onSubscribeAction: () -> Unit,
    val onProfileAction: () -> Unit,
)