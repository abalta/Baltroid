package com.baltroid.ui

import com.hitreads.core.domain.model.ProfileModel
import com.hitreads.core.model.Original
import com.hitreads.core.model.TagsWithOriginals

data class HomeUiState(
    val originals: List<TagsWithOriginals> = emptyList(),
    val favorites: List<Original> = emptyList(),
    val profileModel: ProfileModel? = null,
    val isUserLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)