package com.baltroid.apps.ui.main

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.baltroid.apps.ext.floor
import com.baltroid.apps.showCall
import com.baltroid.apps.showLocationOnMap
import com.baltroid.apps.showMail
import com.baltroid.apps.showWeb
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.H3Title
import com.baltroid.designsystem.component.MallFeature
import com.baltroid.designsystem.component.MallPhoto
import com.baltroid.designsystem.component.ServiceCard
import com.baltroid.designsystem.component.ShopCard
import com.baltroid.designsystem.component.Subhead
import com.baltroid.designsystem.dialog.MqDialog
import com.baltroid.designsystem.theme.hollyColor
import com.baltroid.designsystem.theme.hollyColor54
import com.baltroid.model.Mall
import com.baltroid.model.Shop
import com.google.firebase.storage.FirebaseStorage
import kotlin.math.roundToInt

@Composable
internal fun MallDetailRoute(
    viewModel: MallDetailViewModel,
    onBack: () -> Unit,
    goToShopSearch: () -> Unit,
) {
    val mallDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()
    MallDetailScreen(
        mallDetailUiState,
        viewModel.imageLoader,
        viewModel.fireStorage,
        onBack,
        goToShopSearch,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MallDetailScreen(
    mallDetailState: MallDetailUiState,
    imageLoader: ImageLoader,
    fireStorage: FirebaseStorage,
    onBack: () -> Unit,
    goToShopSearch: () -> Unit,
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
            val filteredShop = remember {
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
                            imageLoader,
                            fireStorage,
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
                        items = filteredShop.value
                    ) {
                        ShopCard(
                            painter = rememberAsyncImagePainter(
                                model = fireStorage.getReferenceFromUrl(it.second.logo),
                                imageLoader = imageLoader,
                                placeholder = painterResource(id = R.drawable.bg_banner)
                            ),
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
                MallDetailTopAppBar(mall, onBack, goToShopSearch)
                MallDetailMediumTopAppBar(
                    toolbarHeight,
                    toolbarOffsetHeightPx,
                    mall,
                    onBack,
                    goToShopSearch
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
    imageLoader: ImageLoader,
    fireStorage: FirebaseStorage,
    mall: Mall,
    pageCount: Int
) {
    val brush = Brush.verticalGradient(listOf(Color(0x80000000), Color.Transparent))

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MallDetailTopAppBar(
    mall: Mall,
    onBack: () -> Unit,
    goToShopSearch: () -> Unit,
) {
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }
    var showInfo by remember { mutableStateOf(false) }

    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = {
                goToShopSearch()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
            IconButton(onClick = { context.showLocationOnMap(mall.location, mall.name) }) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.White
                )
            }
            IconButton(onClick = {showMenu = !showMenu}) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = Color.White
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(text = { Subhead(text = "İletişim Bilgileri") }, onClick = {
                    showInfo = !showInfo
                    showMenu = false
                })
            }
        },
        colors = topAppBarColors(
            containerColor = Color.Transparent
        )
    )

    if (showInfo) {
        MqDialog(mall, onDismiss = { showInfo = false }, onPhoneClick = {
            context.showCall(mall.phone)
        }, onEmailClick = {
            context.showMail(mall.email)
        }, onWebClick = {
            context.showWeb(mall.web)
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MallDetailMediumTopAppBar(
    toolbarHeight: Dp,
    toolbarOffsetHeightPx: Float,
    mall: Mall,
    onBack: () -> Unit,
    goToShopSearch: () -> Unit,
) {
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }
    var showInfo by remember { mutableStateOf(false) }

    MediumTopAppBar(
        modifier = Modifier
            .height(toolbarHeight)
            .offset {
                IntOffset(
                    x = 0,
                    y = toolbarOffsetHeightPx.roundToInt()
                )
            }
            .shadow(
                elevation = 4.dp
            ),
        title = { H3Title(mall.name) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = goToShopSearch) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            IconButton(onClick = {
                context.showLocationOnMap(mall.location, mall.name)
            }) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location"
                )
            }
            IconButton(onClick = {showMenu = !showMenu}) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(text = { Subhead(text = "İletişim Bilgileri") }, onClick = {
                    showInfo = !showInfo
                    showMenu = false
                })
            }
        }
    )

    if (showInfo) {
        MqDialog(mall, onDismiss = { showInfo = false }, onPhoneClick = {
            context.showCall(mall.phone)
        }, onEmailClick = {
            context.showMail(mall.email)
        }, onWebClick = {
            context.showWeb(mall.web)
        })
    }
}