package com.baltroid.core.network.model.originals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTag(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("icon")
    val icon: String
)