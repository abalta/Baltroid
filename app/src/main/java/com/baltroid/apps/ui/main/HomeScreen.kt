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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.baltroid.apps.R
import com.baltroid.designsystem.component.Banner
import com.baltroid.designsystem.component.CardMedium
import com.baltroid.designsystem.component.H3Title

@Composable
internal fun HomeRoute(
    onMallClick: (String) -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val mainState by viewModel.mainState.collectAsStateWithLifecycle()
    HomeScreen(mainState, onMallClick)
}

@Composable
internal fun HomeScreen(
    mainState: MainUiState,
    onMallClick: (String) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    when (mainState) {
        is MainUiState.Success -> {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(34.dp)
            ) {
                items(mainState.cityList) { city ->
                    H3Title(city.name, modifier = Modifier.padding(horizontal = 20.dp))
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
                                    mall,
                                    painter = rememberAsyncImagePainter(
                                        model = viewModel.fireStorage.getReferenceFromUrl(mall.logo),
                                        imageLoader = viewModel.imageLoader,
                                        placeholder = painterResource(id = com.baltroid.core.designsystem.R.drawable.bg_banner)
                                    ),
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

        else -> {

        }
    }
}