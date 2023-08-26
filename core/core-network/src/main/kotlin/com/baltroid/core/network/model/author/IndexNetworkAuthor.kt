package com.baltroid.core.network.model.author

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IndexNetworkAuthor(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?
)