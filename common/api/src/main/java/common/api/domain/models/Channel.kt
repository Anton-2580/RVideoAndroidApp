package common.api.domain.models

import common.api.domain.Model


data class Channel(
    val description: String,
    val id: Int,
    val isBlocked: Boolean,
    val name: String,
    val photo: String?,
    val subscribers: Int,
    val slug: String,
): Model