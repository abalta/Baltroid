package com.baltroid.apps.course

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.ErrorModel
import com.baltroid.core.common.handle
import com.mobven.domain.model.CourseModel
import com.mobven.domain.usecase.CourseDetailUseCase
import com.mobven.domain.usecase.FavoriteUseCase
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
    private val courseDetailUseCase: CourseDetailUseCase,
    private val favoriteUseCase: FavoriteUseCase
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

    fun getCourseDetail() {
        viewModelScope.launch {
            courseDetailUseCase(courseId).handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { detailModel ->
                    state = state.copy(
                        isLoading = false,
                        courseDetail = detailModel, success = triggered
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

    fun addFavorite() {
        viewModelScope.launch {
            favoriteUseCase.invokeAdd(courseId).handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { _ ->
                    state = state.copy(
                        isLoading = false,
                        fav = triggered
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

    fun deleteFavorite() {
        viewModelScope.launch {
            favoriteUseCase.invokeDelete(courseId).handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { _ ->
                    state = state.copy(
                        isLoading = false,
                        fav = triggered
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

    fun favState() {
        state = state.copy(fav = consumed)
    }

    fun onConsumedFailedEvent() {
        state = state.copy(error = consumed())
    }
}

data class CourseDetailState(
    val isLoading: Boolean = false,
    val courseDetail: CourseModel? = null,
    val fav: StateEvent = consumed,
    val success: StateEvent = consumed,
    val error: StateEventWithContent<ErrorModel> = consumed()
)