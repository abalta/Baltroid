package com.baltroid.apps.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.baltroid.core.data.repository.CitiesRepository
import com.baltroid.domain.GetMallsWithCitiesUseCase
import com.baltroid.model.City
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getMallsWithCitiesUseCase: GetMallsWithCitiesUseCase,
    val imageLoader: ImageLoader,
    val fireStorage: FirebaseStorage
): ViewModel() {

    val mainState: StateFlow<MainUiState> = getMallsWithCitiesUseCase.invoke().map(MainUiState::Success).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainUiState.Loading
    )
}

sealed interface MainUiState {
    object Loading : MainUiState
    data class Success(val cityList: List<City>) : MainUiState
}