package com.baltroid.presentation.screens.menu.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.usecase.IsLoggedUseCase
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
    private val loggedUseCase: IsLoggedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiStateIsLogged = MutableStateFlow(false)
    val uiStateIsLogged = _uiStateIsLogged.asStateFlow()

    private val _uiStateLoginFields = MutableStateFlow(LoginFieldsState())
    val uiStateLoginFields = _uiStateLoginFields.asStateFlow()

    init {
        isLogged()
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
            }
            onFailure(::handleFailure)
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
            onFailure(::handleFailure)
        }
    }

    private fun isLogged() = viewModelScope.launch {
        loggedUseCase().handle {
            onSuccess {
                _uiStateIsLogged.update { _ -> it }
            }
            onFailure {
                _uiStateIsLogged.update { _ -> false }
            }
        }
    }

    private fun handleFailure(error: Throwable) = _uiState.update {
        it.copy(
            error = error,
            isLoading = false
        )
    }

}