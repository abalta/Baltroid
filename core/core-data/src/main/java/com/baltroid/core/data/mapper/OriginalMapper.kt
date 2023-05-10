package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.author.NetworkAuthor
import com.baltroid.core.network.model.originals.NetworkOriginal
import com.baltroid.core.network.model.user.NetworkUserData
import com.hitreads.core.domain.model.AuthorModel
import com.hitreads.core.domain.model.OriginalModel
import com.hitreads.core.domain.model.UserDataModel

internal fun NetworkOriginal.asOriginalModel() = OriginalModel(
    type = type,
    id = id,
    title = title,
    description = description,
    cover = cover,
    banner = banner,
    author = author.asAuthorModel(),
    isLocked = isLocked,
    isActual = isActual,
    status = status,
    likeCount = likeCount,
    sort = sort,
    `package` = `package`,
    userData = userData.asUserDataModel()
)

internal fun NetworkAuthor.asAuthorModel() = AuthorModel(
    id = id,
    name = name
)

internal fun NetworkUserData.asUserDataModel() = UserDataModel(
    isLike = isLike,
    isPurchase = isPurchase
)

