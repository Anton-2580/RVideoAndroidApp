package core.player.impl.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import common.impl.presentation.ScaffoldState
import core.network.impl.models.toDomain
import core.player.api.domain.PlayerCommand
import core.player.impl.presentation.videoController.FullVideoPlayerController
import core.player.impl.presentation.videoController.MiniVideoPlayerController
import core.player.impl.presentation.videoController.SemiFullVideoPlayerController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt


@Composable
fun VideoPlayerView(
    scaffoldState: MutableState<ScaffoldState>,
    modifier: Modifier = Modifier,
    bottomMiniPlayerOffset: Dp = 0.dp,
    viewModel: VideoPlayerViewModel = hiltViewModel(),
) {
    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
        PrivateVideoPlayerView(
            scaffoldState = scaffoldState,
            semiBottom = {
                val thisVideo = viewModel.uiState.value.thisVideo.value

                LazyColumn {
                    item {
//                      TODO
                    }
                    items(
                        items = thisVideo.lazyItems,
                        key = { it.id },
                    ) {
                        thisVideo.bottomItem(this, it, viewModel.videoState.value?.toDomain())
                    }
                }
            },
            fullBottom = {
                val thisVideo = viewModel.uiState.value.thisVideo.value

                LazyRow {
                    item {
//                      TODO
                    }
                    items(
                        items = thisVideo.lazyItems,
                        key = { it.id },
                    ) {
                        thisVideo.bottomItem(this, it, viewModel.videoState.value?.toDomain())
                    }
                }
            },
            modifier = modifier,
            bottomMiniPlayerOffset = bottomMiniPlayerOffset,
            viewModel = viewModel,
        )
    }
}


@Composable
private fun PrivateVideoPlayerView(
    scaffoldState: MutableState<ScaffoldState>,
    semiBottom: @Composable () -> Unit,
    fullBottom: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    bottomMiniPlayerOffset: Dp = 0.dp,
    viewModel: VideoPlayerViewModel = hiltViewModel(),
) {
    viewModel.uiState.value.thisVideo.value.let { if (it.id === null && null === it.slug) return }
    LaunchedEffect(Unit) {
        viewModel.updateOpenBottomSheet(scaffoldState)
        viewModel.updateFullscreen(scaffoldState)
    }

    val dragAmount = remember { mutableStateOf(DpOffset(0.dp, 0.dp)) }
    val dragChanging = remember { mutableStateOf(false) }
    val size = remember { mutableStateOf(DpSize(0.dp, 0.dp)) }
    val density = LocalDensity.current

    val scope = rememberCoroutineScope()
    val animationState = remember { Animatable(1f) }
    val fullSizeAnimationState = remember { Animatable(0f) }
    ChangeSizeVersion(
        scope = scope,
        animationState = animationState,
        viewModel = viewModel,
    )

    val maxSize = with(density) { LocalWindowInfo.current.containerSize.toSize().toDpSize() }
    val offset = DpOffset(
        x = (dragAmount.value.x + maxSize.width  - size.value.width) * (1-animationState.value),
        y = (dragAmount.value.y + maxSize.height - size.value.height - bottomMiniPlayerOffset) * (1-animationState.value),
    )

    val boxFraction =
        if (viewModel.uiState.value.sizeVersion === PlayerCommand.ToggleSizeVersion.FullVersion) 1f
        else lerp(0.6f, 1f, animationState.value)

    Box {
        Box(
            modifier = modifier
                .offset(offset.x, offset.y)
                .fillMaxWidth(boxFraction)
                .clip(RoundedCornerShape(percent = abs(10 * (1 - animationState.value)).toInt()))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { size.value = with(density) { it.toSize().toDpSize() } }
                    .zIndex(1f)
            ) { StablePlayerView(viewModel.player.getPlayer()) }

            PlayerController(
                scope = scope,
                size = size,
                animationState = animationState,
                dragAmount = dragAmount,
                dragChanging = dragChanging,
                viewModel = viewModel,
                fullBottom = fullBottom,
                fullSizeAnimationState = fullSizeAnimationState,
                modifier = Modifier
                    .zIndex(2f)
            )
        }

        if (animationState.value != 0f) {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = animationState.value
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = size.value.height)
                ) {
                    semiBottom()
                }
            }
        }
    }
}

@Composable
fun StablePlayerView(player: ExoPlayer) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                this.player = player
                useController = false
                keepScreenOn = true
            }
        },
        onRelease = {
            it.player = null
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    )
}

@Composable
private fun ChangeSizeVersion(
    scope: CoroutineScope,
    animationState: Animatable<Float, AnimationVector1D>,
    viewModel: VideoPlayerViewModel,
) {
    LaunchedEffect(viewModel.uiState.value.sizeVersion) {
        scope.launch {
            when (viewModel.uiState.value.sizeVersion) {
                PlayerCommand.ToggleSizeVersion.FullVersion -> {
                    animationState.snapTo(1f)
                }

                PlayerCommand.ToggleSizeVersion.SemiFullVersion -> {
                    animationState.animateTo(1f)
                }

                PlayerCommand.ToggleSizeVersion.MiniVersion -> {
                    animationState.animateTo(0f)
                }
            }
        }
    }
}

