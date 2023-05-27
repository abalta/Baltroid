package com.baltroid.presentation.screens.menu.login

import com.hitreads.core.model.Login

data class LoginUiState(
    val loginUiModel: Login? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)