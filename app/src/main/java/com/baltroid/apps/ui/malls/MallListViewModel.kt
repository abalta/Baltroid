package com.baltroid.apps.ui.malls

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.apps.navigation.destinations.HomeDestination
import com.baltroid.domain.GetMallsByCityUseCase
import com.baltroid.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MallListViewModel @Inject constructor(
    getMallsByCityUseCase: GetMallsByCityUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val uiState: StateFlow<MallListUiState> =
        getMallsByCityUseCase.invoke(getInitialUiState(savedStateHandle)).map(MallListUiState::Success).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MallListUiState.Loading
        )

    private fun getInitialUiState(savedStateHandle: SavedStateHandle): Int {
        return HomeDestination.fromSavedStateHandle(savedStateHandle)
    }

}

sealed interface MallListUiState {
    object Loading : MallListUiState
    data class Success(val city: City) : MallListUiState
}