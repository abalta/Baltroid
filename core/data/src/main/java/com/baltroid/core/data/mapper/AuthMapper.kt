package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.LoginResponseDto
import com.baltroid.core.network.model.ProfileDto
import com.mobven.domain.model.LoginResponseModel
import com.mobven.domain.model.ProfileModel

fun LoginResponseDto.asLoginResponseModel() = LoginResponseModel(
    customerId = customerId ?: 0,
    token = token.orEmpty(),
    email = email.orEmpty()
)

fun ProfileDto.asProfileModel() = ProfileModel(
    id = id ?: 0,
    email = email.orEmpty(),
    avatar = avatar.orEmpty(),
    name = "$firstName $lastName",
    firstname = firstName.orEmpty(),
    lastname = lastName.orEmpty(),
    phone = phone.orEmpty(),
    about = about.orEmpty()
)