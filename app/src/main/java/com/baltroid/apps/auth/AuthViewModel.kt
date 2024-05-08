package com.baltroid.apps.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.HttpException
import com.baltroid.core.common.handle
import com.baltroid.model.LoginModel
import com.mobven.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    private var state: AuthState
        get() = _authState.value
        set(newState) {
            _authState.update { newState }
        }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password).handle {
                onLoading {
                    state = state.copy(isLoading = true, loginModel = null)
                }
                onSuccess { loginResponseModel ->
                    state = state.copy(
                        isLoading = false, loginModel = LoginModel(
                            loginResponseModel.customerId,
                            loginResponseModel.email,
                            loginResponseModel.token
                        ), triggered
                    )
                }
                onFailure { throwable ->
                    state = state.copy(
                        isLoading = false,
                        loginModel = null,
                        error = triggered((throwable as HttpException).statusMessage ?: "Bir hata oluştu.")
                    )
                }
            }
        }
    }

    fun onConsumedSucceededEvent() {
        state = state.copy(success = consumed)
    }

    fun onConsumedFailedEvent() {
        state = state.copy(error = consumed())
    }
}

data class AuthState(
    val isLoading: Boolean = false,
    val loginModel: LoginModel? = null,
    val success: StateEvent = consumed,
    val error: StateEventWithContent<String> = consumed()
)