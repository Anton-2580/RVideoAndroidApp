package common.ui.ripple

import android.os.Build


internal fun lowRipplePhoneValue(): Float {
    var value = 0f
    if (Build.VERSION.SDK_INT in Build.VERSION_CODES.S_V2..Build.VERSION_CODES.TIRAMISU) {
        value += 0.4f
    }

    return value
}