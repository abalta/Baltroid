package com.baltroid.apps.courses

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.baltroid.apps.ext.collectAsStateLifecycleAware
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.paging.LoadState
import com.baltroid.apps.filter.FilterSheet
import com.baltroid.apps.home.RecentCourses
import com.baltroid.apps.navigation.OnAction
import com.baltroid.apps.navigation.UiAction
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikCard
import com.baltroid.designsystem.component.RowtitleText
import com.baltroid.designsystem.theme.electricVioletColor

@Composable
fun CoursesScreen(
    viewModel: CoursesViewModel = hiltViewModel(), onAction: OnAction
) {
    val uiState by viewModel.courseState.collectAsStateLifecycleAware()
    val allCourses = uiState.courses.collectAsLazyPagingItems()
    var showFilterSheet by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp, bottom = 64.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            FilterHeadText(
                text = stringResource(id = R.string.title_courses),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp)
            ) {
                showFilterSheet = true
            }
        }
        if (uiState.latestCourses.isNotEmpty()) {
            item {
                RowtitleText(
                    text = "Son Eklenenler",
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 18.dp),
                    color = MaterialTheme.colorScheme.electricVioletColor
                )
            }
            item {
                RecentCourses(onAction, uiState.latestCourses)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        items(allCourses.itemCount) {
            val course = allCourses[it]
            MekikCard(
                caption = course?.author.orEmpty(),
                title = course?.title.orEmpty(),
                popular = course?.popular ?: false,
                painter = course?.cover.orEmpty()
            ) {
                onAction(
                    UiAction.OnCourseClick(course?.id ?: 0)
                )
            }
        }
        allCourses.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
    if (showFilterSheet) {
        FilterSheet (onDismiss = {
            showFilterSheet = false
        }, onSubmit = { pair ->
            showFilterSheet = false
            viewModel.refreshCourses(pair.first, pair.second)
        })
    }
}

@Preview
@Composable
fun PreviewCoursesScreen() {
    CoursesScreen {

    }
}