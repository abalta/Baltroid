package com.baltroid.apps.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.baltroid.apps.auth.AuthState
import com.baltroid.apps.mapper.pagingMap
import com.baltroid.core.common.ErrorModel
import com.baltroid.core.common.handle
import com.mobven.domain.model.CourseModel
import com.mobven.domain.usecase.CourseUseCase
import com.mobven.domain.usecase.MyCoursesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCoursesViewModel @Inject constructor(
   private val myCoursesUseCase: MyCoursesUseCase
) : ViewModel() {

    private val _courseState = MutableStateFlow(MyCoursesState())
    val courseState = _courseState.asStateFlow()

    private var state: MyCoursesState
        get() = _courseState.value
        set(newState) {
            _courseState.update { newState }
        }

    init {
        getMyCourses()
    }

    fun getMyCourses() {
        viewModelScope.launch {
            myCoursesUseCase.invoke().handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { myCourses ->
                    state = state.copy(
                        isLoading = false,
                        courses = myCourses,
                        success = triggered,
                        error = triggered(null)
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

    fun onConsumedFailedEvent() {
        state = state.copy(error = consumed())
    }
}

data class MyCoursesState(
    val isLoading: Boolean = false,
    val courses:List<CourseModel>? = null,
    val success: StateEvent = consumed,
    val error: StateEventWithContent<ErrorModel?> = consumed()
)