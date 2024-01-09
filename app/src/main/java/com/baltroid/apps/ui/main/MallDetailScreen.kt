package com.baltroid.apps.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.ext.floor
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.H3Title
import com.baltroid.designsystem.component.MallDetailMediumTopAppBar
import com.baltroid.designsystem.component.MallDetailTopAppBar
import com.baltroid.designsystem.component.MallFeature
import com.baltroid.designsystem.component.MallPhoto
import com.baltroid.designsystem.component.ServiceCard
import com.baltroid.designsystem.component.ShopCard
import com.baltroid.designsystem.component.Subhead
import com.baltroid.designsystem.theme.hollyColor
import com.baltroid.designsystem.theme.hollyColor54
import com.baltroid.model.Mall
import com.baltroid.model.Shop

@Composable
internal fun MallDetailRoute(
    viewModel: MallDetailViewModel,
    onBack: () -> Unit,
    goToShopSearch: () -> Unit,
    goToMallPlan: () -> Unit,
    ) {
    val mallDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()
    MallDetailScreen(
        mallDetailUiState,
        onBack,
        goToShopSearch,
        goToMallPlan,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MallDetailScreen(
    mallDetailState: MallDetailUiState,
    onBack: () -> Unit,
    goToShopSearch: () -> Unit,
    goToMallPlan: () -> Unit
) {

    when (mallDetailState) {
        is MallDetailUiState.Success -> {
            val mall = remember {
                mallDetailState.pairMallAndCategory.first
            }
            val categoryList = remember { mallDetailState.pairMallAndCategory.second }
            val shopMap = mall.shops
            val pageCount = mall.photos.size
            val pagerState = rememberPagerState(pageCount = {
                pageCount
            })
            val categorySelection = remember {
                mutableIntStateOf(0)
            }
            val filteredShop by remember {
                derivedStateOf {
                    shopMap.filter { categorySelection.intValue == 0 || categorySelection.intValue == it.value.categoryCode }
                        .toList()
                }
            }
            val toolbarHeight = 112.dp
            val toolbarHeightPx =
                with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
            var toolbarOffsetHeightPx by rememberSaveable { mutableFloatStateOf(-toolbarHeightPx) }
            val lazyListState = rememberLazyListState()
            val isNestedConnectionEnable by remember {
                derivedStateOf {
                    lazyListState.firstVisibleItemIndex in 1..2
                }
            }
            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        val delta = available.y
                        val newOffset = toolbarOffsetHeightPx - delta
                        if (isNestedConnectionEnable) {
                            toolbarOffsetHeightPx = newOffset.coerceIn(-toolbarHeightPx, 0f)
                        }
                        return Offset.Zero
                    }
                }
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .nestedScroll(nestedScrollConnection)
            ) {
                LazyColumn(state = lazyListState) {
                    item {
                        MallTopPager(
                            pagerState,
                            mall,
                            pageCount
                        )
                    }
                    item {
                        H3Title(
                            text = mall.name,
                            modifier = Modifier.padding(top = 24.dp, start = 20.dp, end = 20.dp)
                        )
                        MallFeatureGroup(
                            mall,
                            shopMap
                        )
                    }
                    item {
                        Subhead(
                            text = stringResource(id = R.string.services),
                            Modifier.padding(top = 34.dp, start = 20.dp, end = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            items(key = {
                                it.code
                            }, items = mall.services.map {
                                it.value
                            }, itemContent = { service ->
                                ServiceCard(serviceName = service.name, serviceIcon = service.icon)
                            })
                        }
                    }
                    item {
                        Subhead(
                            text = stringResource(id = R.string.shops),
                            Modifier.padding(top = 34.dp, start = 20.dp, end = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            items(key = {
                                it.code
                            }, items = categoryList, itemContent = { category ->
                                H3Title(
                                    text = category.name,
                                    color = if (categorySelection.intValue == category.code) MaterialTheme.colorScheme.hollyColor else MaterialTheme.colorScheme.hollyColor54,
                                    modifier = Modifier.selectable(
                                        selected = categorySelection.intValue == category.code,
                                        onClick = {
                                            categorySelection.intValue = category.code
                                        }
                                    ))
                            })
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                    items(
                        key = {
                            it.second.code
                        },
                        items = filteredShop
                    ) {
                        ShopCard(
                            model = it.second.logo,
                            shopName = it.second.name,
                            floor = it.second.shopDetail.floor.floor(),
                            phoneNumber = it.second.shopDetail.phone
                        )
                        Divider(
                            thickness = 1.dp,
                            color = Color(0xFFF3F2F2),
                            modifier = Modifier.padding(all = 20.dp)
                        )
                    }
                }
                MallDetailTopAppBar(mall, onBack, goToShopSearch, goToMallPlan)
                MallDetailMediumTopAppBar(
                    toolbarHeight,
                    toolbarOffsetHeightPx,
                    mall,
                    onBack,
                    goToShopSearch,
                    goToMallPlan
                )
            }

        }

        is MallDetailUiState.Loading -> {

        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MallTopPager(
    pagerState: PagerState,
    mall: Mall,
    pageCount: Int
) {
    val brush = Brush.verticalGradient(listOf(Color(0x80000000), Color.Transparent))

    Box {
        HorizontalPager(state = pagerState) { page ->
            MallPhoto(model = mall.photos[page])
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(280.dp)
                .background(brush)
        )
    }
}

@Composable
internal fun MallFeatureGroup(
    mall: Mall,
    shopMap: Map<Int, Shop>
) {
    Row(
        Modifier.padding(top = 14.dp, start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        if (mall.floors.size == 1) {
            MallFeature(
                featureCount = stringResource(
                    id = R.string.semi
                ),
                featureIcon = R.drawable.icon_flat,
                featureName = stringResource(
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
            featureCount = shopMap.size.toString(),
            featureIcon = R.drawable.icon_shops,
            featureName = stringResource(
                id = R.string.shop
            )
        )
    }
}