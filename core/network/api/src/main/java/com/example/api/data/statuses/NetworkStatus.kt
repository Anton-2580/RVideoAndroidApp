package com.example.api.data.statuses

import com.example.api.events.Status
import kotlin.reflect.KClass


sealed class NetworkStatus(
    open val title: String,
    open val message: String,
    open val code: Short,
): Status {
    operator fun invoke() = message
    companion object {
        val VALUES: List<KClass<out NetworkStatus>> by lazy {
            getAllSubclasses(NetworkStatus::class)
        }
        val INSTANCES: List<NetworkStatus> by lazy {
            VALUES.flatMap {
                val inst = it.objectInstance
                if (inst !== null) listOf(inst)
                else listOf()
            }
        }

        private fun getAllSubclasses(
            klass: KClass<out NetworkStatus>
        ): List<KClass<out NetworkStatus>> {
            return klass.sealedSubclasses.flatMap { subclass ->
                if (subclass.isSealed) {
                    getAllSubclasses(subclass)
                } else {
                    listOf(subclass)
                }
            }
        }
        fun getInstance(item: KClass<out NetworkStatus>) = item.objectInstance
    }

    data object Unknown: NetworkStatus("Unknown", "Unknown error", 0)
    data object NoInternet: NetworkStatus("No Internet", "No Internet", 1)
    data object Serialization: NetworkStatus("Serialization", "Serialization Internet", 2)
}