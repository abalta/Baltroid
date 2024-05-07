package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.LoginResponseDto
import com.mobven.domain.model.LoginResponseModel

fun LoginResponseDto.asLoginResponseModel() = LoginResponseModel(
    customerId = customerId ?: 0,
    token = token.orEmpty(),
    email = email.orEmpty()
)