package common.impl.presentation.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import common.ui.ripple.CoolRippleButton
import common.ui.shimmer.shimmerLoading
import common.ui.theme.TertiaryButtonColors


private val shape = RoundedCornerShape(20)

@Composable
fun SelectItem(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onTertiary,
    buttonColor: ButtonColors = TertiaryButtonColors,
    onClick: () -> Unit = {}
) {
    CoolRippleButton(
        modifier = modifier,
        colors = buttonColor,
        shape = shape,
        onClick = onClick,
    ) {
        Text(text, color = color)
    }
}

@Composable
fun LoadedSelectItem(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.tertiary,
    shimmerColor: Color = MaterialTheme.colorScheme.tertiary.copy(1f, 0.5f, 0.5f, 0.5f),
) {
    Box(
        modifier = modifier
            .clip(shape)
            .width(90.dp)
            .height(40.dp)
            .shimmerLoading(color, shimmerColor)
    )
}


@Preview
@Composable
private fun SelectItemPreview() {
    Column {
        SelectItem(
            text = "Translation",
        )
        Spacer(Modifier.height(10.dp))
        LoadedSelectItem()
    }
}