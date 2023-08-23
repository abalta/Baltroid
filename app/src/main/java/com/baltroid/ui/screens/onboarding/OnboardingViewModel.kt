package com.baltroid.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.model.WelcomeModel
import com.hitreads.core.domain.usecase.WelcomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val welcomeUseCase: WelcomeUseCase
) : ViewModel() {

    private val _uiStateOnboarding = MutableStateFlow<WelcomeModel?>(null)
    val uiStateOnboarding = _uiStateOnboarding.asStateFlow()

    init {
        getOnboardingData()
    }

    private fun getOnboardingData() = viewModelScope.launch {
        welcomeUseCase().handle {
            onSuccess { result ->
                _uiStateOnboarding.update { result[Random.nextInt(result.size)] }
            }
            onFailure {
                _uiStateOnboarding.update { null }
            }
        }
    }
}