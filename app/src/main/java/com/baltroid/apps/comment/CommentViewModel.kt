package com.baltroid.apps.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.handle
import com.mobven.domain.usecase.CommentUseCase
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
class CommentViewModel @Inject constructor(
    private val commentUseCase: CommentUseCase
) : ViewModel() {

    private val _commentState = MutableStateFlow(CommentState())
    val commentState = _commentState.asStateFlow()

    private var state: CommentState
        get() = _commentState.value
        set(newState) {
            _commentState.update { newState }
        }

    fun addComment(
        id: Int,
        comment: String,
        rating: Int
    ) {
        viewModelScope.launch {
            commentUseCase(id, comment, rating).handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { model ->
                    state = state.copy(
                        isLoading = false,
                        success = triggered
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

    fun addCommentSucceededEvent(){
        state = state.copy(success = consumed)
    }
}

data class CommentState(
    val isLoading: Boolean = false,
    val success: StateEvent = consumed,
    val error: StateEventWithContent<String> = consumed()
)