package com.baltroid.core.network.model.episode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InteractiveNetworkBundleAsset(
    @SerialName("type")
    val type: String?,
    @SerialName("type_id")
    val typeId: Int?,
    @SerialName("path")
    val path: String?,
    @SerialName("is_active")
    val isActive: Boolean?
)
