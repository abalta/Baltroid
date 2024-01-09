package com.baltroid.apps.ui.mallplan

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.ui.main.MallDetailUiState
import com.baltroid.apps.ui.main.MallDetailViewModel
import com.baltroid.designsystem.component.H3Title
import com.baltroid.designsystem.component.ZoomableImage
import com.baltroid.designsystem.theme.hollyColor
import com.baltroid.model.Mall
import kotlinx.coroutines.launch

@Composable
internal fun MallPlanRoute(
    viewModel: MallDetailViewModel,
    onBack: () -> Unit
) {
    val mallDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()
    MallPlanScreen(
        onBack,
        mallDetailUiState
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MallPlanScreen(
    onBack: () -> Unit,
    mallDetailState: MallDetailUiState
) {
    when (mallDetailState) {
        is MallDetailUiState.Success -> {
            val coroutineScope = rememberCoroutineScope()

            val mall = remember {
                mallDetailState.pairMallAndCategory.first
            }
            val pagerState = rememberPagerState(pageCount = {
                mall.floors.size
            })

            Box {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)) {
                    HorizontalPager(
                        state = pagerState,
                        userScrollEnabled = false,
                        modifier = Modifier.weight(1f)
                    ) { page ->
                        ZoomableImage(
                            painter = mall.floors[page].plan,
                        )
                    }
                    Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                        }
                        H3Title(text = "${mall.floors.getOrNull(pagerState.currentPage)?.no ?: 0}. Kat")
                        IconButton(onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }) {
                            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "")
                        }
                    }
                }
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.hollyColor
                    )
                }
            }
        }
        MallDetailUiState.Loading -> {}
    }
}

@Preview
@Composable
fun PreviewMallPlan() {
    MallPlanScreen(onBack = { /*TODO*/ }, mallDetailState = MallDetailUiState.Success(
        Pair(
            Mall(
                id = "etiam",
                cityCode = 5831,
                address = "habitasse",
                email = "christine.pena@example.com",
                floors = listOf(),
                location = Pair(0.0, 0.0),
                name = "Kristie Christensen",
                phone = "(268) 788-8275",
                services = mutableMapOf(),
                web = "dictum",
                logo = "litora",
                photos = listOf(),
                rating = "nec",
                reviews = "similique",
                district = "propriae",
                shops = mutableMapOf()
            ),
            mutableListOf()
        )
    ))
}