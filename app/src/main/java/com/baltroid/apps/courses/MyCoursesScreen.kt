package com.baltroid.apps.courses

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.baltroid.apps.auth.LoginSheet
import com.baltroid.apps.ext.collectAsStateLifecycleAware
import com.baltroid.apps.navigation.OnAction
import com.baltroid.apps.navigation.UiAction
import com.baltroid.core.common.ErrorModel
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.ErrorCard
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikCard
import de.palm.composestateevents.EventEffect

@Composable
fun MyCoursesScreen(
    viewModel: MyCoursesViewModel = hiltViewModel(),
    onAction: OnAction
) {
    val uiState by viewModel.courseState.collectAsStateLifecycleAware()
    var error: ErrorModel? by remember { mutableStateOf(null) }
    var showLoginSheet by rememberSaveable { mutableStateOf(false) }

    EventEffect(event = uiState.error, onConsumed = viewModel::onConsumedFailedEvent) {
        error = it
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp, bottom = 64.dp), contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            FilterHeadText(
                text = stringResource(id = R.string.title_my_courses), modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp)
            )
        }
        uiState.courses?.let {
            showLoginSheet = false
            if (it.isNotEmpty()) {
                items(key = { courseModel ->
                    courseModel.id
                }, items = it) { course ->
                    MekikCard(
                        caption = course.author,
                        title = course.title,
                        popular = course.popular,
                        painter = course.cover
                    ) {
                        onAction(
                            UiAction.OnCourseClick(course.id)
                        )
                    }
                }
            } else {
                item {
                    ErrorCard(
                        message = "Eğitiminiz bulunmamaktadır.",
                        buttonText = "Eğitimleri İncele"
                    ) {
                        onAction(UiAction.OnAllCoursesClick)
                    }
                }
            }
        }
        error?.let {
            item {
                if (error?.code == 401) {
                    ErrorCard(
                        message = stringResource(id = R.string.error_unauthorized),
                        buttonText = stringResource(
                            id = R.string.text_btn_login
                        )
                    ) {
                        showLoginSheet = true
                    }
                }
            }
        }
    }
    if (showLoginSheet) {
        LoginSheet(onDismiss = {
            showLoginSheet = false
        }, checkLogin = {
            viewModel.getMyCourses()
        })
    }
}

@Preview
@Composable
fun PreviewMyCoursesScreen() {
    MyCoursesScreen {

    }
}