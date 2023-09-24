package com.baltroid.ui.screens.menu.author

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.CroppedImage
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.SetLoadingState
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.IconlessMenuBar
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.screens.menu.favorites.NamelessAuthorItem
import com.baltroid.ui.screens.menu.favorites.YellowStarBox
import com.baltroid.ui.screens.reading.CommentItem
import com.baltroid.ui.screens.viewmodels.AuthorScreenUiState
import com.baltroid.ui.screens.viewmodels.AuthorViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.hitreads.core.model.IndexOriginal

@Composable
fun AuthorScreen(
    id: Int,
    viewModel: AuthorViewModel = hiltViewModel(),
    navigate: (String, Int?) -> Unit,
    onBackClick: () -> Unit
) {

    val state by viewModel.author.collectAsStateWithLifecycle()

    SetLoadingState(isLoading = state.isLoading)

    LaunchedEffect(Unit) {
        viewModel.showAuthor(id)
    }

    AuthorScreenContent(
        state = state,
        onBackClick = onBackClick,
        navigate = navigate,
        onLikeClick = { isLiked, id ->
            if (isLiked) viewModel.unlikeComment(id)
            else viewModel.likeComment(id)
        }
    )
}

@Composable
fun AuthorScreenContent(
    state: AuthorScreenUiState,
    navigate: (String, Int?) -> Unit,
    onBackClick: () -> Unit,
    onLikeClick: (Boolean, Int) -> Unit
) {
    var selectedTab: AuthorScreenTabs by rememberSaveable {
        mutableStateOf(AuthorScreenTabs.Stories)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .systemBarsPadding()
    ) {
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp36))
        IconlessMenuBar(
            title = state.author?.authorName.toString(),
            onBackClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp22))
        NamelessAuthorItem(
            imgUrl = state.author?.image,
            isFavorite = state.author?.isFavorite == true,
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
        when (selectedTab) {
            AuthorScreenTabs.Stories -> {
                LazyRow(
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp55)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp30))
                ) {
                    items(state.author?.originals.orEmpty()) {
                        OriginalsItem(
                            original = it,
                            onClick = {
                                navigate.invoke(HitReadsScreens.HomeDetailScreen.route, it.id)
                            },
                            removeFavoriteOriginal = {}
                        )
                    }
                }
            }

            AuthorScreenTabs.Comments -> {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.dp50))
                ) {
                    items(state.author?.comments.orEmpty()) { comment ->
                        CommentItem(
                            model = comment,
                            replySize = comment.repliesCount,
                            isChatSelected = false,
                            isSeeAllEnabled = false,
                            hideAllEnabled = false,
                            onHideClicked = {/* no-op */ },
                            disableCommentButtons = true,
                            onExpanseClicked = { /* no-op */
                            },
                            onLikeClick = onLikeClick,
                            onReplyClick = { /* no-op */

                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OriginalsItem(
    original: IndexOriginal,
    onClick: () -> Unit,
    removeFavoriteOriginal: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        AsyncImage(
            model = original.cover,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.hitreads_placeholder),
            modifier = Modifier
                .size(
                    dimensionResource(id = R.dimen.dp127),
                    dimensionResource(id = R.dimen.dp177)
                )
                .clip(MaterialTheme.localShapes.roundedDp18)
                .clickable(onClick = onClick)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp13))
        Text(
            text = original.title.toString(),
            style = MaterialTheme.localTextStyles.poppins12Medium,
            color = MaterialTheme.localColors.white_alpha05,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp11))
        YellowStarBox(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = removeFavoriteOriginal
        )
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
                style = MaterialTheme.localTextStyles.spaceGrotesk20Medium,
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
                style = MaterialTheme.localTextStyles.spaceGrotesk20Medium,
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
                        style = MaterialTheme.localTextStyles.poppins13Medium,
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
                        style = MaterialTheme.localTextStyles.poppins10Regular,
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
            style = MaterialTheme.localTextStyles.poppins14Regular,
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
}

enum class AuthorScreenTabs {
    Stories,
    Comments
}