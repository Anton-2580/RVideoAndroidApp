package core.network.impl.models

import common.api.domain.Model
import common.api.domain.models.Notification
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NotificationSerializable(
    val datetime: Double,
    val id: Int,
    @SerialName("is_read") val isRead: Boolean,
    val message: String,
): Model


fun NotificationSerializable.toDomain() = Notification(
    datetime = datetime,
    id = id,
    isRead = isRead,
    message = message,
)
fun Notification.toSerializable() = NotificationSerializable(
    datetime = datetime,
    id = id,
    isRead = isRead,
    message = message,
)
