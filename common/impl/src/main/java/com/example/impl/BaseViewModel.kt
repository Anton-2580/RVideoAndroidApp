package com.example.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.events.BaseEvents
import com.example.api.events.Status
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KClass


abstract class BaseViewModel: ViewModel() {
    protected val closeEventFlow = MutableSharedFlow<Status>()
    val eventFlow: SharedFlow<Status> = closeEventFlow

    protected fun workWithData(
        errorCondition: (e: Throwable) -> Boolean,
        content: suspend () -> Unit,
    ) {
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
        error: KClass<T> = T::class,
        noinline content: suspend () -> Unit,
    ) {
        workWithData(errorCondition = { e -> error.isInstance(e) }, content = content)
    }
}