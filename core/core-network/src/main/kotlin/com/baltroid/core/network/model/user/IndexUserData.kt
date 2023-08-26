package com.baltroid.core.network.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IndexUserData(
    @SerialName("is_like")
    val isLike: Boolean?,
    @SerialName("is_purchase")
    val isPurchase: Boolean?
)