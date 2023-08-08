package com.baltroid.apps.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun HomeScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val mainState by viewModel.mainState.collectAsStateWithLifecycle()

}