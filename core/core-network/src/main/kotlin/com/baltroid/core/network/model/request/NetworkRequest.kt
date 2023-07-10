package com.baltroid.core.network.model.request

import com.baltroid.core.network.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateBookmarkDto(
    @SerialName(Constants.Fields.ORIGINAL_ID)
    val originalId: Int,

    @SerialName(Constants.Fields.EPISODE_ID)
    val originals: Int
)