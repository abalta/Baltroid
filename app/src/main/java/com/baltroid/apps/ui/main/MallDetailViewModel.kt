package com.baltroid.apps.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.baltroid.domain.GetMallUseCase
import com.baltroid.model.Mall
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MallDetailViewModel @Inject constructor(
    getMallUseCase: GetMallUseCase,
    val imageLoader: ImageLoader,
    val fireStorage: FirebaseStorage,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val uiState: StateFlow<MallDetailUiState> =
        getMallUseCase.invoke(getInitialUiState(savedStateHandle)).map(MallDetailUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MallDetailUiState.Loading
            )


    private fun getInitialUiState(savedStateHandle: SavedStateHandle): String {
        return MallDetailDestination.fromSavedStateHandle(savedStateHandle)
    }

}

sealed interface MallDetailUiState {
    object Loading : MallDetailUiState
    data class Success(val mall: Mall) : MallDetailUiState
}