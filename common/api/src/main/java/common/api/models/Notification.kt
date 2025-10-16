package common.api.models

import common.api.Model


data class Notification(
    val datetime: Double,
    val id: Int,
    val isRead: Boolean,
    val message: String
): Model