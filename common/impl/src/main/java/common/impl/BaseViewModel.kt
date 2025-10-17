package common.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.api.domain.events.BaseEvents
import common.api.domain.events.Status
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel: ViewModel() {
    protected val closeEventFlow: MutableMap<String, MutableSharedFlow<Status>> = mutableMapOf()
    val eventFlow: Map<String, SharedFlow<Status>> = closeEventFlow

    protected fun workWithData(
        tag: String,
        errorCondition: (e: Throwable) -> Boolean,
        content: suspend () -> Unit,
    ) {
        val closeEventFlow = closeEventFlow[tag]
        if (closeEventFlow === null) return

        viewModelScope.launch {
            try {
                closeEventFlow.emit(BaseEvents.Loading)
                content()
                closeEventFlow.emit(BaseEvents.Successful)
            } catch (e: Throwable) {
                if (!errorCondition(e)) {
                    throw e
                }

                closeEventFlow.emit(BaseEvents.Error(e.message.toString()))
            }
        }
    }

    protected inline fun <reified T: Throwable> workWithData(
        tag: String,
        noinline content: suspend () -> Unit,
    ) {
        val error = T::class
        workWithData(
            tag = tag,
            errorCondition = { e -> error.isInstance(e) },
            content = content
        )
    }

    protected fun createEventFlow(tag: String) =
        closeEventFlow.getOrPut(tag) { MutableSharedFlow(extraBufferCapacity = 1) }

    protected fun createEventFlows(
        tags: List<String>,
    ) {
        tags.forEach {
            createEventFlow(it)
        }
    }
}