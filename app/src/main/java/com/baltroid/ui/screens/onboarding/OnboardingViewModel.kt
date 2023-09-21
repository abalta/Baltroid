package com.baltroid.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.model.AnnouncementModel
import com.hitreads.core.domain.model.WelcomeModel
import com.hitreads.core.domain.usecase.GetAnnouncementUseCase
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
    private val welcomeUseCase: WelcomeUseCase,
    private val getAnnouncementUseCase: GetAnnouncementUseCase
) : ViewModel() {

    private val _uiStateOnboarding = MutableStateFlow(OnboardingState())
    val uiStateOnboarding = _uiStateOnboarding.asStateFlow()

    init {
        getOnboardingData()
        getAnnouncement()
    }

    private fun getAnnouncement() {
        viewModelScope.launch {
            getAnnouncementUseCase().handle {
                onLoading {
                    _uiStateOnboarding.update { it.copy(isLoading = true) }
                }
                onSuccess { announcement ->
                    _uiStateOnboarding.update {
                        it.copy(announcementModel = announcement, isLoading = false)
                    }
                }
            }
        }
    }

    private fun getOnboardingData() = viewModelScope.launch {
        welcomeUseCase().handle {
            onLoading {
                _uiStateOnboarding.update { it.copy(isLoading = true) }
            }
            onSuccess { result ->
                _uiStateOnboarding.update { it.copy(welcomeModel = result[Random.nextInt(result.size)]) }
            }
            onFailure {
                _uiStateOnboarding.update { it.copy(isLoading = false) }
            }
        }
    }
}

data class OnboardingState(
    val announcementModel: AnnouncementModel? = null,
    val welcomeModel: WelcomeModel? = null,
    val isLoading: Boolean = false,
)