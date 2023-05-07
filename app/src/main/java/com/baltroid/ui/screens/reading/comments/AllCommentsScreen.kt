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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.SideBarHorizontalDivider
import com.baltroid.ui.components.SideBarVerticalDivider
import com.baltroid.ui.screens.reading.CommentSection
import com.baltroid.ui.screens.reading.HashTagSection
import com.baltroid.ui.screens.reading.HitReadsPageHeader
import com.baltroid.ui.screens.reading.ScrollBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localTextStyles

@Composable
fun AllCommentsScreen(
    openMenuScreen: () -> Unit
) {
    val scrollState = rememberScrollState()
    val lazyListState = rememberLazyListState()

    var sideBarVisibility by remember {
        mutableStateOf(true)
    }
    var bottomBarVisibility by remember {
        mutableStateOf(true)
    }
    var selectedTab by remember {
        mutableStateOf(CommentsTabState.AllComments)
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
                onMenuClick = { },
            )
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    VerticalSpacer(height = MaterialTheme.localDimens.dp12)
                    Text(
                        text = "YORUMLAR",
                        style = MaterialTheme.localTextStyles.title,
                        modifier = Modifier.padding(
                            start = MaterialTheme.localDimens.dp31,
                            end = MaterialTheme.localDimens.dp15
                        )
                    )
                    VerticalSpacer(height = MaterialTheme.localDimens.dp11_5)
                    Row {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(MaterialTheme.localDimens.dp31)
                        ) {
                            ScrollBar(
                                scrollState = scrollState,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }

                        CommentSection(
                            lazyListState = lazyListState,
                            tabState = selectedTab,
                            modifier = Modifier.padding(end = MaterialTheme.localDimens.dp8)
                        ) { newTab ->
                            selectedTab = newTab
                        }
                    }
                }
                AnimatedVisibility(visible = sideBarVisibility) {
                    AllCommentsScreenSideBar {
                        sideBarVisibility = !sideBarVisibility
                    }
                }
            }
            VerticalSpacer(height = MaterialTheme.localDimens.dp15)
            AnimatedVisibility(visible = bottomBarVisibility) {
                HashTagSection(
                    paddingValues = PaddingValues(start = MaterialTheme.localDimens.dp31),
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
        SideBarVerticalDivider()
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
                            vertical = MaterialTheme.localDimens.dp12,
                            horizontal = MaterialTheme.localDimens.dp8
                        )
                        .clickable { onDotsClick.invoke() }
                )
                SideBarHorizontalDivider()
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)

            ) {
                SideBarHorizontalDivider()
                VerticalSpacer(height = MaterialTheme.localDimens.dp12)
                SimpleIcon(iconResId = R.drawable.ic_add_comment)
                VerticalSpacer(height = MaterialTheme.localDimens.dp12)
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