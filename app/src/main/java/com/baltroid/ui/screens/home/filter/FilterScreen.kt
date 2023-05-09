package com.baltroid.ui.screens.home.filter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilterScreen(
    onBackClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .navigationBarsPadding()
    ) {
        VerticalSpacer(height = MaterialTheme.localDimens.dp36)
        MenuBar(
            title = stringResource(id = R.string.filter),
            iconResId = R.drawable.ic_filter,
            onBackClick = onBackClick,
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun FilterScreenPreview() {
    FilterScreen {

    }
}

