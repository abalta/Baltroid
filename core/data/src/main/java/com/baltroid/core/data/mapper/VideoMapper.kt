package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.VideoDto
import com.mobven.domain.model.VideoModel

fun VideoDto.asVideoModel() = VideoModel(
    id = id,
    status = status,
    duration = duration,
    url = url
)