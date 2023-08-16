package com.baltroid.core.network.model.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkView(
    @SerialName("status")
    val status: Boolean?,
    @SerialName("viewed_at")
    val viewedAt: String?
)