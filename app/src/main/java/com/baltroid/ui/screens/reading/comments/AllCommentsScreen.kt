package com.baltroid.ui.screens.reading.comments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.R
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.screens.menu.comments.CommentViewModel
import com.baltroid.ui.screens.reading.CommentSection
import com.baltroid.ui.screens.reading.HashTagSection
import com.baltroid.ui.screens.reading.HitReadsPageHeader
import com.baltroid.ui.screens.reading.ScrollBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localTextStyles

@Composable
fun AllCommentsScreen(
    openMenuScreen: () -> Unit
) {
    val scrollState = rememberScrollState()
    val lazyListState = rememberLazyListState()
    val viewModel: CommentViewModel = hiltViewModel()
    val comments = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {

    }

    var sideBarVisibility by remember {
        mutableStateOf(true)
    }
    var bottomBarVisibility by remember {
        mutableStateOf(true)
    }

    BoxWithConstraints {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.localColors.black)
                .navigationBarsPadding()
        ) {
            HitReadsPageHeader(
                numberOfNotification = 12,
                imgUrl = "",
                onMenuClick = { },
                onIconCLicked = {},
            )
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    VerticalSpacer(height = dimensionResource(id = R.dimen.dp12))
                    Text(
                        text = stringResource(id = R.string.comments),
                        style = MaterialTheme.localTextStyles.poppins17Light,
                        color = MaterialTheme.localColors.white,
                        modifier = Modifier.padding(
                            start = dimensionResource(id = R.dimen.dp31),
                            end = dimensionResource(id = R.dimen.dp15)
                        )
                    )
                    VerticalSpacer(height = dimensionResource(id = R.dimen.dp12))
                    Row {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(dimensionResource(id = R.dimen.dp31))
                        ) {
                            ScrollBar(
                                scrollState = scrollState,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }

                        CommentSection(
                            lazyListState = lazyListState,
                            comments = comments.commentList,
                            modifier = Modifier.padding(end = 8.dp),
                            onLikeClick = { _, _ -> },
                            onReplyClick = {},
                            onExpanseClicked = {
                                viewModel.expanseComment(it)
                            }
                        )
                    }
                }
                AnimatedVisibility(visible = sideBarVisibility) {
                    AllCommentsScreenSideBar {
                        sideBarVisibility = !sideBarVisibility
                    }
                }
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp15))
            AnimatedVisibility(visible = bottomBarVisibility) {
                HashTagSection(
                    paddingValues = PaddingValues(start = dimensionResource(id = R.dimen.dp31)),
                    modifier = Modifier.padding(bottom = this@BoxWithConstraints.maxHeight * .06f)
                )
            }
        }
    }
}

@Composable
fun AllCommentsScreenSideBar(
    modifier: Modifier = Modifier,
    onDotsClick: () -> Unit
) {
    Row(
        modifier = modifier.width(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Min)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                SimpleIcon(
                    iconResId = R.drawable.ic_menu,
                    modifier = Modifier
                        .padding(
                            vertical = dimensionResource(id = R.dimen.dp12),
                            horizontal = 8.dp
                        )
                        .clickable { onDotsClick.invoke() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)

            ) {
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp12))
                SimpleIcon(iconResId = R.drawable.ic_add_comment)
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp12))
            }
        }
    }
}

enum class CommentsTabState {
    AllComments,
    MyFavorites,
    MyComments
}

@Preview
@Composable
fun AllCommentsScreenPreview() {
    AllCommentsScreen {}
}