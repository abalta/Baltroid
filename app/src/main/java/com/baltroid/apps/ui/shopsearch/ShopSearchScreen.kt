package com.baltroid.apps.ui.shopsearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.baltroid.apps.ext.floor
import com.baltroid.apps.ui.main.MallDetailUiState
import com.baltroid.apps.ui.main.MallDetailViewModel
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.H3Title
import com.baltroid.designsystem.component.ShopCard
import com.baltroid.designsystem.theme.h3TitleStyle
import com.baltroid.designsystem.theme.hollyColor
import com.baltroid.designsystem.theme.hollyColor54
import com.google.firebase.storage.FirebaseStorage

@Composable
internal fun ShopSearchRoute(
    viewModel: MallDetailViewModel,
    onBack: () -> Unit
) {
    val mallDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val shopSearchViewModel = hiltViewModel<ShopSearchViewModel>()
    ShopSearchScreen(
        onBack,
        shopSearchViewModel,
        mallDetailUiState
    )
}

@Composable
internal fun ShopSearchScreen(
    onBack: () -> Unit,
    shopSearchViewModel: ShopSearchViewModel,
    mallDetailState: MallDetailUiState
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ShopSearchInput(onBack, shopSearchViewModel)
        when (mallDetailState) {
            is MallDetailUiState.Success -> {
                val mall = remember {
                    mallDetailState.pairMallAndCategory.first
                }
                val shopMap = mall.shops
                val categorySelection = remember {
                    mutableIntStateOf(0)
                }
                val filteredShop = remember {
                    derivedStateOf {
                        shopMap.filter { categorySelection.intValue == 0 || categorySelection.intValue == it.value.categoryCode }
                            .toList()
                    }
                }
                shopSearchViewModel.updateShops(
                    filteredShop.value.map {
                        it.second
                    }
                )
                val shopList by shopSearchViewModel.shops.collectAsStateWithLifecycle()
                LazyColumn {
                    items(
                        key = {
                            it.code
                        },
                        items = shopList
                    ) {
                        ShopCard(
                            model = it.logo,
                            shopName = it.name,
                            floor = it.shopDetail.floor.floor(),
                            phoneNumber = it.shopDetail.phone
                        )
                        Divider(
                            thickness = 1.dp,
                            color = Color(0xFFF3F2F2),
                            modifier = Modifier.padding(all = 20.dp)
                        )
                    }
                }
            }

            is MallDetailUiState.Loading -> {

            }
        }
    }
}

@Composable
internal fun ShopSearchInput(
    onBack: () -> Unit,
    shopSearchViewModel: ShopSearchViewModel
) {
    val searchText by shopSearchViewModel.searchText.collectAsStateWithLifecycle()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.hollyColor
            )
        }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchText,
            singleLine = true,
            trailingIcon = {
                when {
                    searchText.isNotEmpty() -> IconButton(onClick = {
                        shopSearchViewModel.onSearchTextChange("")
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }

            },
            onValueChange = shopSearchViewModel::onSearchTextChange,
            placeholder = {
                H3Title(
                    text = "Mağaza, Restoran Ara",
                    color = MaterialTheme.colorScheme.hollyColor54
                )
            },
            textStyle = MaterialTheme.typography.h3TitleStyle,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.hollyColor54,
                focusedPlaceholderColor = MaterialTheme.colorScheme.hollyColor54
            )
        )
    }
}