@Composable
private fun BoxScope.PlayerController(
    scope: CoroutineScope,
    size: State<DpSize>,
    animationState: Animatable<Float, AnimationVector1D>,
    dragAmount: MutableState<DpOffset>,
    dragChanging: MutableState<Boolean>,
    viewModel: VideoPlayerViewModel,
    fullBottom: @Composable () -> Unit,
    fullSizeAnimationState: Animatable<Float, AnimationVector1D>,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val maxSize = with(density) {
        LocalConfiguration.current.screenWidthDp.toDp() to LocalConfiguration.current.screenHeightDp.toDp()
    }

    val drag = remember { object {
        var toTopOrBottom: Boolean? = null
        var newDragAmount = DpOffset(0.dp, 0.dp)

        suspend fun startDrag(
            offset: Offset,
            first: () -> Unit = {},
            block: suspend () -> Float
        ) {
            if (!dragChanging.value) {
                first()
                newDragAmount = DpOffset(0.dp, 0.dp)
                dragAmount.value = DpOffset(0.dp, 0.dp)
            }
            newDragAmount += with(density) { DpOffset(0.dp, offset.y.toDp()) }

            val scale = block()
            if (!dragChanging.value) {
                dragChanging.value = true

                toTopOrBottom =
                    if (scale < 0) true
                    else false
            }
        }

        suspend fun onDragEnd() {
            dragChanging.value = false
            toTopOrBottom = null
            dragAmount.value = DpOffset(0.dp, 0.dp)

            animationState.animateTo(animationState.value.roundToInt().toFloat())
            fullSizeAnimationState.animateTo(
                fullSizeAnimationState.value.roundToInt().toFloat()
            )

        }
    } }


    when (viewModel.uiState.value.sizeVersion) {
        PlayerCommand.ToggleSizeVersion.FullVersion -> {
            FullVideoPlayerController(
                uiState = viewModel.uiState,
                videoState = viewModel.videoState,
                size = size,
                seekForwardBackward = viewModel.playerController.seekForwardBackward,
                onCommand = { playerCommand -> viewModel.onPlayerCommand(playerCommand) },
                modifier = modifier,
                onDrag = { offset ->
                    scope.launch {
                        drag.startDrag(
                            offset = offset,
                        ) {
                            val scale = (drag.newDragAmount.y / maxSize.first)
                                .coerceIn(
                                    minimumValue = if (drag.toTopOrBottom == false) 0f else -1f,
                                    maximumValue = if (drag.toTopOrBottom == true) 0f else 1f,
                                )

                            if (drag.toTopOrBottom == false) {
                                if (fullSizeAnimationState.value > 0) {
                                    fullSizeAnimationState.snapTo(1 - scale)
                                } else {
                                    animationState.snapTo(1 + scale)
                                }
                            } else if (drag.toTopOrBottom == true) {
                                fullSizeAnimationState.snapTo(-scale)
                            }

                            if (animationState.value > 1.2f) {
                                viewModel.onPlayerCommand(PlayerCommand.ToggleSizeVersion.SemiFullVersion)
                                animationState.snapTo(0f)
                            }
                            scale
                        }
                    }
                },
                onDragEnd = { scope.launch { drag.onDragEnd() } },
            )

            AnimatedVisibility(
                visible = fullSizeAnimationState.value == 1f && !dragChanging.value,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = modifier
                    .zIndex(1f)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.4f))
            ) { }

            Box(
                modifier = Modifier
                    .zIndex(2f)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f * fullSizeAnimationState.value)
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.3f))
            ) {
                fullBottom()
            }
        }

        PlayerCommand.ToggleSizeVersion.SemiFullVersion -> {
            SemiFullVideoPlayerController(
                state = viewModel.uiState,
                size = size,
                seekForwardBackward = viewModel.playerController.seekForwardBackward,
                onCommand = { playerCommand -> viewModel.onPlayerCommand(playerCommand) },
                modifier = modifier,
                onDrag = { offset ->
                    scope.launch {
                        drag.startDrag(
                            offset = offset,
                            first = { viewModel.onPlayerCommand(PlayerCommand.VisibleOff) },
                        ) {
                            val scale = (drag.newDragAmount.y / (maxSize.second + size.value.height))
                                .coerceIn(
                                    minimumValue = if (drag.toTopOrBottom == false) 0f else -1f,
                                    maximumValue = if (drag.toTopOrBottom == true) 0f else 1f,
                                )
                            animationState.snapTo(1 - scale)

                            if (animationState.value > 1.1f) {
                                viewModel.onPlayerCommand(PlayerCommand.ToggleSizeVersion.FullVersion)
                                animationState.snapTo(0f)
                            }
                            scale
                        }
                    }
                },
                onDragEnd = {
                    scope.launch {
                        drag.onDragEnd()
                        if (animationState.value == 0f) {
                            viewModel.onPlayerCommand(PlayerCommand.ToggleSizeVersion.MiniVersion)
                        }
                    }
                },
            )
        }

        PlayerCommand.ToggleSizeVersion.MiniVersion -> {
            MiniVideoPlayerController(
                state = viewModel.uiState,
                size = size,
                onCommand = { playerCommand -> viewModel.onPlayerCommand(playerCommand) },
                modifier = modifier,
                onTap = {
                    scope.launch {
                        delay(200)
                        if (!dragChanging.value) {
                            viewModel.onPlayerCommand(PlayerCommand.ToggleSizeVersion.SemiFullVersion)
                        }
                    }
                },
                onDrag = {
                    if (!dragChanging.value) dragChanging.value = true

                    with(density) {
                        dragAmount.value += DpOffset(it.x.toDp(), it.y.toDp())
                    }
                },
                onDragEnd = {
                    dragChanging.value = false
                }
            )
        }
    }
}