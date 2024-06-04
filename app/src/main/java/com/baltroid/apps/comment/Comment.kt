package com.baltroid.apps.comment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baltroid.apps.course.CourseDetailViewModel
import com.baltroid.apps.ext.collectAsStateLifecycleAware
import com.baltroid.apps.home.hideSheetAndUpdateState
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.CommentCard
import com.baltroid.designsystem.component.CommentTextField
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikOutlinedButton
import com.baltroid.designsystem.component.RatingBarCard
import com.baltroid.designsystem.component.RatingCard
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableIntStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.baltroid.designsystem.component.MekikFilledButton
import de.palm.composestateevents.EventEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsSheet(
    viewModel: CourseDetailViewModel,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        true
    )
    var showMakeCommentSheet by rememberSaveable { mutableStateOf(false) }
    val sheetStateScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val uiState by viewModel.courseDetailState.collectAsStateLifecycleAware()

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        dragHandle = null,
        sheetState = sheetState,
        containerColor = Color.White,
        onDismissRequest = {
            onDismiss()
        }) {
        Box(modifier = Modifier.padding(bottom = 48.dp)) {
            Scaffold(
                containerColor = Color.White,
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.padding(bottom = 48.dp)
                    )
                }
            ) { padding ->
                LazyColumn(modifier = Modifier.padding(padding)) {
                    item {
                        IconButton(enabled = uiState.isLoading.not(), onClick = {
                            sheetStateScope.hideSheetAndUpdateState(sheetState) {
                                onDismiss()
                            }
                        }, modifier = Modifier.padding(top = 16.dp, start = 24.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "close"
                            )
                        }
                        FilterHeadText(
                            text = stringResource(id = R.string.title_comments),
                            showIcon = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 22.dp, top = 24.dp)
                        )
                        RatingCard(
                            rating = uiState.courseDetail?.ratingAvg.toString(),
                            ratingPercent = uiState.courseDetail?.ratings.orEmpty()
                        )
                    }
                    items(key = {
                        it.id
                    }, items = uiState.courseDetail?.comments.orEmpty()) {
                        CommentCard(
                            name = it.user.name,
                            rating = it.rating.toString(),
                            comment = it.comment,
                            avatar = it.user.avatar
                        )
                    }
                    item {
                        MekikOutlinedButton(
                            text = stringResource(id = R.string.title_do_comment),
                            modifier = Modifier.padding(top = 24.dp, start = 11.dp, end = 11.dp)
                        ) {
                            showMakeCommentSheet = true
                        }
                    }
                }
            }
        }
        if (showMakeCommentSheet) {
            MakeCommentSheet(uiState.courseDetail?.id ?: 0,
                onDismiss = {
                    showMakeCommentSheet = false
                    viewModel.getCourseDetail()
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeCommentSheet(
    courseId: Int,
    viewModel: CommentViewModel = hiltViewModel(),
    onDismiss: (Boolean) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        true
    )
    val sheetStateScope = rememberCoroutineScope()

    val uiState by viewModel.commentState.collectAsStateLifecycleAware()

    var rating by remember { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }

    EventEffect(
        event = uiState.success,
        onConsumed = viewModel::addCommentSucceededEvent
    ) {
        sheetStateScope.hideSheetAndUpdateState(sheetState) {
            onDismiss(true)
        }
    }

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        dragHandle = null,
        sheetState = sheetState,
        containerColor = Color.White,
        onDismissRequest = {
            onDismiss(false)
        }) {
        IconButton(onClick = {
            sheetStateScope.hideSheetAndUpdateState(sheetState) {
                onDismiss(false)
            }
        }, modifier = Modifier.padding(top = 16.dp, start = 24.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "close"
            )
        }
        FilterHeadText(
            text = stringResource(id = R.string.title_do_comment),
            showIcon = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, top = 24.dp)
        )
        RatingBarCard(modifier = Modifier.padding(top = 16.dp), onRatingSelect =  {
            rating = it.toInt()
        })
        CommentTextField {
            comment = it
        }
        Spacer(modifier = Modifier.weight(1f))
        MekikFilledButton(text = "Gönder", modifier = Modifier.padding(top = 16.dp, start = 11.dp, end = 11.dp, bottom = 48.dp)) {
            if (rating == 0) {
                return@MekikFilledButton
            } else if (comment.isEmpty()) {
                return@MekikFilledButton
            } else {
                viewModel.addComment(
                    id = courseId,
                    comment = comment,
                    rating = rating
                )
            }

        }
    }
}