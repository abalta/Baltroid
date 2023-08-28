package com.baltroid.core.network.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IndexUserData(
    @SerialName("is_fav")
    val isFav: Boolean?,
    @SerialName("is_purchase")
    val isPurchase: Boolean?
)