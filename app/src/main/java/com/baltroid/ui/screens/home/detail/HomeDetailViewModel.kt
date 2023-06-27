package com.baltroid.ui.screens.home.detail

import androidx.lifecycle.ViewModel
import com.hitreads.core.model.Original
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor() : ViewModel() {

    private val _homeDetailState: MutableStateFlow<Original?> =
        MutableStateFlow(null)
    val homeDetailState: StateFlow<Original?> = _homeDetailState.asStateFlow()

    fun setHomeDetailState(state: Original?) {
        _homeDetailState.update { state }
    }
}