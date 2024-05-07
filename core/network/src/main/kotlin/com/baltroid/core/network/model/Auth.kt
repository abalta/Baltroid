package com.baltroid.core.network.model

import com.baltroid.core.network.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    @SerialName(Constants.Fields.EMAIL)
    val email: String,
    @SerialName(Constants.Fields.PASSWORD)
    val password: String
)

@Serializable
data class LoginResponseDto(
    @SerialName(Constants.Fields.TOKEN)
    val token: String?,
    @SerialName(Constants.Fields.CUSTOMER_ID)
    val customerId: Int?,
    @SerialName(Constants.Fields.EMAIL)
    val email: String?
)