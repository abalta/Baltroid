package com.baltroid.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.apps.R
import com.baltroid.ui.common.IconWithTextBelow
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.conditional

@Composable
fun HitReadsSideBar(
    numberOfComments: Int,
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    isFullHeight: Boolean,
    onVisibilityChange: (Boolean) -> Unit,
    onShowComments: () -> Unit,
    onCreateComment: () -> Unit,
    onShowEpisodes: () -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp * 2
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            animationSpec = tween(100, easing = LinearEasing)
        ) { it / 2 },
        exit = slideOutHorizontally(
            animationSpec = tween(100, easing = LinearEasing)
        ) { it / 2 }
    ) {
        Row(
            modifier = modifier
        ) {
            Divider(
                modifier = Modifier
                    .conditional(isFullHeight) {
                        fillMaxHeight()
                    }
                    .conditional(!isFullHeight) {
                        height(screenHeight)
                    }
                    .width(dimensionResource(id = R.dimen.dp0_5)),
                color = MaterialTheme.localColors.white
            )
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                SimpleIcon(
                    iconResId = R.drawable.ic_menu,
                    Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.dp12),
                            bottom = dimensionResource(id = R.dimen.dp11),
                            top = dimensionResource(id = R.dimen.dp11)
                        )
                        .clickable {
                            onVisibilityChange.invoke(!isVisible)
                        }
                )
                Divider(
                    thickness = dimensionResource(id = R.dimen.dp0_5),
                    color = MaterialTheme.localColors.white
                )
                IconWithTextBelow(
                    iconResId = R.drawable.ic_comment,
                    text = numberOfComments.toString(),
                    textStyle = MaterialTheme.localTextStyles.poppins10Regular,
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.dp12),
                            bottom = dimensionResource(id = R.dimen.dp9),
                            top = dimensionResource(id = R.dimen.dp9)
                        )
                        .clickable {
                            onShowComments.invoke()
                        }
                )
                Divider(
                    thickness = dimensionResource(id = R.dimen.dp0_5),
                    color = MaterialTheme.localColors.white
                )
                SimpleIcon(
                    iconResId = R.drawable.ic_add_comment,
                    Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.dp12),
                            bottom = dimensionResource(id = R.dimen.dp22),
                            top = dimensionResource(id = R.dimen.dp22)
                        )
                        .clickable {
                            onCreateComment.invoke()
                        }
                )
                Divider(
                    thickness = dimensionResource(id = R.dimen.dp0_5),
                    color = MaterialTheme.localColors.white
                )
                SimpleIcon(
                    iconResId = R.drawable.ic_filter,
                    Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.dp12),
                            bottom = dimensionResource(id = R.dimen.dp22),
                            top = dimensionResource(id = R.dimen.dp22)
                        )
                        .clickable {
                            onShowEpisodes.invoke()
                        }
                )
                Divider(
                    thickness = dimensionResource(id = R.dimen.dp0_5),
                    color = MaterialTheme.localColors.white
                )
            }
        }
    }
}

