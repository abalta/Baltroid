package com.baltroid.apps.instructors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.baltroid.apps.courses.CoursesState
import com.baltroid.core.common.handle
import com.mobven.domain.model.CourseModel
import com.mobven.domain.model.TeacherModel
import com.mobven.domain.usecase.CourseUseCase
import com.mobven.domain.usecase.TeacherUseCase
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
class TeachersViewModel  @Inject constructor(
   teachersUseCase: TeacherUseCase
) : ViewModel() {

    private val _teachersState = MutableStateFlow(TeachersState())
    val teachersState = _teachersState.asStateFlow()

    private var state: TeachersState
        get() = _teachersState.value
        set(newState) {
            _teachersState.update { newState }
        }

    init {
        val courses = teachersUseCase.invoke().cachedIn(viewModelScope)
        viewModelScope.launch {
            state = state.copy(courses = courses)
        }
    }
}

data class TeachersState(
    val isLoading: Boolean = false,
    val courses: Flow<PagingData<TeacherModel>> = flowOf(),
    val success: StateEvent = consumed,
    val error: StateEventWithContent<String> = consumed()
)