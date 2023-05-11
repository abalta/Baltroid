package com.baltroid.ui.screens.menu.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R
import com.baltroid.ui.common.CroppedImage
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun CommentsScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .systemBarsPadding()
    ) {
        VerticalSpacer(height = MaterialTheme.localDimens.dp36)
        MenuBar(
            title = stringResource(id = R.string.comments),
            iconResId = R.drawable.ic_comment
        ) {}
        VerticalSpacer(height = MaterialTheme.localDimens.dp24)
        CommentsScreenTabs(
            modifier = Modifier
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp33)
        Comments()
    }
}

@Composable
private fun Comments() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp29),
        contentPadding = PaddingValues(horizontal = MaterialTheme.localDimens.dp35)
    ) {
        item { CommentItem(title = "KİMSE GERÇEK DEĞİL", numberOfComments = 220) }
        item { CommentItem(title = "KİMSE GERÇEK DEĞİL", numberOfComments = 220) }
        item { CommentItem(title = "KİMSE GERÇEK DEĞİL", numberOfComments = 220) }
    }
}

@Composable
fun CommentsScreenTabs(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp21)
    ) {
        CommentsScreenTabItem(
            tabTitle = stringResource(id = R.string.all_comments),
            isSelected = true
        )
        CommentsScreenTabItem(
            tabTitle = stringResource(id = R.string.my_favorite_comments),
            isSelected = false
        )
        CommentsScreenTabItem(
            tabTitle = stringResource(id = R.string.my_comments),
            isSelected = false
        )
    }
}

@Composable
fun CommentsScreenTabItem(
    tabTitle: String,
    isSelected: Boolean
) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = tabTitle,
            style = MaterialTheme.localTextStyles.commentsScreenTabText,
            modifier = Modifier.width(IntrinsicSize.Max)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.localDimens.dp3)
                .background(
                    if (isSelected) MaterialTheme.localColors.white_alpha07
                    else MaterialTheme.localColors.transparent
                )
        )
    }
}

@Composable
private fun CommentItem(
    title: String,
    numberOfComments: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CroppedImage(
            imgResId = R.drawable.woods_image,
            modifier = Modifier
                .size(
                    width = MaterialTheme.localDimens.dp127,
                    height = MaterialTheme.localDimens.dp177
                )
                .clip(MaterialTheme.localShapes.roundedDp18)
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp13)
        Text(text = title, style = MaterialTheme.localTextStyles.storyItemTitle)
        VerticalSpacer(height = MaterialTheme.localDimens.dp10)
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp3),
            modifier = Modifier
                .clip(MaterialTheme.localShapes.roundedDp2)
                .border(
                    width = MaterialTheme.localDimens.dp0_5,
                    color = MaterialTheme.localColors.white_alpha07,
                    shape = MaterialTheme.localShapes.roundedDp2
                )
                .padding(
                    vertical = MaterialTheme.localDimens.dp6_5,
                    horizontal = MaterialTheme.localDimens.dp20
                )
        ) {
            SimpleIcon(
                iconResId = R.drawable.ic_comment,
                tint = MaterialTheme.localColors.white_alpha04
            )
            Text(
                text = numberOfComments.toString(),
                style = MaterialTheme.localTextStyles.episodeSectionIconText
            )
        }
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun CommentScreenPreview() {
    CommentsScreen()
}