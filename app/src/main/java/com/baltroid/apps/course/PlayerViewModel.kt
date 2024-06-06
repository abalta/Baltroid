package com.baltroid.apps.course

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.ErrorModel
import com.baltroid.core.common.handle
import com.mobven.domain.model.VideoModel
import com.mobven.domain.usecase.PlayerUseCase
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
class PlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val playerUseCase: PlayerUseCase
) : ViewModel() {
    private val _playerState = MutableStateFlow(PlayerState())
    val playerState = _playerState.asStateFlow()

    private val playerId: String = checkNotNull(savedStateHandle["id"])

    private var state: PlayerState
        get() = _playerState.value
        set(newState) {
            _playerState.update { newState }
        }

    init {
        getVideo()
    }

    private fun getVideo() {
        viewModelScope.launch {
            playerUseCase(playerId).handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { detailModel ->
                    state = state.copy(
                        isLoading = false,
                        playerModel = detailModel, triggered
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

data class PlayerState(
    val isLoading: Boolean = false,
    val playerModel: VideoModel? = null,
    val success: StateEvent = consumed,
    val error: StateEventWithContent<ErrorModel> = consumed()
)