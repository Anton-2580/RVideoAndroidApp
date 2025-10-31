package common.impl.presentation.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import common.ui.shimmer.shimmerLoading


@Composable
fun LoadingVideoBlock(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    shimmerColor: Color = MaterialTheme.colorScheme.surface.copy(1f, 0.5f, 0.5f, 0.5f),
) {
    val padding = 4.dp
    BaseVideoBlock(
        videoBlock = {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = padding)
                    .clip(RoundedCornerShape(10))
                    .aspectRatio(16 / 9f)
                    .shimmerLoading(color, shimmerColor)
            )
        },
        titleBlock = {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = padding)
                    .clip(RoundedCornerShape(20))
                    .aspectRatio(20f)
                    .shimmerLoading(color, shimmerColor)
            )
        },
        channelIconBlock = {
            Box(
                modifier = modifier
                    .weight(1f)
                    .padding(vertical = padding)
                    .clip(CircleShape)
                    .aspectRatio(1f)
                    .shimmerLoading(color, shimmerColor)
            )
        },
        channelTitleBlock = {
            Box(
                modifier = modifier
                    .padding(padding)
                    .clip(RoundedCornerShape(30))
                    .height(20.dp)
                    .fillMaxWidth(0.9f)
                    .shimmerLoading(color, shimmerColor)
            )
        },
        infoBlock = {
            Box(
                modifier = modifier
                    .padding(top = padding)
                    .clip(RoundedCornerShape(30))
                    .height(20.dp)
                    .fillMaxWidth(0.7f)
                    .shimmerLoading(color, shimmerColor)
            )
        },
    )
}


@Preview
@Composable
private fun VideoBlockPreview() {
    LoadingVideoBlock()
}