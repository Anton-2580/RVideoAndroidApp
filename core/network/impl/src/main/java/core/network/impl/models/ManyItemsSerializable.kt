package core.network.impl.models

import common.api.domain.Model
import common.api.domain.models.ManyItems
import kotlinx.serialization.Serializable
import javax.annotation.concurrent.Immutable


@Immutable
@Serializable
data class ManyItemsSerializable<T: Model>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>,
): Model


fun <T: Model> ManyItemsSerializable<T>.toDomain() = ManyItems(
    count = count,
    next = next,
    previous = previous,
    results = results,
)
fun <T: Model> ManyItems<T>.toSerializable() = ManyItemsSerializable(
    count = count,
    next = next,
    previous = previous,
    results = results,
)