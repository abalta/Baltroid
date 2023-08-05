package com.baltroid.apps.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.data.repository.CitiesRepository
import com.baltroid.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    citiesRepository: CitiesRepository
): ViewModel() {

    val mainState: StateFlow<MainUiState> = citiesRepository.getCities().map(MainUiState::Success).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainUiState.Loading
    )
}

sealed interface MainUiState {
    object Loading : MainUiState
    data class Success(val cityList: List<City>) : MainUiState
}