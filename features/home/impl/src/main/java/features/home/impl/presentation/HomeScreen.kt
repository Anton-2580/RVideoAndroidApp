package features.home.impl.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import common.api.domain.events.BaseEvents
import common.api.domain.events.Status
import common.impl.presentation.utils.formatTimeLocalized
import common.impl.presentation.utils.getNewPaddingValues
import common.impl.presentation.widgets.LoadedSelectItem
import common.impl.presentation.widgets.LoadingVideoBlock
import common.impl.presentation.widgets.SelectItem
import common.impl.presentation.widgets.VideoBlock
import common.ui.ripple.CoolRippleIconButton
import common.ui.theme.Bell
import common.ui.theme.DefaultUser
import common.ui.theme.FullLogo
import common.ui.theme.Gray4F
import common.ui.theme.Search
import core.navigation.api.domain.Navigator
import core.navigation.impl.domain.ContentDestinationsSerializable
import core.network.impl.models.VideoWithChannelSerializable
import features.home.api.domain.ContentFilter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import common.api.R as CommonR


@Composable
fun HomeScreen(
    padding: PaddingValues,
    onVideoClick: (videoWithChannel: VideoWithChannelSerializable) -> Unit,
    navigator: Navigator,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val videosEvents: MutableState<Status> = remember { mutableStateOf(value =
        if (viewModel.videos.value.isNotEmpty()) BaseEvents.Successful
        else BaseEvents.Loading
    ) }
    LaunchedEffect(Unit) {
        if (viewModel.videos.value.isNotEmpty()) return@LaunchedEffect

        viewModel.getFirstVideos()
        viewModel.eventFlow[viewModel.videoTag]?.collectLatest {
            delay(100)
            videosEvents.value = it
        }
    }
    val density = LocalDensity.current
    val maxWidth = with(density) { LocalConfiguration.current.screenWidthDp.toDp() }

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
                    LoadedSelectItem(
                        modifier = Modifier
                            .defaultMinSize(0.25f * maxWidth)
                    )
                }
                is BaseEvents.Successful -> items(
                    items = ContentFilter.valuesSealed.getInstances(),
                    key = { it.resId },
                ) {
                    if (it.isPlurals) {
                        // TODO
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
                state = viewModel.listState,
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
                                val photo = video.channel.photo
                                if (photo === null) { DefaultUser() }
                                else {
                                    Image(
                                        painter = rememberAsyncImagePainter(photo),
                                        contentDescription = "Channel Profile",
                                    )
                                }
                            },
                            infoBlock = {
                                Text(buildAnnotatedString {
                                    val style = SpanStyle(
                                        color = Gray4F,
                                    )

                                    withStyle(style = style) {
                                        append(pluralStringResource(
                                            CommonR.plurals.browsing,
                                            video.browsing,
                                            video.browsing,
                                        ))
                                        append(" ")
                                    }

                                    withStyle(style = style) {
                                        append(formatTimeLocalized(video.datetime))
                                        append(" ")
                                        append(stringResource(CommonR.string.ago))
                                    }
                                })
                            },
                            videoBlock = {
                                val photo = video.photo
                                val videoContentDescription = "Video Preview"
                                if (photo === null) {
                                    viewModel.loadPosterFromVideo(video.video)
                                    viewModel.videoPosters.value.get(video.video)?.let {
                                        Image(
                                            bitmap = it.asImageBitmap(),
                                            contentDescription = videoContentDescription,
                                        )
                                    }
                                } else {
                                    val image = viewModel.relatedVideoImages.value.get(photo).let {
                                        if (it === null) {
                                            val painter = rememberAsyncImagePainter(photo)
                                            viewModel.relatedVideoImages.value.put(photo, painter)
                                            painter
                                        } else it
                                    }
                                    Image(
                                        painter = image,
                                        contentDescription = videoContentDescription,
                                    )
                                }
                            },
                            onVideoClick = {
                                onVideoClick(video)
                            },
                            onChannelClick = {
                                navigator.navigate(
                                    ContentDestinationsSerializable.ChannelPage(video.channel.id)
                                )
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