package com.baltroid.ui.screens.menu.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.SetLoadingState
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.CommentWritingCard
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.screens.reading.CommentSection
import com.baltroid.ui.screens.reading.CommentSectionTabs
import com.baltroid.ui.screens.reading.comments.CommentsTabState
import com.baltroid.ui.theme.Poppins
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.ALL
import com.baltroid.util.orZero
import com.hitreads.core.model.Comment
import kotlin.random.Random

@Composable
fun CommentsScreen(
    viewModel: CommentViewModel,
    onBackClick: () -> Unit,
    navigate: (route: String, episodeId: Int?) -> Unit
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    SetLoadingState(isLoading = state.isLoading)

    LaunchedEffect(Unit) {
        viewModel.getAllComments(ALL)
        viewModel.getCommentsByMe()
        viewModel.getCommentsLikedByMe()
    }

    CommentsScreenContent(
        state,
        onBackClick,
        createComment = { comment, content ->
            viewModel.createComment(
                id = comment?.indexOriginal?.id.orZero(),
                content = content,
                responseId = comment?.id
            )
        },
        onLikeClick = { isLiked, id ->
            if (!isLiked) viewModel.likeComment(id)
            else viewModel.unlikeComment(id)
        },
        onHideClicked = {
            viewModel.hideComment(it)
        },
        navigate = navigate,
        onExpanseClicked = {
            viewModel.expanseComment(it)
        }
    )
}

@Composable
private fun CommentsScreenContent(
    uiState: CommentsUiState,
    onBackClick: () -> Unit,
    createComment: (Comment?, String) -> Unit,
    onLikeClick: (Boolean, Int) -> Unit,
    onExpanseClicked: (Int) -> Unit,
    onHideClicked: (Int) -> Unit,
    navigate: (route: String, episodeId: Int?) -> Unit
) {

    var selectedTab by remember {
        mutableStateOf(CommentsTabState.AllComments)
    }

    var isCommentWriteActive by remember {
        mutableStateOf(false)
    }

    var selectedComment by rememberSaveable {
        mutableStateOf<Comment?>(null)
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.localColors.black)
                .systemBarsPadding()
        ) {
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp36))
            MenuBar(
                title = stringResource(id = R.string.comments),
                iconResId = R.drawable.ic_chat_filled
            ) {
                onBackClick.invoke()
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp24))

            CommentSectionTabs(tabState = selectedTab) {
                selectedTab = it
            }

            VerticalSpacer(height = dimensionResource(id = R.dimen.dp33))
            when (selectedTab) {
                CommentsTabState.AllComments -> {
                    CommentSection(
                        lazyListState = rememberLazyListState(),
                        comments = uiState.commentList,
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp30)),
                        onLikeClick = onLikeClick,
                        onReplyClick = {
                            isCommentWriteActive = true
                            selectedComment = it
                        },
                        onHideClicked = onHideClicked,
                        onExpanseClicked = onExpanseClicked
                    )
                }

                CommentsTabState.MyFavorites -> {
                    Comments(uiState.commentsLikedByMe) {
                        navigate.invoke(
                            HitReadsScreens.HomeDetailScreen.route,
                            it.indexOriginal?.id
                        )
                    }
                }

                CommentsTabState.MyComments -> {
                    Comments(uiState.commentsByMe) {
                        navigate.invoke(
                            HitReadsScreens.HomeDetailScreen.route,
                            it.indexOriginal?.id
                        )
                    }
                }
            }
        }
        if (isCommentWriteActive) {
            CommentWritingCard(
                author = selectedComment?.authorName.orEmpty(),
                hashTag = selectedComment?.indexOriginal?.hashtag.orEmpty(),
                onBackClick = { isCommentWriteActive = false },
                imgUrl = "",
                sendComment = { comment ->
                    createComment.invoke(selectedComment, comment)
                    isCommentWriteActive = false
                }
            )
        }
    }
}

@Composable
private fun Comments(
    comments: List<Comment>,
    navigate: (Comment) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp29)),
        contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.dp35)),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(comments) { comment ->
            CommentItem(
                comment = comment
            ) {
                navigate.invoke(comment)
            }
        }
    }
}

@Composable
private fun CommentItem(
    comment: Comment,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .clickable { onItemClick.invoke() }
    ) {
        AsyncImage(
            model = comment.indexOriginal?.cover.orEmpty(),
            contentDescription = null,
            error = painterResource(id = R.drawable.hitreads_placeholder),
            modifier = Modifier
                .size(
                    width = dimensionResource(id = R.dimen.dp127),
                    height = dimensionResource(id = R.dimen.dp177)
                )
                .clip(MaterialTheme.localShapes.roundedDp18)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp13))
        Text(
            text = comment.indexOriginal?.title.orEmpty(),
            style = MaterialTheme.localTextStyles.poppins12Medium,
            color = MaterialTheme.localColors.white_alpha05, textAlign = TextAlign.Center
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp10))
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp3)),
            modifier = Modifier
                .clip(MaterialTheme.localShapes.roundedDp2)
                .border(
                    width = dimensionResource(id = R.dimen.dp1),
                    color = MaterialTheme.localColors.white_alpha07,
                    shape = MaterialTheme.localShapes.roundedDp2
                )
                .padding(
                    vertical = dimensionResource(id = R.dimen.dp7),
                    horizontal = dimensionResource(id = R.dimen.dp20)
                )
        ) {
            SimpleIcon(
                iconResId = R.drawable.ic_comment,
                tint = MaterialTheme.localColors.white_alpha04
            )
            if (!comment.isReply) {
                Text(
                    text = comment.indexOriginal?.commentCount.toString(),
                    style = MaterialTheme.localTextStyles.poppins11Medium,
                    color = MaterialTheme.localColors.white_alpha04
                )
            }
        }
    }
}

@Composable
fun CommentImage(
    imgUrl: String,
    letter: String
) {

    val randomColor by remember {
        mutableStateOf(
            Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        )
    }

    if (imgUrl.isNotEmpty()) {
        AsyncImage(
            model = imgUrl,
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp48))
                .clip(MaterialTheme.localShapes.circleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp48))
                .clip(MaterialTheme.localShapes.circleShape)
                .background(color = randomColor)
        ) {

            Text(
                text = letter.uppercase(),
                color = MaterialTheme.localColors.white,
                fontFamily = Poppins,
                fontWeight = FontWeight.ExtraLight,
                fontSize = 30.sp
            )
        }
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun CommentScreenPreview() {

}