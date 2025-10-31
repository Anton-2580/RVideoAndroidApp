package common.impl.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import common.ui.ripple.CoolRippleButton
import common.ui.ripple.CoolRippleIconButton
import common.ui.theme.DefaultUser
import common.ui.theme.HorizontalDotsMenu
import common.ui.theme.TransparentButtonColors


@Composable
fun VideoBlock(
    modifier: Modifier = Modifier,
    videoBlock: @Composable (RowScope.() -> Unit),
    titleBlock: @Composable (() -> Unit),
    channelIconBlock: @Composable (() -> Unit),
    channelTitleBlock: @Composable (() -> Unit),
    infoBlock: @Composable (() -> Unit),
    onVideoClick: () -> Unit,
    onChannelClick: () -> Unit,
    onActionMenuClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    BaseVideoBlock(
        modifier = modifier
            .clip(RoundedCornerShape(5))
            .clickable(
                onClick = onVideoClick,
                role = Role.Button,
                indication = ripple(),
                interactionSource = interactionSource,
            ),
        videoBlock = {
            CoolRippleButton(
                onClick = onVideoClick,
                content = videoBlock,
                colors = TransparentButtonColors,
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(10),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
            )
        },
        titleBlock = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                Box(
                    content = { titleBlock() },
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                )
                CoolRippleIconButton(
                    onClick = onActionMenuClick,
                ) {
                    HorizontalDotsMenu(
                        modifier = Modifier
                            .fillMaxHeight(0.6f)
                            .aspectRatio(1f)
                    )
                }
            }
        },
        channelIconBlock = {
            CoolRippleIconButton(
                onClick = onChannelClick,
                content = channelIconBlock,
            )
        },
        channelTitleBlock = {
            Box(
                content = { channelTitleBlock() },
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onChannelClick
                    )
            )
        },
        infoBlock = {
            Box(
                content = { infoBlock() },
                modifier = Modifier
                    .padding(top = 5.dp, start = 5.dp, end = 5.dp)
                    .fillMaxWidth()
            )
        },
    )
}


@Preview
@Composable
private fun VideoBlockPreview() {
    VideoBlock(
        titleBlock = { Text(text = "VideoTitle ".repeat(3), color = Color.White) },
        channelTitleBlock = { Text(text = "ChannelName", color = Color.White) },
        channelIconBlock = { DefaultUser() },
        infoBlock = { Text(text = "100 views  20 days ago", color = Color.Gray) },
        videoBlock = {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
            )
        },
        onVideoClick = {},
        onChannelClick = {},
        onActionMenuClick = {},
    )
}