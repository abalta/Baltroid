package com.baltroid.apps.instructor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.ErrorModel
import com.baltroid.core.common.handle
import com.mobven.domain.model.TeacherDetailModel
import com.mobven.domain.usecase.TeacherDetailUseCase
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
class TeacherDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val teacherDetailUseCase: TeacherDetailUseCase
) : ViewModel() {
    private val _teacherDetailState = MutableStateFlow(TeacherDetailState())
    val teacherDetailState = _teacherDetailState.asStateFlow()

    private val teacherId: Int = checkNotNull(savedStateHandle["id"])

    private var state: TeacherDetailState
        get() = _teacherDetailState.value
        set(newState) {
            _teacherDetailState.update { newState }
        }

    init {
        getTeacherDetail()
    }

    private fun getTeacherDetail() {
        viewModelScope.launch {
            teacherDetailUseCase(teacherId).handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { detailModel ->
                    state = state.copy(
                        isLoading = false,
                        teacherDetail = detailModel, triggered
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

data class TeacherDetailState(
    val isLoading: Boolean = false,
    val teacherDetail: TeacherDetailModel? = null,
    val success: StateEvent = consumed,
    val error: StateEventWithContent<ErrorModel> = consumed()
)