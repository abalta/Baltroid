package com.baltroid.ui.screens.menu.profile

import com.hitreads.core.model.Avatar
import com.hitreads.core.model.Profile

data class ProfileUIState(
    val isLoading: Boolean = false,
    val profile: Profile? = null,
    val avatars: List<Avatar> = emptyList()
)
