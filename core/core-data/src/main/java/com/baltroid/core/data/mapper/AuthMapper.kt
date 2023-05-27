package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.response.LoginDto
import com.hitreads.core.domain.model.LoginModel

internal fun LoginDto.asLoginModel() = LoginModel(
    userId = userId, token = token, username = username, avatar = avatar, wallet = wallet
)