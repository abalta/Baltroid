package com.baltroid.ui

import androidx.annotation.StringRes

data class RegisterScreenUIState(
    val name: InputFieldState = InputFieldState(),
    val email: InputFieldState = InputFieldState(),
    val password: InputFieldState = InputFieldState(),
    val passwordConfirm: InputFieldState = InputFieldState(),
    val isPrivacyPolicyChecked: CheckBoxState = CheckBoxState(),
    val isCookiePolicyChecked: CheckBoxState = CheckBoxState(),
    @StringRes val errorMsg: Int? = null,
    val isSuccess: Boolean = false
)

data class InputFieldState(
    val fieldValue: String = "",
    @StringRes val errorMsg: Int? = null
)

data class CheckBoxState(
    val isChecked: Boolean = false,
    @StringRes val errorMsg: Int? = null
)
