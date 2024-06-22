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
data class RegisterRequestDto(
    @SerialName(Constants.Fields.EMAIL)
    val email: String,
    @SerialName(Constants.Fields.PASSWORD)
    val password: String,
    @SerialName(Constants.Fields.PASSWORD_CONFIRMATION)
    val passwordConfirmation: String,
    @SerialName(Constants.Fields.FIRST_NAME)
    val firstName: String,
    @SerialName(Constants.Fields.LAST_NAME)
    val lastName: String,
    @SerialName(Constants.Fields.AGREEMENT)
    val agreement: Boolean
)

@Serializable
data class ForgotPasswordRequestDto(
    @SerialName(Constants.Fields.EMAIL)
    val email: String
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

@Serializable
data class ProfileDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("email")
    val email: String?,
    @SerialName("avatar")
    val avatar: String?,
    @SerialName("firstname")
    val firstName: String?,
    @SerialName("lastname")
    val lastName: String?,
    @SerialName("phone")
    val phone: String?,
    @SerialName("about_text")
    val about: String?
)