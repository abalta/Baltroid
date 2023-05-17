package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.HitReadsResponse
import com.hitreads.core.domain.model.ResponseModel

internal fun HitReadsResponse<*>.asResponseModel() = ResponseModel(
    data = data,
    status = status,
    message = message
)