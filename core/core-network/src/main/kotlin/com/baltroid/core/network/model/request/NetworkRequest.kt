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
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("password_confirmation")
    val passwordConfirmation: String,
    @SerialName("account_type")
    val accountType: String,
    @SerialName("karma")
    val karma: String,
    @SerialName("device")
    val device: String,
    @SerialName("identifier")
    val identifier: String,
    @SerialName("user_agreement")
    val user_agreement: Boolean,
    @SerialName("privacy_policy")
    val privacy_policy: Boolean,
    @SerialName("clarification_text")
    val clarificationText: Boolean,
    @SerialName("accept_receive_marketing_mail")
    val acceptReceiveMarketingMail: Boolean,
    @SerialName("birth_date")
    val birthDate: String,
)