package com.baltroid.apps.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.ErrorModel
import com.baltroid.core.common.handle
import com.baltroid.core.designsystem.R
import com.mobven.domain.model.CategoryModel
import com.mobven.domain.model.CourseModel
import com.mobven.domain.usecase.CategoryUseCase
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
class FilterViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState = _categoryState.asStateFlow()

    private var state: CategoryState
        get() = _categoryState.value
        set(newState) {
            _categoryState.update { newState }
        }

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            categoryUseCase().handle {
                onLoading {
                    state = state.copy(isLoading = true)
                }
                onSuccess { list ->
                    state = state.copy(
                        isLoading = false,
                        categories = list,
                        sortOptions = mutableListOf(
                            SortModel(
                                "Yeniden Eskiye",
                                "en-yeniler",
                                R.drawable.sort_new_to_old
                            ),
                            SortModel(
                                "Eskiden Yeniye",
                                "eski-yeniler",
                                R.drawable.sort_old_to_new
                            ),
                            SortModel(
                                "Alfabetik",
                                "az",
                                R.drawable.sort_a_to_z
                            ),
                            SortModel(
                                "En Popüler",
                                "en-cok-izlenenler",
                                R.drawable.sort_popular
                            )
                        ),
                        triggered
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

    fun onConsumedFailedEvent() {
        state = state.copy(error = consumed())
    }
}

data class CategoryState(
    val isLoading: Boolean = false,
    val categories: List<CategoryModel>? = null,
    var sortOptions: List<SortModel>? = null,
    val success: StateEvent = consumed,
    val error: StateEventWithContent<ErrorModel> = consumed()
)

data class SortModel(
    val name: String,
    val key: String,
    val icon: Int
)