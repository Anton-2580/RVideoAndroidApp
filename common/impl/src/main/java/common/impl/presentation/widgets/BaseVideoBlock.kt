package common.impl.presentation.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun BaseVideoBlock(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    videoBlock: @Composable (ColumnScope.() -> Unit),
    titleBlock: @Composable (ColumnScope.() -> Unit),
    channelIconBlock: @Composable (RowScope.() -> Unit),
    channelTitleBlock: @Composable (RowScope.() -> Unit),
    infoBlock: @Composable (ColumnScope.() -> Unit),
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
    ) {
        videoBlock()

        titleBlock()

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            channelIconBlock()

            channelTitleBlock()
        }
        infoBlock()
    }
}