package com.baltroid.apps.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.baltroid.apps.auth.AuthState
import com.baltroid.apps.mapper.pagingMap
import com.baltroid.core.common.handle
import com.mobven.domain.model.CourseModel
import com.mobven.domain.usecase.CourseUseCase
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
class CoursesViewModel  @Inject constructor(
   courseUseCase: CourseUseCase
) : ViewModel() {

    private val _courseState = MutableStateFlow(CoursesState())
    val courseState = _courseState.asStateFlow()

    private var state: CoursesState
        get() = _courseState.value
        set(newState) {
            _courseState.update { newState }
        }

    init {
        val courses = courseUseCase.invoke().cachedIn(viewModelScope)
        val latestCoursesFlow = courseUseCase.invoke(5, "en-yeniler")
        viewModelScope.launch {
            latestCoursesFlow.handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { latestCourses ->
                    state = state.copy(latestCourses = latestCourses)
                }
                onFailure {
                    state = state.copy(error = triggered(it))
                }
            }
            state = state.copy(courses = courses)
        }
    }
}

data class CoursesState(
    val isLoading: Boolean = false,
    val courses: Flow<PagingData<CourseModel>> = flowOf(),
    val latestCourses: List<CourseModel> = emptyList(),
    val success: StateEvent = consumed,
    val error: StateEventWithContent<String> = consumed()
)