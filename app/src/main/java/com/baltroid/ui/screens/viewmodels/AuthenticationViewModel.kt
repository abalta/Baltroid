package com.baltroid.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.apps.R
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.usecase.ProfileUseCase
import com.hitreads.core.domain.usecase.RegisterUseCase
import com.hitreads.core.ui.mapper.asProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileUIState())
    val profileState = _profileState.asStateFlow()

    private val _uiStateRegister = MutableStateFlow(RegisterScreenUIState())
    val uiStateRegister = _uiStateRegister.asStateFlow()

    fun getProfile() = viewModelScope.launch {
        profileUseCase().handle {
            onSuccess { profileModel ->
                _profileState.update {
                    it.copy(profile = profileModel.asProfile())
                }
            }
        }
    }

    fun updateNameField(value: String) {
        _uiStateRegister.update { it.copy(name = it.name.copy(fieldValue = value)) }
    }

    fun updateEmailField(value: String) {
        _uiStateRegister.update { it.copy(email = it.email.copy(fieldValue = value)) }
    }

    fun updatePasswordField(value: String) {
        _uiStateRegister.update { it.copy(password = it.password.copy(fieldValue = value)) }
    }

    fun updatePasswordConfirmField(value: String) {
        _uiStateRegister.update { it.copy(passwordConfirm = it.passwordConfirm.copy(fieldValue = value)) }
    }

    fun clearSuccess() {
        _uiStateRegister.update { it.copy(isSuccess = false) }
    }

    fun updatePrivacyPolicy(isChecked: Boolean) {
        _uiStateRegister.update {
            it.copy(
                isPrivacyPolicyChecked = it.isPrivacyPolicyChecked.copy(
                    isChecked = isChecked
                )
            )
        }
    }

    fun updateCookiePolicy(isChecked: Boolean) {
        _uiStateRegister.update {
            it.copy(
                isCookiePolicyChecked = it.isCookiePolicyChecked.copy(
                    isChecked = isChecked
                )
            )
        }
    }

    fun register() {
        viewModelScope.launch {
            if (inputsAreValid()) {
                val fields = _uiStateRegister.value
                registerUseCase.invoke(
                    fields.name.fieldValue,
                    fields.email.fieldValue,
                    fields.password.fieldValue,
                    privacyPolicy = fields.isPrivacyPolicyChecked.isChecked,
                    userAgreement = fields.isCookiePolicyChecked.isChecked,
                ).handle {
                    onSuccess {
                        _uiStateRegister.update { it.copy(isSuccess = true) }
                    }
                    onFailure {
                        _uiStateRegister.update { it.copy(errorMsg = R.string.something_went_wrong) }
                    }
                }
            }
        }
    }

    fun clearErrorMessage() {
        _uiStateRegister.update { it.copy(errorMsg = null) }
    }

    private fun inputsAreValid(): Boolean = with(_uiStateRegister.value) {
        val isNameFieldValid = name.fieldValue.isNotEmpty().also { isValid ->
            updateFieldError(NAME, isValid, R.string.no_empty_field)
        }
        val isEmailValid = email.fieldValue.isValidEmail().also { isValid ->
            updateFieldError(EMAIL, isValid, R.string.invalid_email)
        }
        val isPasswordValid = password.fieldValue.isPasswordValid().also { isValid ->
            updateFieldError(PASSWORD, isValid, R.string.invalid_password)
        }
        val isPasswordConfirmValid =
            (password.fieldValue == passwordConfirm.fieldValue).also { isValid ->
                updateFieldError(PASSWORD_CONFIRM, isValid, R.string.invalid_confirm_password)
            }

        val isPrivacyPolicyValid = isPrivacyPolicyChecked.isChecked.also { isValid ->
            updateFieldError(PRIVACY_POLICY, isValid, R.string.privacy_policy_error)
        }
        val isCookiePolicyValid = isCookiePolicyChecked.isChecked.also { isValid ->
            updateFieldError(COOKIE_POLICY, isValid, R.string.cookie_policy_error)
        }

        return@with listOf(
            isNameFieldValid,
            isEmailValid,
            isPasswordValid,
            isPasswordConfirmValid,
        ).any { isValid ->
            isValid.not()
        }.not()
    }

    private fun updateFieldError(field: String, isValid: Boolean, errorMsgResId: Int) {
        val errorMsg = if (isValid) null else errorMsgResId
        _uiStateRegister.update {
            when (field) {
                NAME -> it.copy(name = it.name.copy(errorMsg = errorMsg))
                EMAIL -> it.copy(email = it.email.copy(errorMsg = errorMsg))
                PASSWORD -> it.copy(password = it.password.copy(errorMsg = errorMsg))
                PASSWORD_CONFIRM -> it.copy(passwordConfirm = it.passwordConfirm.copy(errorMsg = errorMsg))
                PRIVACY_POLICY -> it.copy(
                    isPrivacyPolicyChecked = it.isPrivacyPolicyChecked.copy(
                        errorMsg = errorMsg
                    )
                )

                COOKIE_POLICY -> it.copy(
                    isCookiePolicyChecked = it.isCookiePolicyChecked.copy(
                        errorMsg = errorMsg
                    )
                )

                else -> it
            }
        }
    }

    private fun String.isPasswordValid(): Boolean {
        if (this.length != 8) {
            return false
        }
        val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8}\$")
        return passwordRegex.matches(this)
    }

    private fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

private const val NAME = "name"
private const val EMAIL = "email"
private const val PASSWORD = "password"
private const val PASSWORD_CONFIRM = "passwordConfirm"
private const val PRIVACY_POLICY = "privacyPolicy"
private const val COOKIE_POLICY = "cookiePolicy"