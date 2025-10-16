package common.impl.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import common.ui.ripple.CoolRippleButton
import common.ui.ripple.CoolRippleIconButton
import common.ui.theme.HorizontalDotsMenu


@Composable
fun VideoBlock(
    modifier: Modifier = Modifier,
    videoBlock: @Composable (RowScope.() -> Unit),
    titleBlock: @Composable (RowScope.() -> Unit),
    channelIconBlock: @Composable (RowScope.() -> Unit),
    channelTitleBlock: @Composable (RowScope.() -> Unit),
    infoBlock: @Composable (RowScope.() -> Unit),
    onVideoClick: () -> Unit,
    onChannelClick: () -> Unit,
    onActionMenuClick: () -> Unit,
) {
    val buttonColors = ButtonColors(
        Color.Transparent,
        MaterialTheme.colorScheme.onSurface,
        Color.Transparent,
        MaterialTheme.colorScheme.onSurface.copy(0.5f),
    )

    BaseVideoBlock(
        modifier = modifier,
        videoBlock = {
            CoolRippleButton(
                onClick = onVideoClick,
                content = videoBlock,
                colors = buttonColors,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16/9f)
            )
        },
        titleBlock = {
            Row {
                CoolRippleButton(
                    onClick = onVideoClick,
                    content = titleBlock,
                    colors = buttonColors,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                CoolRippleIconButton(
                    onClick = onActionMenuClick,
                ) {
                    HorizontalDotsMenu()
                }
            }
        },
        channelIconBlock = {
            CoolRippleButton(
                onClick = onChannelClick,
                content = channelIconBlock,
                colors = buttonColors,
            )
        },
        channelTitleBlock = {
            CoolRippleButton(
                onClick = onChannelClick,
                content = channelTitleBlock,
                colors = buttonColors,
            )
        },
        infoBlock = {
            CoolRippleButton(
                onClick = onVideoClick,
                content = infoBlock,
                colors = buttonColors,
            )
        },
    )
}
