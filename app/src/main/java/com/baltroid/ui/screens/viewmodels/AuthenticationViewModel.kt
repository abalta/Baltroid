package com.baltroid.ui.screens.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.apps.R
import com.baltroid.core.common.result.handle
import com.baltroid.ui.screens.menu.profile.ProfileUIState
import com.baltroid.ui.screens.menu.register.RegisterScreenUIState
import com.hitreads.core.domain.usecase.GetAvatarsUseCase
import com.hitreads.core.domain.usecase.ProfileUseCase
import com.hitreads.core.domain.usecase.RegisterUseCase
import com.hitreads.core.domain.usecase.UpdateUserProfileUseCase
import com.hitreads.core.ui.mapper.asAvatar
import com.hitreads.core.ui.mapper.asProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val NAME = "name"
private const val USERNAME = "username"
private const val EMAIL = "email"
private const val BIRTHDATE = "birthdate"
private const val PASSWORD = "password"
private const val COOKIE_POLICY = "cookiePolicy"
private const val USER_AGREEMENT = "userAgreement"
//private const val PASSWORD_CONFIRM = "passwordConfirm"

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getAvatarsUseCase: GetAvatarsUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileUIState())
    val profileState = _profileState.asStateFlow()

    private val _uiStateRegister = MutableStateFlow(RegisterScreenUIState())
    val uiStateRegister = _uiStateRegister.asStateFlow()

    private val _uiStateUpdateAvatar: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val uiStateUpdateAvatar = _uiStateUpdateAvatar.asStateFlow()

    init {
        getProfile()
    }

    fun getProfile() = viewModelScope.launch {
        profileUseCase().handle {
            onSuccess { profileModel ->
                onLoading {
                    _profileState.update { it.copy(isLoading = true) }
                }
                _profileState.update {
                    it.copy(profile = profileModel.asProfile(), isLoading = false)
                }
                onFailure {
                    _profileState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun updateUserProfile(username: String, nickname: String, email: String) {
        viewModelScope.launch {
            updateUserProfileUseCase.invoke(username = username, nickname = nickname, email = email)
                .handle {
                    onLoading {
                        _profileState.update { it.copy(isLoading = true) }
                    }
                    onSuccess {
                        _profileState.update { it.copy(isLoading = false, isProfileUpdated = true) }
                    }
                    onFailure {
                        _profileState.update {
                            it.copy(
                                isLoading = false,
                                isProfileUpdated = false
                            )
                        }
                    }
                }
        }
    }

    fun clearProfileUpdatedState() {
        _profileState.update { it.copy(isProfileUpdated = null) }
    }

    fun loadAvatars() = viewModelScope.launch {
        getAvatarsUseCase().handle {
            onLoading {
                _profileState.update {
                    it.copy(isLoading = true)
                }
            }
            onSuccess { avatarModels ->
                _profileState.update {
                    it.copy(isLoading = false, avatars = avatarModels.map { it.asAvatar() })
                }
            }
        }
    }

    fun updateNameField(value: String) {
        _uiStateRegister.update { it.copy(name = it.name.copy(fieldValue = value)) }
    }

    fun updateBirthDateField(value: String) {
        _uiStateRegister.update { it.copy(birthdate = it.birthdate.copy(fieldValue = value)) }
    }

    fun updateUsernameField(value: String) {
        _uiStateRegister.update { it.copy(username = it.username.copy(fieldValue = value)) }
    }

    fun updateEmailField(value: String) {
        _uiStateRegister.update { it.copy(email = it.email.copy(fieldValue = value)) }
    }

    fun updatePasswordField(value: String) {
        if (value.length <= 8) {
            _uiStateRegister.update { it.copy(password = it.password.copy(fieldValue = value)) }
        }
    }

    /* fun updatePasswordConfirmField(value: String) {
         _uiStateRegister.update { it.copy(passwordConfirm = it.passwordConfirm.copy(fieldValue = value)) }
     }*/

    fun clearSuccess() {
        _uiStateRegister.update { it.copy(isSuccess = false) }
    }

    fun updatePrivacyPolicy(isChecked: Boolean) {
        _uiStateRegister.update {
            it.copy(
                userAgreement = it.userAgreement.copy(
                    isChecked = isChecked
                )
            )
        }
    }

    fun updateCookiePolicy(isChecked: Boolean) {
        _uiStateRegister.update {
            it.copy(
                cookiePolicy = it.cookiePolicy.copy(
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
                    name = fields.name.fieldValue,
                    username = fields.username.fieldValue,
                    password = fields.password.fieldValue,
                    email = fields.email.fieldValue,
                    cookiePolicy = fields.cookiePolicy.isChecked,
                    userAgreement = fields.userAgreement.isChecked,
                    birthdate = fields.birthdate.fieldValue
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
        val isUsernameFieldValid = username.fieldValue.isNotEmpty().also { isValid ->
            updateFieldError(USERNAME, isValid, R.string.no_empty_field)
        }
        val isEmailValid = email.fieldValue.isValidEmail().also { isValid ->
            updateFieldError(EMAIL, isValid, R.string.invalid_email)
        }
        val isPasswordValid = password.fieldValue.isPasswordValid().also { isValid ->
            updateFieldError(PASSWORD, isValid, R.string.invalid_password)
        }
        /*val isPasswordConfirmValid =
            (password.fieldValue == passwordConfirm.fieldValue).also { isValid ->
                updateFieldError(PASSWORD_CONFIRM, isValid, R.string.invalid_confirm_password)
            }*/

        val isUserAgreementValid = userAgreement.isChecked.also { isValid ->
            updateFieldError(USER_AGREEMENT, isValid, R.string.privacy_policy_error)
        }
        val isCookiePolicyValid = cookiePolicy.isChecked.also { isValid ->
            updateFieldError(COOKIE_POLICY, isValid, R.string.cookie_policy_error)
        }
        val isBirthdateValid = birthdate.fieldValue.isNotEmpty().also { isValid ->
            updateFieldError(BIRTHDATE, isValid, R.string.no_empty_field)
        }

        return@with listOf(
            isNameFieldValid,
            isUsernameFieldValid,
            isEmailValid,
            isBirthdateValid,
            isPasswordValid,
            isUserAgreementValid,
            isCookiePolicyValid,
        ).any { isValid ->
            isValid.not()
        }.not()
    }

    private fun updateFieldError(field: String, isValid: Boolean, errorMsgResId: Int) {
        val errorMsg = if (isValid) null else errorMsgResId
        _uiStateRegister.update {
            when (field) {
                NAME -> it.copy(name = it.name.copy(errorMsg = errorMsg))
                USERNAME -> it.copy(name = it.username.copy(errorMsg = errorMsg))
                EMAIL -> it.copy(email = it.email.copy(errorMsg = errorMsg))
                PASSWORD -> it.copy(password = it.password.copy(errorMsg = errorMsg))
                BIRTHDATE -> it.copy(birthdate = it.birthdate.copy(errorMsg = errorMsg))
                USER_AGREEMENT -> it.copy(
                    userAgreement = it.userAgreement.copy(
                        errorMsg = errorMsg
                    )
                )

                COOKIE_POLICY -> it.copy(
                    cookiePolicy = it.cookiePolicy.copy(
                        errorMsg = errorMsg
                    )
                )
                //PASSWORD_CONFIRM -> it.copy(passwordConfirm = it.passwordConfirm.copy(errorMsg = errorMsg))
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

    fun updateUserAvatar(selectedId: Int) {
        viewModelScope.launch {
            updateUserProfileUseCase.invoke(avatarId = selectedId).handle {
                onSuccess {
                    _uiStateUpdateAvatar.update { true }
                }
                onFailure {
                    _uiStateUpdateAvatar.update { false }
                }
            }
        }
    }
}