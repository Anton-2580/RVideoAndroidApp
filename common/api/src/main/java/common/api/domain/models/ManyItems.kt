package common.api.domain.models

import common.api.domain.Model


data class ManyItems<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>,
): Model