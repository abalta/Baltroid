package com.baltroid.presentation.screens.menu.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
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
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun login(email: String, password: String) = viewModelScope.launch {
        loginUseCase(email, password).handle {
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

    private fun handleFailure(error: Throwable) = _uiState.update {
        it.copy(
            error = error,
            isLoading = false
        )
    }

}