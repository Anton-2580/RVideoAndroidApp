package features.home.impl.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import common.api.events.BaseEvents
import common.api.events.Status
import common.impl.utils.getNewPaddingValues
import common.impl.widgets.LoadedSelectItem
import common.impl.widgets.LoadingVideoBlock
import common.impl.widgets.SelectItem
import common.impl.widgets.VideoBlock
import common.ui.ripple.CoolRippleIconButton
import common.ui.theme.Bell
import common.ui.theme.FullLogo
import common.ui.theme.Search
import features.home.api.domain.ContentFilter
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HomeScreen(
    padding: PaddingValues = PaddingValues.Zero,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val videosEvents: MutableState<Status> = remember { mutableStateOf(BaseEvents.Loading) }
    LaunchedEffect(Unit) {
        viewModel.eventFlow[viewModel.videoTag]?.collectLatest {
            videosEvents.value = it
        }
    }

    val newPadding = getNewPaddingValues(padding, horizontalPadding = 10.dp)
    val vertical = PaddingValues(
        top = newPadding[0],
        bottom = newPadding[1]
    )
    val horizontal = PaddingValues(
        start = newPadding[2],
        end = newPadding[3],
    )
    ContentWithRow(
        vertical = vertical,
        horizontal = horizontal,
        blockBefore = {
            FirstRow(
                onBellClick = {
                },
                onSearchClick = {
                },
                modifier = Modifier
                    .padding(horizontal)
            )
        },
        content = {
            when (videosEvents.value) {
                is BaseEvents.Loading -> items(count = 4) {
                    LoadedSelectItem()
                }
                is BaseEvents.Successful -> items(
                    items = ContentFilter.valuesSealed.getInstances(),
                    key = { it.resId },
                ) {
                    if (it.isPlurals) {
                    } else {
                        SelectItem(stringResource(it.resId))
                    }
                }
                else -> {
                }
            }
        },
        blockAfter = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                when (videosEvents.value) {
                    is BaseEvents.Loading -> items(3) {
                        LoadingVideoBlock()
                    }
                    is BaseEvents.Successful -> items(
                        items = viewModel.videos.value,
                        key = { it.id }
                    ) { video ->
                        VideoBlock(
                            titleBlock = { Text(video.title) },
                            channelTitleBlock = { Text(video.channel.name) },
                            channelIconBlock = {
                            },
                            videoBlock = {
                            },
                            infoBlock = {
                            },
                            onVideoClick = {
                            },
                            onChannelClick = {
                            },
                            onActionMenuClick = {
                            },
                        )
                    }
                    else -> {
                    }
                }
            }
        },
    )
}

@Composable
private fun FirstRow(
    onBellClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        FullLogo(modifier = Modifier.fillMaxWidth(0.4f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CoolRippleIconButton(
                onClick = onBellClick,
            ) { Bell(modifier = Modifier.height(20.dp)) }
            CoolRippleIconButton(
                onClick = onSearchClick,
            ) { Search(modifier = Modifier.height(20.dp)) }
        }
    }
}