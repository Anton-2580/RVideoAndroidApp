package common.impl.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicReference


@Composable
fun <T> Debounce(
    value: T,
    delayMillis: Long = 300L,
    onChange: (T) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val debounceJob = remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(value) {
        debounceJob.value?.cancel()
        debounceJob.value = coroutineScope.launch {
            delay(delayMillis)
            onChange(value)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            debounceJob.value?.cancel()
        }
    }
}

fun debounce(
    scope: CoroutineScope,
    delay: Long = 500,
    block: suspend () -> Unit,
) {
    val job = AtomicReference<Job?>(null)

    job.get()?.cancel()
    job.set(scope.launch {
        delay(delay)
        block()
    })
}