/*@Composable
fun HitReadsSideBar(
    modifier: Modifier = Modifier,
    numberOfViews: Int,
    numberOfComments: Int,
    hashTag: String,
    isMarked: Boolean,
    hasSmallHeight: Boolean,
    isCommentsSelected: Boolean,
    onShareClicked: () -> Unit,
    onMarkClicked: (Boolean) -> Unit,
    onDotsClick: () -> Unit,
    onCommentsClick: () -> Unit,
    addComment: () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        SideBarVerticalDivider()
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Min)
        ) {
            SideBarTopSection(
                numberOfViews = numberOfViews,
                numberOfComments = numberOfComments,
                isCommentsSelected = isCommentsSelected,
                onDotsClick = onDotsClick,
                hashTag = hashTag,
                onCommentsClick = onCommentsClick,
                onShareClicked = onShareClicked,
                hasSmallHeight = hasSmallHeight, modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            )
            SideBarBottomSection(
                addComment = addComment,
                isMarked = isMarked,
                onShareClicked,
                onMarkClicked,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun SideBarVerticalDivider() {
    Divider(
        color = MaterialTheme.localColors.white_alpha05,
        modifier = Modifier
            .fillMaxHeight()
            .width(dimensionResource(id = R.dimen.dp1))
    )
}

@Composable
fun SideBarHorizontalDivider() {
    Divider(
        color = MaterialTheme.localColors.white_alpha05,
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dp1))
    )
}

@Composable
fun SideBarTopSection(
    modifier: Modifier = Modifier,
    numberOfViews: Int,
    hashTag: String,
    numberOfComments: Int,
    hasSmallHeight: Boolean,
    isCommentsSelected: Boolean,
    onShareClicked: () -> Unit,
    onCommentsClick: () -> Unit,
    onDotsClick: () -> Unit
) {

    val localColors = MaterialTheme.localColors

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(IntrinsicSize.Min)
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
        if (!hasSmallHeight) {
            SideBarHorizontalDivider()
            IconWithTextBelow(
                iconResId = R.drawable.ic_eye,
                text = numberOfViews.toString(),
                textStyle = MaterialTheme.localTextStyles.poppins10Regular,
                spacedBy = dimensionResource(id = R.dimen.dp10),
                modifier = Modifier.padding(
                    vertical = dimensionResource(id = R.dimen.dp12),
                    horizontal = 8.dp
                )
            )
            SideBarHorizontalDivider()
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.height(64.dp)
            ) {
                Text(
                    text = hashTag,
                    modifier = Modifier.rotate(-90f),
                    style = MaterialTheme.localTextStyles.poppins17Medium,
                    color = MaterialTheme.localColors.white
                )
            }
        }
        SideBarHorizontalDivider()
        IconWithTextBelow(
            iconResId = R.drawable.ic_comment,
            text = numberOfComments.toString(),
            spacedBy = dimensionResource(id = R.dimen.dp3),
            textStyle = MaterialTheme.localTextStyles.poppins10Regular,
            modifier = Modifier
                .fillMaxWidth()
                .conditional(isCommentsSelected) {
                    drawBehind {
                        drawRoundRect(
                            color = localColors.orange,
                            size = Size(
                                width = 3.dp.toPx(),
                                height = size.height
                            )
                        )
                    }
                }
                .padding(
                    vertical = dimensionResource(id = R.dimen.dp12),
                    horizontal = 8.dp
                )
                .clickable { onCommentsClick.invoke() }
        )
        SideBarHorizontalDivider()
    }
}

@Composable
fun SideBarBottomSection(
    addComment: () -> Unit,
    isMarked: Boolean,
    onShareClicked: () -> Unit,
    onMarkClicked: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(IntrinsicSize.Min)
    ) {
        SideBarHorizontalDivider()
        IconWithTextBelow(
            iconResId = R.drawable.ic_rectangle_filled,
            text = "0",
            textStyle = MaterialTheme.localTextStyles.poppins10Regular,
            spacedBy = dimensionResource(id = R.dimen.dp4),
            modifier = Modifier.padding(
                vertical = dimensionResource(id = R.dimen.dp12),
                horizontal = 8.dp
            )
        )
        SideBarHorizontalDivider()
        SimpleIcon(
            iconResId = R.drawable.ic_share,
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.dp12),
                    horizontal = 8.dp
                )
                .clickable {
                    onShareClicked.invoke()
                }
        )
        SideBarHorizontalDivider()
        SimpleIcon(
            iconResId = R.drawable.ic_add_comment,
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.dp12),
                    horizontal = 8.dp
                )
                .clickable { addComment.invoke() }
        )
        SideBarHorizontalDivider()
        SimpleIcon(
            iconResId = if (isMarked) R.drawable.ic_banner_filled else R.drawable.ic_banner_outlined,
            tint = if (isMarked) MaterialTheme.localColors.purple else Color.Unspecified,
            modifier = Modifier
                .clickable { onMarkClicked.invoke(isMarked) }
                .padding(
                    vertical = dimensionResource(id = R.dimen.dp12),
                    horizontal = 8.dp
                )
        )
    }
}*/

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun HitReadsSideBarPreview() {
    HitReadsSideBar(
        12,
        isVisible = true,
        isFullHeight = false,
        onVisibilityChange = { /*TODO*/ },
        onShowComments = { /*TODO*/ },
        onCreateComment = { /*TODO*/ }) {

    }
}