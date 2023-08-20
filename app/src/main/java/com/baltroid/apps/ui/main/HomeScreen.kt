package com.baltroid.apps.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.designsystem.component.Banner
import com.baltroid.designsystem.component.CardMedium
import com.baltroid.designsystem.component.H3Title

@Composable
internal fun HomeScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onMallClick: () -> Unit
) {
    val mainState by viewModel.mainState.collectAsStateWithLifecycle()

    when (mainState) {
        is MainUiState.Success -> {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(34.dp)
            ) {
                items((mainState as MainUiState.Success).cityList) { city ->
                    H3Title("${city.name}", modifier = Modifier.padding(horizontal = 20.dp))
                    Spacer(modifier = Modifier.height(24.dp))
                    if (city.malls.isEmpty()) {
                        Banner(modifier = Modifier.padding(horizontal = 20.dp))
                    } else {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            items(items = city.malls, itemContent = { mall ->
                                CardMedium(
                                    avmName = mall.name,
                                    viewModel.imageLoader,
                                    viewModel.fireStorage.getReferenceFromUrl(mall.logo),
                                    onMallClick
                                )
                            })
                        }
                    }
                }
            }
        }
        MainUiState.Loading -> {
            Log.i("MQ", "Home Loading")
        }
    }
}