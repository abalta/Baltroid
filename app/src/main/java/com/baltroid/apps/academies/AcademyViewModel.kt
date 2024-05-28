package com.baltroid.apps.academies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mobven.domain.model.AcademyModel
import com.mobven.domain.usecase.AcademyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AcademyViewModel @Inject constructor(
   academyUseCase: AcademyUseCase
) : ViewModel() {

    private val _academyState = MutableStateFlow(AcademyState())
    val academyState = _academyState.asStateFlow()

    private var state: AcademyState
        get() = _academyState.value
        set(newState) {
            _academyState.update { newState }
        }

    init {
        val academies = academyUseCase.invoke().cachedIn(viewModelScope)
        viewModelScope.launch {
            state = state.copy(academies = academies)
        }
    }
}

data class AcademyState(
    val isLoading: Boolean = false,
    val academies: Flow<PagingData<AcademyModel>> = flowOf(),
    val success: StateEvent = consumed,
    val error: StateEventWithContent<String> = consumed()
)