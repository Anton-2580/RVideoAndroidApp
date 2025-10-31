package core.player.impl.presentation.controllItems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import common.ui.ripple.CoolRippleButton
import common.ui.theme.TransparentButtonColors


@Composable
fun BottomSheetItem(
    onClick: () -> Unit,
    icon: ImageVector,
    majorText: String,
    modifier: Modifier = Modifier,
    extraText: String = "",
) {
    CoolRippleButton(
        onClick = onClick,
        colors = TransparentButtonColors,
        modifier = modifier
            .fillMaxWidth(),
        buttonModifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon"
                )

                Text(text = majorText)
            }

            Text(
                text = extraText,
                color = Color.Gray,
            )
        }
    }
}