package common.ui.ripple

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun CoolRippleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    activeBorder: BorderStroke? = null,
    inactiveBorder: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    boundColor: Color = Color.Gray,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val interactionSource = remember { interactionSource ?: MutableInteractionSource() }

    CoolRippleBaseButton(
        modifier = modifier,
        buttonModifier = buttonModifier,
        activeBorder = activeBorder,
        inactiveBorder = inactiveBorder,
        boundColor = boundColor,
        interactionSource = interactionSource,
    ) { border, buttonModifier ->
        Button(
            onClick = onClick,
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border.value,
            contentPadding = contentPadding,
            interactionSource = interactionSource,
            content = content,
            modifier = buttonModifier,
        )
    }
}


@Composable
fun CoolRippleIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    activeBorder: BorderStroke? = null,
    inactiveBorder: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    boundColor: Color = Color.Gray,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    shape: Shape = IconButtonDefaults.standardShape,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { interactionSource ?: MutableInteractionSource() }

    CoolRippleBaseButton(
        modifier = modifier,
        buttonModifier = buttonModifier,
        activeBorder = activeBorder,
        inactiveBorder = inactiveBorder,
        interactionSource = interactionSource,
        boundColor = boundColor,
    ) { border, buttonModifier ->
        IconButton(
            onClick = onClick,
            enabled = enabled,
            colors = colors,
            interactionSource = interactionSource,
            shape = shape,
            content = content,
            modifier = buttonModifier
                .border(border.value, shape)
        )
    }
}


@Preview
@Composable
private fun CoolRippleButtonPreview() {
    Column {
        CoolRippleButton(
            onClick = {},
        ) {
            Text("Button Test")
        }
        CoolRippleIconButton(
            onClick = {}
        ) {
            Text("Icon", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}