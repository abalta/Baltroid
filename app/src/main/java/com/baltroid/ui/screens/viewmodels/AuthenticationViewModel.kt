package com.baltroid.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.baltroid.ui.screens.menu.profile.ProfileUIState
import com.hitreads.core.domain.usecase.ProfileUseCase
import com.hitreads.core.ui.mapper.asProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileUIState())
    val profileState = _profileState.asStateFlow()

    init {
        getProfile()
    }

    private fun getProfile() = viewModelScope.launch {
        profileUseCase().handle {
            onSuccess { profileModel ->
                _profileState.update {
                    it.copy(profile = profileModel.asProfile())
                }
            }
        }
    }
}