package com.baltroid.core.network.model.originals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTag(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val name: String,
    @SerialName("icon")
    val icon: String
)