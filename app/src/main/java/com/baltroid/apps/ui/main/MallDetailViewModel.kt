package com.baltroid.apps.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.baltroid.domain.GetMallUseCase
import com.baltroid.domain.GetMallsWithCitiesUseCase
import com.baltroid.model.City
import com.baltroid.model.Mall
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MallDetailViewModel @Inject constructor(
    private val getMallUseCase: GetMallUseCase,
    val imageLoader: ImageLoader,
    val fireStorage: FirebaseStorage,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow(getInitialUiState(savedStateHandle))
    val uiState = _uiState.asStateFlow()

    private var contentJob = getMall(uiState.value.mallId)

    private fun getInitialUiState(savedStateHandle: SavedStateHandle): MallDetailUiState {
        val id = MallDetailDestination.fromSavedStateHandle(savedStateHandle)
        return MallDetailUiState(mallId = id)
    }

    private fun getMall(id: String) = viewModelScope.launch {
        getMallUseCase.invoke(id).collect {mall ->
            _uiState.update {
                it.copy(mall = mall)
            }
        }
    }
}

data class MallDetailUiState(
    val mallId: String,
    val mall: Mall? = null
)