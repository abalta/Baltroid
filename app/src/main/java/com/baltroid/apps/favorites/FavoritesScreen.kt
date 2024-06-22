package com.baltroid.apps.favorites

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
fun FavoritesScreen(
    viewModel: FavoriteViewModel = hiltViewModel(), onAction: OnAction
) {

    val uiState by viewModel.favoriteState.collectAsStateLifecycleAware()
    var error: ErrorModel? by remember { mutableStateOf(null) }
    var showLoginSheet by rememberSaveable { mutableStateOf(false) }

    EventEffect(event = uiState.error, onConsumed = viewModel::onConsumedFailedEvent) {
        error = it
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp, bottom = 64.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            FilterHeadText(
                text = stringResource(id = R.string.title_favorites),
                showIcon = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp)
            )
        }
        uiState.favorites?.let { course ->
            showLoginSheet = false
            if (course.isNotEmpty()) {
                items(key = {
                    it.id
                }, items = course) { favorite ->
                    MekikCard(
                        caption = favorite.author,
                        title = favorite.title,
                        painter = favorite.cover,
                        popular = favorite.popular
                    ) {
                        onAction(UiAction.OnCourseClick(favorite.id))
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
            viewModel.getFavorites()
        })
    }
}