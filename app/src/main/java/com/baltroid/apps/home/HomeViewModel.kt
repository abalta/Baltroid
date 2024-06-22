package com.baltroid.apps.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.ErrorModel
import com.baltroid.core.common.EventBus
import com.baltroid.core.common.HttpException
import com.baltroid.core.common.handle
import com.mobven.domain.model.CourseModel
import com.mobven.domain.usecase.AuthUseCase
import com.mobven.domain.usecase.CategoryUseCase
import com.mobven.domain.usecase.CourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val courseUseCase: CourseUseCase,
    private val eventBus: EventBus
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    private var state: HomeState
        get() = _homeState.value
        set(newState) {
            _homeState.update { newState }
        }

    init {
        isUserLoggedIn()
        getHomeCourses()
        viewModelScope.launch {
            eventBus.events.collect {
                isUserLoggedIn()
            }
        }
    }

    fun isUserLoggedIn() {
        authUseCase().let { isLoggedIn ->
            state = state.copy(isLoggedIn = isLoggedIn)
        }
    }

    private fun getHomeCourses() {
        viewModelScope.launch {
            val coursesFlow = courseUseCase.invoke(limit = 12, null)
            val latestCoursesFlow = courseUseCase.invoke(5, "en-yeniler")
            coursesFlow.zip(latestCoursesFlow) { courses, latestCourses ->
                Pair(courses, latestCourses)
            }.collect {
                it.first.handle {
                    onLoading {
                        state = state.copy(isLoading = true)
                    }
                    onSuccess { courses ->
                        state = state.copy(
                            isLoading = false,
                            courses = courses,
                            success = triggered
                        )
                    }
                    onFailure { throwable ->
                        state = state.copy(
                            isLoading = false,
                            error = triggered(throwable)
                        )
                    }
                }
                it.second.handle {
                    onLoading {
                        state = state.copy(isLoading = true)
                    }
                    onSuccess { latestCourses ->
                        state = state.copy(
                            isLoading = false,
                            latestCourses = latestCourses,
                            success = triggered
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


    fun onConsumedFailedEvent() {
        state = state.copy(error = consumed())
    }
}

data class HomeState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val courses: List<CourseModel> = emptyList(),
    val latestCourses: List<CourseModel> = emptyList(),
    val success: StateEvent = consumed,
    val error: StateEventWithContent<ErrorModel> = consumed()
)