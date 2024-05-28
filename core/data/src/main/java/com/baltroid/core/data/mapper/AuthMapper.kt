package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.LoginResponseDto
import com.baltroid.core.network.model.ProfileEntity
import com.mobven.domain.model.LoginResponseModel
import com.mobven.domain.model.ProfileModel

fun LoginResponseDto.asLoginResponseModel() = LoginResponseModel(
    customerId = customerId ?: 0,
    token = token.orEmpty(),
    email = email.orEmpty()
)

fun ProfileEntity.asProfileModel() = ProfileModel(
    id = profile.id ?: 0,
    email = profile.email.orEmpty(),
    avatar = profile.avatar.orEmpty(),
    name = "${profile.firstName.orEmpty()} ${profile.lastName.orEmpty()}",
    phone = profile.phone ?: "Telefon numarası girebilirsiniz.",
    about = profile.about ?: "Hakkınızda bilgi girebilirsiniz."
)