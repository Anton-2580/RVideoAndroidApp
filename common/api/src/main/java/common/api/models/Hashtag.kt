package common.api.models

import common.api.Model


data class Hashtag(
    val hashtag: String,
    val id: Int,
    val video: Int,
): Model