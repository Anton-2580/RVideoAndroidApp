package common.api.domain.models

import common.api.domain.Model


data class Hashtag(
    val hashtag: String,
    val id: Int,
    val video: Int,
): Model