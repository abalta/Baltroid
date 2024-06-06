package com.baltroid.apps.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.ErrorModel
import com.baltroid.core.common.handle
import com.mobven.domain.model.CourseModel
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
class FavoriteViewModel @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {

    private val _favoriteState = MutableStateFlow(FavoriteState())
    val favoriteState = _favoriteState.asStateFlow()

    private var state: FavoriteState
        get() = _favoriteState.value
        set(newState) {
            _favoriteState.update { newState }
        }

    init {
        getFavorites()
    }

    fun getFavorites() {
        viewModelScope.launch {
            favoriteUseCase().handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { list ->
                    state = state.copy(
                        isLoading = false,
                        favorites = list, triggered
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

data class FavoriteState(
    val isLoading: Boolean = false,
    val favorites: List<CourseModel>? = null,
    val success: StateEvent = consumed,
    val error: StateEventWithContent<ErrorModel> = consumed()
)