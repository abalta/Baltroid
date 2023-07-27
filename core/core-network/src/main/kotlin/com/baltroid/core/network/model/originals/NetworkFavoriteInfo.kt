package com.baltroid.core.network.model.originals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NetworkFavoriteInfo(
    @SerialName("id")
    val id: Int?,
    @SerialName("user_id")
    val userId: Int?
)