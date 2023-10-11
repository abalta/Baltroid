package com.baltroid.ui.screens.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.HttpException
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.model.AnnouncementModel
import com.hitreads.core.domain.model.WelcomeModel
import com.hitreads.core.domain.usecase.GetAnnouncementUseCase
import com.hitreads.core.domain.usecase.LogOutUseCase
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
    private val getAnnouncementUseCase: GetAnnouncementUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private val _uiStateOnboarding = MutableStateFlow(OnboardingState())
    val uiStateOnboarding = _uiStateOnboarding.asStateFlow()

    var isSessionExpired by mutableStateOf(false)

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
                onFailure {
                    checkSession(it)
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
                checkSession(it)
                _uiStateOnboarding.update { it.copy(isLoading = false) }
            }
        }
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

data class OnboardingState(
    val announcementModel: AnnouncementModel? = null,
    val welcomeModel: WelcomeModel? = null,
    val isLoading: Boolean = false,
)