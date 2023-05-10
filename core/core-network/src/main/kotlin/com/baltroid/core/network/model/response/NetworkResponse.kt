package com.baltroid.core.network.model.response

import com.baltroid.core.network.model.originals.NetworkOriginal
import com.baltroid.core.network.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OriginalResponseDto(
    @SerialName(Constants.Fields.DATA_COUNT)
    val dataCount: Int,

    @SerialName(Constants.Fields.ORIGINALS)
    val originals: List<NetworkOriginal>,

    @SerialName(Constants.Fields.ACTIVE_PAGE)
    val totalPages: Int,

    @SerialName(Constants.Fields.TOTAL_PAGE)
    val totalResults: Int,
)