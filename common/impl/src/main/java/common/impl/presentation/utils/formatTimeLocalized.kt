package common.impl.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import common.api.R
import java.time.Duration
import java.time.Instant


@Composable
fun formatTimeLocalized(
    timestamp: Double,
): String {
    val now = Instant.now()
    val then = Instant.ofEpochMilli((timestamp * 1000).toLong())
    val duration = Duration.between(then, now)

    return when {
        duration.toSeconds() < 60 -> pluralStringResource(
            R.plurals.seconds,
            duration.toSeconds().toInt(),
            duration.toSeconds().toInt(),
        )
        duration.toMinutes() < 60 -> pluralStringResource(
            R.plurals.minutes,
            duration.toMinutes().toInt(),
            duration.toMinutes().toInt(),
        )
        duration.toHours() < 24 -> pluralStringResource(
            R.plurals.hours,
            duration.toHours().toInt(),
            duration.toHours().toInt(),
        )
        duration.toDays() < 7 -> pluralStringResource(
            R.plurals.days,
            duration.toDays().toInt(),
            duration.toDays().toInt(),
        )
        duration.toDays() < 30 -> pluralStringResource(
            R.plurals.weeks,
            duration.toDays().toInt() / 7,
            duration.toDays().toInt() / 7,
        )
        duration.toDays() < 365.25 -> pluralStringResource(
            R.plurals.months,
            duration.toDays().toInt() / 30,
            duration.toDays().toInt() / 30,
        )
        else -> pluralStringResource(
            R.plurals.years,
            (duration.toDays() / 365.25).toInt(),
            (duration.toDays() / 365.25).toInt(),
        )
    }
}