package com.baltroid.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.apps.R
import com.baltroid.util.conditional

@Composable
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
                textStyle = MaterialTheme.localTextStyles.sideBarIconText,
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
                    style = MaterialTheme.localTextStyles.mediumTitle,
                    color = MaterialTheme.localColors.white
                )
            }
        }
        SideBarHorizontalDivider()
        IconWithTextBelow(
            iconResId = R.drawable.ic_comment,
            text = numberOfComments.toString(),
            spacedBy = dimensionResource(id = R.dimen.dp3),
            textStyle = MaterialTheme.localTextStyles.sideBarIconText,
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
            textStyle = MaterialTheme.localTextStyles.sideBarIconText,
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
}

@Composable
fun HitReadsSideBar(
    numberOfComments: Int,
    modifier: Modifier = Modifier,
    onVisibilityChange: () -> Unit,
    onShowComments: () -> Unit,
    onCreateComment: () -> Unit,
    onShowEpisodes: () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(dimensionResource(id = R.dimen.dp1)),
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
                        onVisibilityChange.invoke()
                    }
            )
            Divider(
                thickness = dimensionResource(id = R.dimen.dp1),
                color = MaterialTheme.localColors.white
            )
            IconWithTextBelow(
                iconResId = R.drawable.ic_comment,
                text = numberOfComments.toString(),
                textStyle = MaterialTheme.localTextStyles.sideBarIconText,
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
                thickness = dimensionResource(id = R.dimen.dp1),
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
                thickness = dimensionResource(id = R.dimen.dp1),
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
                thickness = dimensionResource(id = R.dimen.dp1),
                color = MaterialTheme.localColors.white
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun HitReadsSideBarPreview() {
    HitReadsSideBar(
        12,
        onVisibilityChange = { /*TODO*/ },
        onShowComments = { /*TODO*/ },
        onCreateComment = { /*TODO*/ }) {

    }
}