package common.ui.ripple

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.RippleConfiguration


internal fun getRippleConfiguration(): RippleConfiguration {
    val value = lowRipplePhoneValue()
    return RippleConfiguration(
        rippleAlpha = if (value != 0f) RippleAlpha(
            draggedAlpha = value,
            focusedAlpha = value,
            hoveredAlpha = value,
            pressedAlpha = value,
        ) else null,
    )
}