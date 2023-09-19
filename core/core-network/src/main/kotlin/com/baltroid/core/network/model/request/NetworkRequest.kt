package com.baltroid.core.network.model.request

import com.baltroid.core.network.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateBookmarkDto(
    @SerialName(Constants.Fields.ORIGINAL_ID)
    val originalId: Int,

    @SerialName(Constants.Fields.EPISODE_ID)
    val originals: Int
)

@Serializable
data class RegisterRequestBody(
    @SerialName("name")
    val name: String,
    @SerialName("username")
    val username: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("device")
    val device: String,
    @SerialName("identifier")
    val identifier: String,
    @SerialName("user_agreement")
    val userAgreement: Boolean,
    @SerialName("cookie_policy")
    val cookiePolicy: Boolean,
    @SerialName("birth_date")
    val birthDate: String,
)