package com.baltroid.apps.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.ErrorModel
import com.baltroid.core.common.handle
import com.mobven.domain.model.ProfileModel
import com.mobven.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    private var state: ProfileState
        get() = _profileState.value
        set(newState) {
            _profileState.update { newState }
        }

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            profileUseCase().handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { model ->
                    state = state.copy(
                        isLoading = false,
                        profile = model, triggered
                    )
                }
                onFailure { throwable ->
                    state = state.copy(
                        isLoading = false,
                        error = triggered(throwable)
                    )
                }
            }
        }
    }

    fun updateProfile(
        email: String?,
        firstname: String?,
        lastname: String?,
        phone: String?,
        about: String?
    ) {
        viewModelScope.launch {
            profileUseCase(email, firstname, lastname, phone, about).handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { model ->
                    state = state.copy(
                        isLoading = false,
                        profile = model, triggered
                    )
                }
                onFailure { throwable ->
                    state = state.copy(
                        isLoading = false,
                        error = triggered(throwable)
                    )
                }
            }
        }
    }
}

data class ProfileState(
    val isLoading: Boolean = false,
    val profile: ProfileModel? = null,
    val success: StateEvent = consumed,
    val error: StateEventWithContent<ErrorModel> = consumed()
)