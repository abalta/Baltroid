package com.baltroid.apps.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.domain.GetMallsWithCitiesUseCase
import com.baltroid.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMallsWithCitiesUseCase: GetMallsWithCitiesUseCase,
): ViewModel() {

    private val _mainState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState.Loading)
    val mainState = _mainState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _mainState.update {
                getMallsWithCitiesUseCase.invoke().map(MainUiState::Success).first()
            }
        }
    }
}

sealed interface MainUiState {
    object Loading : MainUiState
    data class Success(val cityList: List<City>) : MainUiState
}