package com.baltroid.ui.common

import androidx.compose.runtime.Composable
import com.baltroid.ui.theme.LocalLoadingState

@Composable
fun SetLoadingState(isLoading: Boolean) {
    LocalLoadingState.current.value = isLoading
}