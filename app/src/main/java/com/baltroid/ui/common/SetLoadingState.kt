package com.baltroid.ui

import androidx.compose.runtime.Composable

@Composable
fun SetLoadingState(isLoading: Boolean) {
    LocalLoadingState.current.value = isLoading
}