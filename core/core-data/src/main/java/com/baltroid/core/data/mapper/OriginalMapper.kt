package com.baltroid.core.data.mapper

import com.baltroid.core.database.model.common.Author
import com.baltroid.core.database.model.common.User
import com.baltroid.core.network.model.author.NetworkAuthor
import com.baltroid.core.network.model.originals.NetworkOriginal
import com.baltroid.core.network.model.user.NetworkUserData
import com.hitreads.core.domain.model.OriginalModel

internal fun NetworkOriginal.asOriginalModel() = OriginalModel(
    type = type,
    id = id,
    title = title,
    description = description,
    cover = cover,
    banner = banner,
    author = author?.asAuthorEntity(),
    isLocked = isLocked,
    isActual = isActual,
    status = status,
    likeCount = likeCount,
    sort = sort,
    packagex = packagex,
    userData = userData?.asUserEntity()
)

internal fun NetworkAuthor.asAuthorEntity() = Author(
    id = id,
    name = name
)

internal fun NetworkUserData.asUserEntity() = User(
    isLike = isLike,
    isPurchase = isPurchase
)

