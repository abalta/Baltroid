package com.baltroid.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.apps.R

@Composable
fun AuthorScreen(
    onBackClick: () -> Unit
) {
    AuthorScreenContent(
        scrollState = rememberScrollState(),
        onBackClick = onBackClick
    )
}

@Composable
fun AuthorScreenContent(
    scrollState: ScrollState,
    onBackClick: () -> Unit,
) {
    var selectedTab: AuthorScreenTabs by rememberSaveable {
        mutableStateOf(AuthorScreenTabs.Stories)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .verticalScroll(scrollState)
            .systemBarsPadding()
    ) {
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp36))
        IconlessMenuBar(
            title = "ZEYNEP SEY",
            onBackClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp22))
        NamelessAuthorItem(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp44))
        StoryAndCommentTabs(
            selectedTab = selectedTab,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp54))
        ) { tabToSelect ->
            selectedTab = tabToSelect
        }
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp34))
        LazyRow(
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp55)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp30))
        ) {

        }
    }
}

@Composable
fun StoryAndCommentTabs(
    selectedTab: AuthorScreenTabs,
    modifier: Modifier = Modifier,
    onTabSelected: (AuthorScreenTabs) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp33))
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .clickable {
                    onTabSelected.invoke(AuthorScreenTabs.Stories)
                }
        ) {
            Text(
                text = stringResource(id = R.string.stories),
                style = MaterialTheme.localTextStyles.menuBarSubTitle,
                color = MaterialTheme.localColors.white_alpha09,
                modifier = Modifier.width(IntrinsicSize.Max)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(
                        if (selectedTab == AuthorScreenTabs.Stories) {
                            MaterialTheme.localColors.white_alpha07
                        } else {
                            MaterialTheme.localColors.transparent
                        }
                    )
            )
        }
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .clickable {
                    onTabSelected.invoke(AuthorScreenTabs.Comments)
                }
        ) {
            Text(
                text = stringResource(id = R.string.comments),
                style = MaterialTheme.localTextStyles.menuBarSubTitle,
                color = MaterialTheme.localColors.white_alpha09,
                modifier = Modifier.width(IntrinsicSize.Max)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(
                        if (selectedTab == AuthorScreenTabs.Comments) {
                            MaterialTheme.localColors.white_alpha07
                        } else {
                            MaterialTheme.localColors.transparent
                        }
                    )
            )
        }
    }
}

@Composable
fun AuthorComments(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp23)),
        modifier = modifier
    ) {
        repeat(5) {

            AuthorCommentItem(
                owner = "ZEYNEP SEY",
                date = "04/01/2023 20:29",
                isLiked = false
            )

        }
    }
}

@Composable
fun AuthorCommentItem(
    owner: String,
    date: String,
    isLiked: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            CroppedImage(
                imgResId = R.drawable.woods_image,
                modifier
                    .size(dimensionResource(id = R.dimen.dp48))
                    .clip(MaterialTheme.localShapes.circleShape)
            )
            HorizontalSpacer(width = dimensionResource(id = R.dimen.dp13))
            Column(
                verticalArrangement = Arrangement.Bottom,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = owner,
                        style = MaterialTheme.localTextStyles.subtitle,
                        color = MaterialTheme.localColors.white
                    )
                    SimpleIcon(
                        iconResId = R.drawable.ic_star,
                        tint = MaterialTheme.localColors.yellow,
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.dp1))
                            .size(dimensionResource(id = R.dimen.dp15))
                            .align(Alignment.Top)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = date,
                        style = MaterialTheme.localTextStyles.dateText,
                        color = MaterialTheme.localColors.white_alpha07,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp14)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                    ) {
                        SimpleIcon(iconResId = if (isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart_outlined)
                        SimpleIcon(iconResId = R.drawable.ic_chat_outlined)
                        SimpleIcon(iconResId = R.drawable.ic_menu_horizontal)
                    }
                }
            }
        }
        VerticalSpacer(height = 6.dp)
        Text(
            text = "First of all please publish this so I can buy it for my library! second #KGD is my best book",
            style = MaterialTheme.localTextStyles.body,
            color = MaterialTheme.localColors.white_alpha08,
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(start = 5.dp)
        )
    }
}

@Preview
@Composable
fun AuthorScreenPreview() {
    AuthorScreen {}
}

enum class AuthorScreenTabs {
    Stories,
    Comments
}