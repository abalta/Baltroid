package com.baltroid.apps.ui.main

import android.util.Log
import androidx.compose.animation.core.animate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.baltroid.designsystem.component.Banner
import com.baltroid.designsystem.component.Body
import com.baltroid.designsystem.component.MallCard
import com.baltroid.designsystem.component.H3Title
import com.baltroid.designsystem.theme.eucalyptusColor
import com.baltroid.model.City
import com.baltroid.model.Mall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeRoute(
    viewModel: MainViewModel,
    onMallClick: (String) -> Unit,
    onMallList: (Int) -> Unit
) {
    val mainState by viewModel.mainState.collectAsStateWithLifecycle()
    val state = rememberPullToRefreshState()
    if (state.isRefreshing) {
        Log.i("MQ", "Refreshing")
        viewModel.load()
    }
    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(state.nestedScrollConnection)
    ) {
        HomeScreen(mainState, state, onMallClick, onMallList)
        PullToRefreshContainer(state = state, modifier = Modifier.align(Alignment.TopCenter))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    mainState: MainUiState,
    pullState: PullToRefreshState,
    onMallClick: (String) -> Unit,
    onMallList: (Int) -> Unit
) {
    when (mainState) {
        is MainUiState.Success -> {
            Log.i("MQ", "Home Success")
            pullState.endRefresh()
            LazyColumn(
                contentPadding = PaddingValues(vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(34.dp),
            ) {
                items(key = {
                    it.code
                }, items = mainState.cityList) { city ->
                    if (pullState.isRefreshing.not()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            H3Title(city.name, modifier = Modifier.padding(horizontal = 20.dp))
                            if (city.malls.isNotEmpty()) {
                                TextButton(onClick = {
                                    onMallList(city.code)
                                }, modifier = Modifier.padding(horizontal = 8.dp)) {
                                    Body(
                                        text = "Tümünü gör",
                                        color = MaterialTheme.colorScheme.eucalyptusColor,
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        if (city.malls.isEmpty()) {
                            Banner(modifier = Modifier.padding(horizontal = 20.dp))
                        } else {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                items(key = {
                                    it.id
                                }, items = city.malls, itemContent = { mall ->
                                    MallCard(
                                        mall,
                                        onMallClick
                                    )
                                })
                            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(mainState = MainUiState.Success(
        listOf(
            City(
                code = 6391, name = "Stephanie Levy", image = "nibh", malls = mutableListOf(
                    Mall(
                        id = "purus",
                        cityCode = 1980,
                        address = "repudiare",
                        email = "sandy.holland@example.com",
                        floors = listOf(),
                        location = Pair(0.0, 0.0),
                        name = "Angelique Patton",
                        phone = "(649) 890-4837",
                        services = mutableMapOf(),
                        web = "hendrerit",
                        logo = "ludus",
                        photos = listOf(),
                        rating = "4.2",
                        reviews = "13k",
                        district = "adipiscing",
                        shops = mutableMapOf()
                    )
                )
            ),
            City(
                code = 6541,
                name = "Eunice Bradshaw",
                image = "ubique",
                malls = mutableListOf()
            )
        )
    ), rememberPullToRefreshState(), onMallClick = {}, onMallList = {})
}