package core.player.impl.presentation.videoController

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import common.ui.ripple.getAnimationSpec
import core.player.api.domain.PlayerCommand
import core.player.impl.presentation.states.PlayerUiState
import kotlinx.coroutines.launch


@Composable
internal fun BaseVideoPlayerController(
    state: State<PlayerUiState>,
    size: State<DpSize>,
    onCommand: (PlayerCommand) -> Unit,
    modifier: Modifier = Modifier,
    onHoldSpeed: (() -> Unit)? = null,
    onReleaseHold: (() -> Unit)? = null,
    onTap: (() -> Unit) = { onCommand(PlayerCommand.ToggleVisible) },
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit = {},
    seekForwardBackward: Int = 10,
    centerRowControllers: @Composable RowScope.() -> Unit = {},
    bottomControllers: @Composable BoxScope.() -> Unit = {},
    topControllers: @Composable RowScope.() -> Unit = {},
    topControllersModifier: Modifier = Modifier,
    centerRowControllersModifier: Modifier = Modifier,
    seekingForward: MutableIntState? = mutableIntStateOf(0),
    seekingBackward: MutableIntState? = mutableIntStateOf(0),
    indent: Dp = 16.dp
) {
    val animationState = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    var prevSpeed = remember { 1f }
    val onHoldSpeed = remember { onHoldSpeed ?: {
        prevSpeed = state.value.speed
        onCommand(PlayerCommand.VisibleOff)
        onCommand(PlayerCommand.Speed(2f))
    } }
    val onReleaseHold = remember { onReleaseHold ?: {
        onCommand(PlayerCommand.Speed(prevSpeed))
    } }

    Box(
        modifier = modifier
            .size(size.value)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        onDrag(dragAmount)
                    },
                    onDragEnd = onDragEnd,
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onTap() },
                    onPress = {
                        tryAwaitRelease()
                        onReleaseHold()
                    },
                    onLongPress = {
                        onHoldSpeed()
                    },
                    onDoubleTap = { offset ->
                        if (seekingForward !== null && offset.x > this.size.width / 2) {
                            onCommand(PlayerCommand.SeekForward)
                            seekingForward.intValue += seekForwardBackward
                        } else if (seekingBackward !== null) {
                            onCommand(PlayerCommand.SeekBackward)
                            seekingBackward.intValue += seekForwardBackward
                        } else {
                            return@detectTapGestures
                        }

                        scope.launch {
                            animationState.snapTo(0f)
                            animationState.animateTo(
                                targetValue = 1f,
                                animationSpec = getAnimationSpec(),
                            )
                        }
                    },
                )
            }
    ) {
        AnimatedVisibility(
            visible = state.value.visible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(indent)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    content = topControllers,
                    modifier = topControllersModifier
                        .fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    content = centerRowControllers,
                    modifier = centerRowControllersModifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )

                bottomControllers()
            }
        }
    }
}