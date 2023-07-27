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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.CommentWritingCard
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.screens.reading.CommentSection
import com.baltroid.ui.screens.reading.CommentSectionTabs
import com.baltroid.ui.screens.reading.comments.CommentsTabState
import com.baltroid.ui.theme.Poppins
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.orZero
import com.hitreads.core.model.Comment
import kotlin.random.Random

@Composable
fun CommentsScreen(
    viewModel: CommentViewModel,
    onBackClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getAllComments("all", null)
    }

    CommentsScreenContent(
        viewModel.uiState.collectAsStateWithLifecycle().value.commentList,
        onBackClick,
        createComment = { comment, content ->
            viewModel.createComment(
                id = comment?.original?.id.orZero(),
                content = content,
                responseId = comment?.id
            )
        },
        onLikeClick = { isLiked, id ->
            if (!isLiked) viewModel.likeComment(id)
            else viewModel.unlikeComment(id)
        }
    )
}

@Composable
private fun CommentsScreenContent(
    comments: List<Comment>,
    onBackClick: () -> Unit,
    createComment: (Comment?, String) -> Unit,
    onLikeClick: (Boolean, Int) -> Unit,
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
            VerticalSpacer(height = MaterialTheme.localDimens.dp36)
            MenuBar(
                title = stringResource(id = R.string.comments),
                iconResId = R.drawable.ic_chat_filled
            ) {
                onBackClick.invoke()
            }
            VerticalSpacer(height = MaterialTheme.localDimens.dp24)

            CommentSectionTabs(tabState = selectedTab) {
                selectedTab = it
            }

            VerticalSpacer(height = MaterialTheme.localDimens.dp33)
            when (selectedTab) {
                CommentsTabState.AllComments -> {
                    CommentSection(
                        lazyListState = rememberLazyListState(),
                        comments = comments,
                        modifier = Modifier.padding(start = MaterialTheme.localDimens.dp30),
                        onLikeClick = onLikeClick,
                        onReplyClick = {
                            isCommentWriteActive = true
                            selectedComment = it
                        }
                    )
                }

                CommentsTabState.MyFavorites -> {
                    Comments(comments) {

                    }
                }

                CommentsTabState.MyComments -> {
                    Comments(comments) {

                    }
                }
            }
        }
        if (isCommentWriteActive) {
            CommentWritingCard(
                author = selectedComment?.authorName.orEmpty(),
                hashTag = selectedComment?.original?.hashtag.orEmpty(),
                onBackClick = { isCommentWriteActive = false },
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
    onReplyClick: (Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp29),
        contentPadding = PaddingValues(horizontal = MaterialTheme.localDimens.dp35),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(comments) { comment ->
            CommentItem(
                comment = comment,
                onReplyClick = onReplyClick
            )
        }
    }
}

@Composable
private fun CommentItem(
    comment: Comment,
    onReplyClick: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        AsyncImage(
            model = comment.original?.cover.orEmpty(),
            contentDescription = null,
            error = painterResource(id = R.drawable.hitreads_placeholder),
            modifier = Modifier
                .size(
                    width = MaterialTheme.localDimens.dp127,
                    height = MaterialTheme.localDimens.dp177
                )
                .clip(MaterialTheme.localShapes.roundedDp18)
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp13)
        Text(
            text = comment.original?.title.orEmpty(),
            style = MaterialTheme.localTextStyles.storyItemTitle
        )
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
                tint = MaterialTheme.localColors.white_alpha04,
                modifier = Modifier.clickable { onReplyClick.invoke(comment.id) }
            )
            if (!comment.isReply) {
                Text(
                    text = comment.original?.commentCount.toString(),
                    style = MaterialTheme.localTextStyles.episodeSectionIconText
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
                .size(MaterialTheme.localDimens.dp48)
                .clip(MaterialTheme.localShapes.circleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(MaterialTheme.localDimens.dp48)
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