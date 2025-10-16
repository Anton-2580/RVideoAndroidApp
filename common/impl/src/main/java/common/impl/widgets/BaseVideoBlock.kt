package common.impl.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun BaseVideoBlock(
    modifier: Modifier = Modifier,
    videoBlock: @Composable (ColumnScope.() -> Unit),
    titleBlock: @Composable (ColumnScope.() -> Unit),
    channelIconBlock: @Composable (RowScope.() -> Unit),
    channelTitleBlock: @Composable (ColumnScope.() -> Unit),
    infoBlock: @Composable (ColumnScope.() -> Unit),
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        videoBlock()

        titleBlock()

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            channelIconBlock()

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier
                    .weight(6f)
            ) {
                channelTitleBlock()

                infoBlock()
            }
        }
    }
}