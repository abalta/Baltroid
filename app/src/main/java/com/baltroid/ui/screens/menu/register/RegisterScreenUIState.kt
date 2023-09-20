package com.baltroid.ui.screens.menu.register

import androidx.annotation.StringRes

data class RegisterScreenUIState(
    val name: InputFieldState = InputFieldState(),
    val username: InputFieldState = InputFieldState(),
    val email: InputFieldState = InputFieldState(),
    val password: InputFieldState = InputFieldState(),
    val birthdate: InputFieldState = InputFieldState(),
    //val passwordConfirm: InputFieldState = InputFieldState(),
    val userAgreement: CheckBoxState = CheckBoxState(),
    val cookiePolicy: CheckBoxState = CheckBoxState(),
    @StringRes val errorMsg: Int? = null,
    val isSuccess: Boolean? = null
)

data class InputFieldState(
    val fieldValue: String = "",
    @StringRes val errorMsg: Int? = null
)

data class CheckBoxState(
    val isChecked: Boolean = false,
    @StringRes val errorMsg: Int? = null
)