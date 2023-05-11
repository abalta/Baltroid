package com.baltroid.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R
import com.baltroid.ui.common.IconWithTextBelow
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.conditional

@Composable
fun HitReadsSideBar(
    modifier: Modifier = Modifier,
    numberOfViews: Int,
    numberOfComments: Int,
    hasSmallHeight: Boolean,
    isCommentsSelected: Boolean,
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
                onCommentsClick = onCommentsClick,
                hasSmallHeight = hasSmallHeight, modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            )
            SideBarBottomSection(
                addComment = addComment,
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
            .width(MaterialTheme.localDimens.dp0_5)
    )
}

@Composable
fun SideBarHorizontalDivider() {
    Divider(
        color = MaterialTheme.localColors.white_alpha05,
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.localDimens.dp0_5)
    )
}

@Composable
fun SideBarTopSection(
    modifier: Modifier = Modifier,
    numberOfViews: Int,
    numberOfComments: Int,
    hasSmallHeight: Boolean,
    isCommentsSelected: Boolean,
    onCommentsClick: () -> Unit,
    onDotsClick: () -> Unit
) {

    val localColors = MaterialTheme.localColors
    val localDimens = MaterialTheme.localDimens

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(IntrinsicSize.Min)
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
        if (!hasSmallHeight) {
            SideBarHorizontalDivider()
            IconWithTextBelow(
                iconResId = R.drawable.ic_eye,
                text = numberOfViews.toString(),
                textStyle = MaterialTheme.localTextStyles.sideBarIconText,
                spacedBy = MaterialTheme.localDimens.dp10,
                modifier = Modifier.padding(
                    vertical = MaterialTheme.localDimens.dp12,
                    horizontal = MaterialTheme.localDimens.dp8
                )
            )
            SideBarHorizontalDivider()
            SimpleIcon(
                iconResId = R.drawable.ic_hashtag,
                modifier = Modifier.padding(
                    vertical = MaterialTheme.localDimens.dp12,
                    horizontal = MaterialTheme.localDimens.dp8
                )
            )
        }
        SideBarHorizontalDivider()
        IconWithTextBelow(
            iconResId = R.drawable.ic_comment,
            text = numberOfComments.toString(),
            spacedBy = MaterialTheme.localDimens.dp3,
            textStyle = MaterialTheme.localTextStyles.sideBarIconText,
            modifier = Modifier
                .fillMaxWidth()
                .conditional(isCommentsSelected) {
                    drawBehind {
                        drawRoundRect(
                            color = localColors.orange,
                            size = Size(
                                width = localDimens.dp3.toPx(),
                                height = size.height
                            )
                        )
                    }
                }
                .padding(
                    vertical = MaterialTheme.localDimens.dp12,
                    horizontal = MaterialTheme.localDimens.dp8
                )
                .clickable { onCommentsClick.invoke() }
        )
        SideBarHorizontalDivider()
    }
}

@Composable
fun SideBarBottomSection(
    addComment: () -> Unit,
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
            spacedBy = MaterialTheme.localDimens.dp4,
            modifier = Modifier.padding(
                vertical = MaterialTheme.localDimens.dp12,
                horizontal = MaterialTheme.localDimens.dp8
            )
        )
        SideBarHorizontalDivider()
        SimpleIcon(
            iconResId = R.drawable.ic_share,
            modifier = Modifier.padding(
                vertical = MaterialTheme.localDimens.dp12,
                horizontal = MaterialTheme.localDimens.dp8
            )
        )
        SideBarHorizontalDivider()
        SimpleIcon(
            iconResId = R.drawable.ic_add_comment,
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.localDimens.dp12,
                    horizontal = MaterialTheme.localDimens.dp8
                )
                .clickable { addComment.invoke() }
        )
        SideBarHorizontalDivider()
        SimpleIcon(
            iconResId = R.drawable.ic_banner_filled,
            tint = MaterialTheme.localColors.purple,
            modifier = Modifier.padding(
                vertical = MaterialTheme.localDimens.dp12,
                horizontal = MaterialTheme.localDimens.dp8
            )
        )
    }
}

@Preview
@Composable
fun HitReadsSideBarPreview() {
    HitReadsSideBar(
        numberOfViews = 12,
        numberOfComments = 12,
        hasSmallHeight = false,
        onCommentsClick = {},
        isCommentsSelected = true,
        onDotsClick = {},
        addComment = {}
    )
}