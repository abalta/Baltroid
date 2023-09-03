package com.baltroid.apps.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Layers
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.baltroid.designsystem.component.Body
import com.baltroid.designsystem.component.H3Title
import com.baltroid.designsystem.component.MallPhoto
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
    mallState: MallDetailUiState,
    imageLoader: ImageLoader,
    fireStorage: FirebaseStorage
) {
        if (mallState.mall != null) {
            val mall = mallState.mall
            val pagerState = rememberPagerState(pageCount = {
                mall.photos.size
            })
            Column {
                HorizontalPager(state = pagerState) {page ->
                    MallPhoto(painter = rememberAsyncImagePainter(
                        model = fireStorage.getReferenceFromUrl(mall.photos[page]),
                        imageLoader = imageLoader,
                        placeholder = painterResource(id = com.baltroid.core.designsystem.R.drawable.bg_banner)
                    ))
                }
                H3Title(text = mall.id, Modifier.padding(top = 24.dp, start = 20.dp, end = 20.dp))
                Icon(Icons.Rounded.Layers, contentDescription = "Floor")
                Body(text = "${mall.phone} • ${mall.web}", Modifier.padding(top = 14.dp, start = 20.dp, end = 20.dp))
            }
    }
}