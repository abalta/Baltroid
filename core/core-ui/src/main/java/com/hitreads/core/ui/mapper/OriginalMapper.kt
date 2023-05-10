package com.hitreads.core.ui.mapper

import com.hitreads.core.domain.model.AuthorModel
import com.hitreads.core.domain.model.OriginalModel
import com.hitreads.core.domain.model.UserDataModel
import com.hitreads.core.model.Author
import com.hitreads.core.model.Original
import com.hitreads.core.model.UserData

fun OriginalModel.asOriginal() = Original(
    author = author.asAuthor(),
    banner = banner,
    cover = cover,
    description = description,
    id = id,
    isActual = isActual,
    isLocked = isLocked,
    likeCount = likeCount,
    `package` = `package`.orEmpty(),
    sort = sort,
    status = status,
    title = title,
    type = type,
    userData = userData.asUserData()
)

fun AuthorModel.asAuthor() = Author(
    id, name
)

fun UserDataModel.asUserData() = UserData(isLike, isPurchase)