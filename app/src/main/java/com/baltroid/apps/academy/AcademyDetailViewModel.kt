package com.baltroid.apps.academy

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.handle
import com.mobven.domain.model.AcademyDetailModel
import com.mobven.domain.usecase.AcademyDetailUseCase
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
class AcademyDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val academyDetailUseCase: AcademyDetailUseCase
) : ViewModel() {
    private val _academyDetailState = MutableStateFlow(AcademyDetailState())
    val academyDetailState = _academyDetailState.asStateFlow()

    private val academyId: Int = checkNotNull(savedStateHandle["id"])

    private var state: AcademyDetailState
        get() = _academyDetailState.value
        set(newState) {
            _academyDetailState.update { newState }
        }

    init {
        getAcademyDetail()
    }

    private fun getAcademyDetail() {
        viewModelScope.launch {
            academyDetailUseCase(academyId).handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { detailModel ->
                    state = state.copy(
                        isLoading = false,
                        academyDetail = detailModel, triggered
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

data class AcademyDetailState(
    val isLoading: Boolean = false,
    val academyDetail: AcademyDetailModel? = null,
    val success: StateEvent = consumed,
    val error: StateEventWithContent<String> = consumed()
)