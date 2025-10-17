package common.api.domain.models

import common.api.domain.Model


data class Notification(
    val datetime: Double,
    val id: Int,
    val isRead: Boolean,
    val message: String
): Model