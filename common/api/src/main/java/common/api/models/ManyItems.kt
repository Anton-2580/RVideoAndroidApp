package common.api.models

import common.api.Model


data class ManyItems<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>,
): Model