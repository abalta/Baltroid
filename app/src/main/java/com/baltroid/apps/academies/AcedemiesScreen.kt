package com.baltroid.apps.academies

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.baltroid.apps.ext.collectAsStateLifecycleAware
import com.baltroid.apps.navigation.OnAction
import com.baltroid.apps.navigation.UiAction
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikCardDouble

@Composable
fun AcademiesScreen(
    viewModel: AcademyViewModel = hiltViewModel(),
    onAction: OnAction
) {
    val uiState by viewModel.academyState.collectAsStateLifecycleAware()
    val allAcademies = uiState.academies.collectAsLazyPagingItems()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp, bottom = 64.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            FilterHeadText(
                text = stringResource(id = R.string.title_academies),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp)
            )
        }
        items(allAcademies.itemCount) {
            val academy = allAcademies[it]
            MekikCardDouble(
                captionTop = academy?.teacherCount.orEmpty(),
                title = academy?.name.orEmpty(),
                captionBottom = academy?.courseCount.orEmpty(),
                painter = academy?.logo.orEmpty(),
            ) {
                onAction(UiAction.OnAcademyClick(academy?.id ?: 0))
            }
        }
        allAcademies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .wrapContentWidth(
                                    Alignment.CenterHorizontally
                                )
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .wrapContentWidth(
                                    Alignment.CenterHorizontally
                                )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewInstructorsScreen() {
    AcademiesScreen {}
}