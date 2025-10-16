package common.api

import kotlin.reflect.KClass


class ValuesSealed<T: Any>(
    val kClass: KClass<T>,
) {
    private fun getAllSubclasses(
        klass: KClass<out T>
    ): List<KClass<out T>> {
        return klass.sealedSubclasses.flatMap { subclass ->
            if (subclass.isSealed) {
                getAllSubclasses(subclass)
            } else {
                listOf(subclass)
            }
        }
    }
    fun getInstance(item: KClass<out T>) = item.objectInstance

    private var pValues: List<KClass<out T>> = listOf()
    fun getValues(): List<KClass<out T>> =
        pValues.ifEmpty {
            pValues = getAllSubclasses(kClass)
            pValues
        }
    private var pInstances: List<T> = listOf()
    fun getInstances(): List<T> =
        pInstances.ifEmpty {
            pInstances = getValues().flatMap {
                val inst = it.objectInstance
                if (inst !== null) listOf(inst)
                else listOf()
            }
            pInstances
        }
}