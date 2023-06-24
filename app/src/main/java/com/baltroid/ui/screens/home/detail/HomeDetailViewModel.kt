package com.baltroid.ui.screens.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.baltroid.util.orZero
import com.hitreads.core.domain.usecase.ShowOriginalUseCase
import com.hitreads.core.model.Original
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(
    private val showOriginalUseCase: ShowOriginalUseCase
) : ViewModel() {

    private val _homeDetailState: MutableStateFlow<Original?> =
        MutableStateFlow(null)
    val homeDetailState: StateFlow<Original?> = _homeDetailState.asStateFlow()

    fun setHomeDetailState(state: Original?) {
        _homeDetailState.update { state }
        showOriginal(state?.id.orZero())
        println("home: $state")
    }

    private fun showOriginal(id: Int) = viewModelScope.launch {
        showOriginalUseCase.invoke(id).handle {
            onSuccess { println("detail: $it") }
            onFailure { println("failure") }
        }
    }
}