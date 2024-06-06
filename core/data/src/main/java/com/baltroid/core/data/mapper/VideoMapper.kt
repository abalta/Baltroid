package com.baltroid.core.data.mapper

import androidx.core.text.HtmlCompat
import com.baltroid.core.network.model.VideoDto
import com.mobven.domain.model.VideoModel

fun VideoDto.asVideoModel() = VideoModel(
    id = id,
    status = status,
    duration = duration,
    url = url/*HtmlCompat.fromHtml(url.orEmpty(), HtmlCompat.FROM_HTML_MODE_COMPACT).toString()*/
)