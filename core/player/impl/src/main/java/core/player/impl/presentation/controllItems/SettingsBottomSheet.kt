package core.player.impl.presentation.controllItems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import common.impl.presentation.ScaffoldState
import common.ui.ripple.CoolRippleButton
import common.ui.theme.TransparentButtonColors
import core.player.api.domain.PlayerCommand
import core.player.impl.R
import core.player.impl.presentation.states.PlayerUiState
import core.player.impl.ui.PlaySpeed
import core.player.impl.ui.Quality
import core.player.impl.ui.Subtitles
import core.player.impl.ui.SubtitlesFill


@Composable
fun ColumnScope.SettingsBottomSheet(
    playerUiState: State<PlayerUiState>,
    onCommand: (PlayerCommand) -> Unit,
    scaffoldState: MutableState<ScaffoldState>,
) {
    val state = playerUiState.value

    BottomSheetItem(
        onClick = { scaffoldState.value = scaffoldState.value.copy(
            sheetContent = {
                SettingsBottomSheetItem(
                    majorText = playerUiState.value.quality?.height?.toString(),
                    firstItem = {
                        Item(
                            onClick = { onCommand(PlayerCommand.EnableAutoQuality) },
                            text = stringResource(R.string.auto),
                        )
                    },
                    items = playerUiState.value.allQuality,
                    item = {
                        Item(
                            onClick = { onCommand(PlayerCommand.Quality(it)) },
                            text = it.height.toString(),
                        )
                    },
                )
            },
        ) },
        icon = Quality,
        majorText = stringResource(R.string.quality),
        extraText = state.quality?.height?.let {
            val height = it.toString() + "p"

            if (!state.isAutoQuality) height
            else stringResource(R.string.quality_auto, height)
        } ?: "",
    )

    BottomSheetItem(
        onClick = { scaffoldState.value = scaffoldState.value.copy(
            sheetContent = {
                SettingsBottomSheetItem(
                    majorText = playerUiState.value.speed.toString(),
                    items = List(8) { (it + 1) * 0.25f },
                    item = {
                        Item(
                            onClick = { onCommand(PlayerCommand.Speed(it)) },
                            text =
                                (if (it == it.toInt().toFloat()) it.toInt().toString()
                                else it.toString()) + "x",
                        )
                    },
                )
            },
        ) },
        icon = PlaySpeed,
        majorText = stringResource(R.string.playback_speed),
        extraText = state.speed.toString() + "x",
    )

    BottomSheetItem(
        onClick = {  },
        icon = if (state.subtitlesEnabled) SubtitlesFill else Subtitles,
        majorText = stringResource(R.string.subtitles),
    )
}


@Composable
private fun <T> SettingsBottomSheetItem(
    items: List<T>,
    item: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    firstItem: @Composable () -> Unit = {},
    majorText: String? = null,
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth()
    ) {
        item { SettingsBottomSheetItemHeader(
            text = stringResource(R.string.current_quality),
            majorText = majorText,
        ) }
        item {
            firstItem()
        }
        items(items) {
            item(it)
        }
    }
}
@Composable
private fun SettingsBottomSheetItemHeader(
    text: String,
    modifier: Modifier = Modifier,
    majorText: String? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Text(text)

        majorText?.let {
            Text(
                text = it,
                color = Color.Gray,
            )
        }
    }
}

@Composable
private fun Item(
    onClick: () -> Unit,
    text: String,
) {
    CoolRippleButton(
        onClick = onClick,
        colors = TransparentButtonColors,
        modifier = Modifier
            .fillMaxWidth(),
        buttonModifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text)
        }
    }
}