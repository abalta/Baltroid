package com.baltroid.apps.ui.main

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.CardMedium
import com.baltroid.designsystem.component.H3Title
import com.baltroid.designsystem.component.MallFeature
import com.baltroid.designsystem.component.MallPhoto
import com.baltroid.designsystem.component.ServiceCard
import com.baltroid.designsystem.component.Subhead
import com.baltroid.designsystem.theme.hollyColor
import com.baltroid.designsystem.theme.hollyColor54
import com.google.firebase.storage.FirebaseStorage

@Composable
internal fun MallDetailRoute(
    viewModel: MallDetailViewModel = hiltViewModel()
) {
    val mallDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()
    MallDetailScreen(mallDetailUiState, viewModel.imageLoader, viewModel.fireStorage)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MallDetailScreen(
    mallDetailState: MallDetailUiState,
    imageLoader: ImageLoader,
    fireStorage: FirebaseStorage
) {
    when (mallDetailState) {
        is MallDetailUiState.Success -> {
            val mall = mallDetailState.pairMallAndCategory.first
            val categoryList = mallDetailState.pairMallAndCategory.second
            val shopMap = mall.shops
            val pageCount = mall.photos.size
            val pagerState = rememberPagerState(pageCount = {
                pageCount
            })
            val categorySelection = remember {
                mutableIntStateOf(0)
            }
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box {
                    HorizontalPager(state = pagerState) { page ->
                        MallPhoto(
                            painter = rememberAsyncImagePainter(
                                model = fireStorage.getReferenceFromUrl(mall.photos[page]),
                                imageLoader = imageLoader,
                                placeholder = painterResource(id = R.drawable.bg_banner)
                            )
                        )
                    }
                    Row(
                        Modifier
                            .padding(20.dp)
                            .height(5.dp)
                            .align(Alignment.BottomEnd),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        repeat(pageCount) { iteration ->
                            val color =
                                if (pagerState.currentPage == iteration) Color.White else Color(
                                    0x4DFFFFFF
                                )
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(color)

                            )
                        }
                    }
                }
                H3Title(
                    text = mall.name,
                    modifier = Modifier.padding(top = 24.dp, start = 20.dp, end = 20.dp)
                )
                Row(
                    Modifier.padding(top = 14.dp, start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    if (mall.floors.size == 1) {
                        MallFeature(
                            featureCount = stringResource(
                                id = R.string.semi
                            ), featureIcon = R.drawable.icon_flat, featureName = stringResource(
                                id = R.string.open
                            )
                        )
                    } else {
                        MallFeature(
                            featureCount = mall.floors.size.toString(),
                            featureIcon = R.drawable.icon_floors,
                            featureName = stringResource(
                                id = R.string.floor
                            )
                        )
                    }

                    MallFeature(
                        featureCount = mall.services.size.toString(),
                        featureIcon = R.drawable.icon_services,
                        featureName = stringResource(
                            id = R.string.service
                        )
                    )
                    MallFeature(
                        featureCount = mall.services.size.toString(),
                        featureIcon = R.drawable.icon_shops,
                        featureName = stringResource(
                            id = R.string.shop
                        )
                    )
                }
                Subhead(
                    text = stringResource(id = R.string.services),
                    Modifier.padding(top = 34.dp, start = 20.dp, end = 20.dp)
                )
                Spacer(modifier = Modifier.height(18.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(items = mall.services.map {
                        it.value
                    }, itemContent = { service ->
                        ServiceCard(serviceName = service.name, serviceIcon = service.icon)
                    })
                }
                Subhead(
                    text = stringResource(id = R.string.shops),
                    Modifier.padding(top = 34.dp, start = 20.dp, end = 20.dp)
                )
                Spacer(modifier = Modifier.height(18.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(items = categoryList, itemContent = { category ->
                        H3Title(
                            text = category.name,
                            color = if(categorySelection.intValue == category.code) MaterialTheme.colorScheme.hollyColor else MaterialTheme.colorScheme.hollyColor54,
                            modifier = Modifier.selectable(
                                selected = categorySelection.intValue == category.code,
                                onClick = {
                                    categorySelection.intValue = category.code
                                }
                            ))
                    })
                }
                Spacer(modifier = Modifier.height(18.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    shopMap.forEach {
                        if (categorySelection.intValue == 0) {
                            Subhead(text = it.value.name)
                        } else if (categorySelection.intValue == it.value.categoryCode) {
                            Subhead(text = it.value.name)
                        }
                    }
                }
            }
        }

        is MallDetailUiState.Loading -> {

        }
    }

}