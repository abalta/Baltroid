package com.baltroid.apps.course

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.apps.home.HomeState
import com.baltroid.core.common.HttpException
import com.baltroid.core.common.handle
import com.baltroid.model.LoginModel
import com.mobven.domain.model.CourseDetailModel
import com.mobven.domain.usecase.CourseDetailUseCase
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
class CourseDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val courseDetailUseCase: CourseDetailUseCase
) : ViewModel() {
    private val _courseDetailState = MutableStateFlow(CourseDetailState())
    val courseDetailState = _courseDetailState.asStateFlow()

    private val courseId: Int = checkNotNull(savedStateHandle["id"])

    private var state: CourseDetailState
        get() = _courseDetailState.value
        set(newState) {
            _courseDetailState.update { newState }
        }

    init {
        getCourseDetail()
    }

    private fun getCourseDetail() {
        viewModelScope.launch {
            courseDetailUseCase(courseId).handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { detailModel ->
                    state = state.copy(
                        isLoading = false,
                        courseDetail = detailModel, triggered
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

data class CourseDetailState(
    val isLoading: Boolean = false,
    val courseDetail: CourseDetailModel? = null,
    val success: StateEvent = consumed,
    val error: StateEventWithContent<String> = consumed()
)