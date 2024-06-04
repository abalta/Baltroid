package com.baltroid.apps.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.handle
import com.mobven.domain.model.SearchModel
import com.mobven.domain.model.TotalModel
import com.mobven.domain.usecase.SearchUseCase
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
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    private var state: SearchState
        get() = _searchState.value
        set(newState) {
            _searchState.update { newState }
        }

    init {
        total()
    }

    private fun total() {
        viewModelScope.launch {
            searchUseCase().handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { model ->
                    state = state.copy(
                        isLoading = false,
                        totalModel = model, success = triggered
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

    fun search(query: String) {
        viewModelScope.launch {
            searchUseCase(query).handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { model ->
                    state = state.copy(
                        isLoading = false,
                        searchModel = model, success = triggered
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

data class SearchState(
    val isLoading: Boolean = false,
    val searchModel: SearchModel? = null,
    val totalModel: TotalModel? = null,
    val success: StateEvent = consumed,
    val error: StateEventWithContent<String> = consumed()
)