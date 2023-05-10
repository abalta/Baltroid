package com.hitreads.core.ui.mapper

import com.hitreads.core.domain.model.OriginalModel
import com.hitreads.core.model.Original

fun OriginalModel.asOriginal() = Original(
    author = author,
    banner = banner,
    cover = cover,
    description = description,
    id = id,
    isActual = isActual,
    isLocked = isLocked,
    likeCount = likeCount,
    `package` = packagex.orEmpty(),
    sort = sort,
    status = status,
    title = title,
    type = type,
    userData = userData
)