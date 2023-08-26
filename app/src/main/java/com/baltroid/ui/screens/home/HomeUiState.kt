package com.baltroid.ui.screens.home

import com.hitreads.core.domain.model.ProfileModel
import com.hitreads.core.model.FavoriteOriginal
import com.hitreads.core.model.Original
import com.hitreads.core.model.TagsWithOriginals

data class HomeUiState(
    val originals: List<TagsWithOriginals> = emptyList(),
    val favorites: List<FavoriteOriginal> = emptyList(),
    val continueReading: List<Original> = emptyList(),
    val profileModel: ProfileModel? = null,
    val isUserLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)