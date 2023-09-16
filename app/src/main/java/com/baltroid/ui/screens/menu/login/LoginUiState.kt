package com.baltroid.ui.screens.menu.login

import androidx.annotation.StringRes
import com.hitreads.core.model.Login

data class LoginUiState(
    val loginUiModel: Login? = null,
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null,
)