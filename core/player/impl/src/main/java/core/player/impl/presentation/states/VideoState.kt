package core.player.impl.presentation.states

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import common.api.domain.models.VideoWithChannel


@Stable
data class VideoState(
    val id: Int? = null,
    val slug: String? = null,
    val isPlaying: Boolean = true,

    val lazyItems: List<VideoWithChannel> = emptyList(),
    val bottomItem: @Composable LazyItemScope.(VideoWithChannel, VideoWithChannel?) -> Unit = { lazyItem, currentVideo -> },
)
