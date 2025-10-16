package features.home.impl.presentation

import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ContentWithRow(
    vertical: PaddingValues,
    horizontal: PaddingValues,
    blockBefore: @Composable ColumnScope.() -> Unit,
    blockAfter: @Composable ColumnScope.() -> Unit,
    content: LazyListScope.() -> Unit,
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    verticalArrangementColumn: Arrangement.Vertical = Arrangement.spacedBy(10.dp),
    horizontalAlignmentColumn: Alignment.Horizontal = Alignment.Start,
    state: LazyListState = rememberLazyListState(),
    reverseLayout: Boolean = false,
    horizontalArrangementRow: Arrangement.Horizontal = Arrangement.spacedBy(10.dp),
    verticalAlignmentRow: Alignment.Vertical = Alignment.Top,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    overscrollEffect: OverscrollEffect? = rememberOverscrollEffect(),
) {
    val horizontalModifier = modifier
        .padding(horizontal)
    val verticalModifier = modifier
        .padding(vertical)

    Column(
        verticalArrangement = verticalArrangementColumn,
        horizontalAlignment = horizontalAlignmentColumn,
        modifier = verticalModifier
    ) {
        Column(
            verticalArrangement = verticalArrangementColumn,
            horizontalAlignment = horizontalAlignmentColumn,
            modifier = horizontalModifier
        ) {
            blockBefore()
        }

        LazyRow(
            horizontalArrangement = horizontalArrangementRow,
            contentPadding = horizontal,
            modifier = rowModifier,
            content = content,
            state = state,
            reverseLayout = reverseLayout,
            verticalAlignment  = verticalAlignmentRow,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            overscrollEffect = overscrollEffect,
        )

        Column(
            verticalArrangement = verticalArrangementColumn,
            horizontalAlignment = horizontalAlignmentColumn,
            modifier = horizontalModifier
        ) {
            blockAfter()
        }
    }
}