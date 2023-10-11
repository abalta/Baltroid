package com.baltroid.ui.screens.menu.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.apps.R
import com.baltroid.core.common.result.HttpException
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.usecase.ForgotPasswordUseCase
import com.hitreads.core.domain.usecase.IsLoggedUseCase
import com.hitreads.core.domain.usecase.LogOutUseCase
import com.hitreads.core.domain.usecase.LoginUseCase
import com.hitreads.core.ui.mapper.asLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loggedUseCase: IsLoggedUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiStateIsLogged = MutableStateFlow(false)
    val uiStateIsLogged = _uiStateIsLogged.asStateFlow()

    private val _uiStateLoginFields = MutableStateFlow(LoginFieldsState())
    val uiStateLoginFields = _uiStateLoginFields.asStateFlow()

    var isSessionExpired by mutableStateOf(false)

    init {
        isLogged()
    }

    fun clearAll() {
        _uiState.update { LoginUiState() }
        _uiStateIsLogged.update { false }
    }

    fun updateEmail(email: String) {
        _uiStateLoginFields.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _uiStateLoginFields.update { it.copy(password = password) }
    }

    fun login() = viewModelScope.launch {
        loginUseCase(
            _uiStateLoginFields.value.email.toString(),
            _uiStateLoginFields.value.password.toString()
        ).handle {
            onLoading {
                _uiState.update { state ->
                    state.copy(loginUiModel = it?.asLogin(), isLoading = true)
                }
            }
            onSuccess {
                _uiState.update { state ->
                    state.copy(loginUiModel = it.asLogin(), isLoading = false)
                }
                _uiStateIsLogged.update { true }
            }
            onFailure {
                _uiState.update {
                    it.copy(error = R.string.login_error)
                }
            }
        }
    }

    fun loginTest(email: String, password: String) = viewModelScope.launch {
        loginUseCase(
            email,
            password
        ).handle {
            onLoading {
                _uiState.update { state ->
                    state.copy(loginUiModel = it?.asLogin(), isLoading = true)
                }
            }
            onSuccess {
                _uiState.update { state ->
                    state.copy(loginUiModel = it.asLogin(), isLoading = false)
                }
            }
            onFailure {
                checkSession(it)
            }
        }
    }

    private fun isLogged() = viewModelScope.launch {
        loggedUseCase().handle {
            onSuccess {
                _uiStateIsLogged.update { _ -> it }
            }
            onFailure {
                checkSession(it)
                _uiStateIsLogged.update { _ -> false }
            }
        }
    }

    fun clearLoginError() {
        _uiState.update { it.copy(error = null) }
    }

    fun sendResetPassword(email: String) {
        viewModelScope.launch {
            forgotPasswordUseCase.invoke(email).handle {
                onLoading {
                    _uiState.update { it.copy(isLoading = true) }
                }
                onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            sendResetPasswordMessage = R.string.send_password_reset
                        )
                    }
                }
                onFailure {
                    checkSession(it)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            sendResetPasswordMessage = R.string.invalid_email
                        )
                    }
                }
            }
        }
    }

    fun resetInfoMessage() {
        _uiState.update { it.copy(sendResetPasswordMessage = null) }
    }

    private fun checkSession(it: Throwable) {
        try {
            if ((it as HttpException).statusCode == 401) {
                viewModelScope.launch {
                    logOutUseCase.invoke()
                    isSessionExpired = true
                }
            }
        } catch (e: Exception) {/* no-op */
        }
    }
}