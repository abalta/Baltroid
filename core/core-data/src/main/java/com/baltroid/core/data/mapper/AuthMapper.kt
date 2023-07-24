package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.response.LoginDto
import com.baltroid.core.network.model.response.ProfileDto
import com.hitreads.core.domain.model.LoginModel
import com.hitreads.core.domain.model.ProfileModel

internal fun LoginDto.asLoginModel() = LoginModel(
    userId = userId,
    token = token,
    username = username,
    avatar = avatar,
    wallet = wallet,
    imgUrl = ""
)

internal fun ProfileDto.asProfileModel() = ProfileModel(
    userName = userName.orEmpty(),
    email = email.orEmpty(),
    karma = karma ?: 0,
    avatar = avatar.orEmpty(),
    is_beta = is_beta ?: false,
    gem = gem ?: 0
)