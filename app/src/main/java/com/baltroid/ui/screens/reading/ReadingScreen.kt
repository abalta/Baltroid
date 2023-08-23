package com.baltroid.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.util.orZero
import com.hitreads.core.domain.model.OriginalType
import com.hitreads.core.model.Comment
import com.hitreads.core.model.Episode
import com.hitreads.core.model.ShowEpisode
import com.hitreads.core.model.ShowOriginal

@Composable
fun ReadingScreen(
    viewModel: OriginalViewModel,
    commentViewModel: CommentViewModel = hiltViewModel(),
    openMenuScreen: () -> Unit,
    navigate: (String) -> Unit
) {
    val screenState = viewModel.uiStateReading.collectAsStateWithLifecycle().value
    val sharedOriginal = viewModel.sharedUIState.collectAsStateWithLifecycle().value
    val comments = commentViewModel.uiState.collectAsStateWithLifecycle().value.commentList

    LaunchedEffect(Unit) {
        viewModel.showOriginal(viewModel.selectedOriginal?.id.orZero())
        commentViewModel.getAllComments("original", viewModel.selectedOriginal?.id)
    }

    LaunchedEffect(screenState.original) {
        if (screenState.original != null) {
            viewModel.showEpisode(
                viewModel.selectedEpisode?.id.orZero(),
                OriginalType.TEXT
            )
        }
    }

    ReadingScreenContent(
        body = screenState.episode?.content.orEmpty(),
        original = screenState.original,
    )

    /* ReadingScreenContent(
         commentViewModel,
         originalViewModel = viewModel,
         screenState = screenState,
         textOriginal = sharedOriginal,
         hashTag = sharedOriginal?.hashtag.orEmpty(),
         comments = comments,
         onEpisodeChange = {
             viewModel.showEpisode(
                 screenState.original?.episodes?.get(it)?.id.orZero(),
                 OriginalType.TEXT
             )
         },
         onLikeClick = { isLiked ->
             if (!isLiked) {
                 screenState.episode?.id?.let { it1 -> viewModel.createFavorite(it1) }
             } else {
                 screenState.episode?.id?.let { it1 -> viewModel.deleteFavorite(it1) }
             }
         },
         sendComment = { content, id ->
         },
         onMarkClicked = { isMarked ->
             if (!isMarked) viewModel.createBookmark(
                 screenState.original?.id.orZero(),
                 screenState.episode?.id.orZero()
             ) else {
                 viewModel.deleteBookmark(screenState.episode?.favoriteId.orZero())
             }
         },
         navigate = navigate,
         openMenuScreen,
         onExpanseClicked = {
             commentViewModel.expanseComment(it)
         },
     )*/
}

/*
@Composable
private fun ReadingScreenContent(
    commentVM: CommentViewModel,
    originalViewModel: OriginalViewModel,
    screenState: ReadingUiState,
    textOriginal: Original?,
    hashTag: String,
    comments: List<Comment>,
    onEpisodeChange: (Int) -> Unit,
    onLikeClick: (Boolean) -> Unit,
    sendComment: (String, Int) -> Unit,
    onMarkClicked: (Boolean) -> Unit,
    navigate: (String) -> Unit,
    openMenuScreen: () -> Unit,
    onExpanseClicked: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()
    val lazyScrollState = rememberLazyListState()

    var isSideBarVisible by remember {
        mutableStateOf(true)
    }
    var isReadingSection by remember {
        mutableStateOf(true)
    }
    var isWriteCardShown by remember {
        mutableStateOf(false)
    }

    var selectedEpisodeIndex by rememberSaveable {
        mutableStateOf(0)
    }

    var selectedComment by rememberSaveable {
        mutableStateOf<Comment?>(null)
    }

    val context = LocalContext.current

    LaunchedEffect(selectedEpisodeIndex) {
        onEpisodeChange.invoke(selectedEpisodeIndex)
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.localColors.black)
                .navigationBarsPadding()
        ) {
            HitReadsPageHeader(
                numberOfNotification = 10,
                imgUrl = screenState.original?.cover.orEmpty(),
                onMenuClick = openMenuScreen,
                onIconCLicked = { navigate.invoke(HitReadsScreens.HomeScreen.route) }
            )
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    TitleSection(
                        title = screenState.original?.title.orEmpty(),
                        subtitle = screenState.original?.author?.name.orEmpty(),
                        isExpanded = !isSideBarVisible,
                        episodeName = screenState.episode?.name.orEmpty(),
                        onDotsClick = { isSideBarVisible = !isSideBarVisible },
                        isLiked = screenState.episode?.isFav ?: false,
                        onLikeClick = { onLikeClick(it) },
                        modifier = Modifier.padding(
                            top = dimensionResource(id = R.dimen.dp12),
                            start = MaterialTheme.localDimens.dp32
                        )
                    )
                    Row(
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        if (isReadingSection) {
                            ScrollBar(
                                scrollState = scrollState,
                                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp12))
                            )
                        }
                        if (isReadingSection) {
                            ReadingSection(
                                text = screenState.episode?.content.orEmpty(),
                                scrollState = scrollState,
                                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp12))
                            )
                        } else {
                            CommentSection(
                                lazyListState = lazyScrollState,
                                comments = comments,
                                modifier = Modifier.padding(start = MaterialTheme.localDimens.dp32),
                                onLikeClick = { isLiked, id ->
                                    if (isLiked) {
                                        commentVM.unlikeComment(id)
                                    } else {
                                        commentVM.likeComment(id)
                                    }
                                },
                                onExpanseClicked = onExpanseClicked,
                                onReplyClick = { comment ->
                                    isWriteCardShown = true
                                    selectedComment = comment
                                }
                            )
                        }
                    }
                }
                AnimatedVisibility(visible = isSideBarVisible) {
                    BoxWithConstraints {
                        HitReadsSideBar(
                            numberOfViews = textOriginal?.viewCount.orZero(),
                            numberOfComments = textOriginal?.commentCount.orZero(),
                            hashTag = hashTag,
                            hasSmallHeight = maxHeight < MaterialTheme.localDimens.minSideBarHeight,
                            isCommentsSelected = !isReadingSection,
                            onDotsClick = { isSideBarVisible = !isSideBarVisible },
                            onCommentsClick = { isReadingSection = !isReadingSection },
                            isMarked = screenState.episode?.isBookmarked ?: false,
                            onMarkClicked = onMarkClicked,
                            onShareClicked = {
                                share(screenState.original?.title, context)
                            },
                            addComment = { isWriteCardShown = true }
                        )
                    }
                }
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp16))
            AnimatedVisibility(visible = if (isReadingSection) scrollState.isEpisodesVisible() else lazyScrollState.isEpisodesVisible()) {
                EpisodeSection(
                    episodes = screenState.original?.episodes.orEmpty(),
                    paddingValues = PaddingValues(horizontal = MaterialTheme.localDimens.dp32),
                    selectedEpisodeIndex = selectedEpisodeIndex,
                    episode = screenState.episode,
                    onEpisodeClick = { index ->
                        selectedEpisodeIndex = index
                    }
                )
            }
        }
        AnimatedVisibility(visible = isWriteCardShown, enter = fadeIn(), exit = fadeOut()) {
            CommentWritingCard(
                author = selectedComment?.authorName.orEmpty(),
                textOriginal?.hashtag.orEmpty() + " " + "B${selectedEpisodeIndex}",
                onBackClick = { isWriteCardShown = !isWriteCardShown }
            ) {
                if (isReadingSection) {
                    originalViewModel.createComment(
                        screenState.original?.id.orZero(),
                        it,
                        null
                    )
                } else {
                    commentVM.createComment(
                        selectedComment?.original?.id.orZero(),
                        it,
                        selectedComment?.id.orZero()
                    )
                }
                isWriteCardShown = false
            }
        }
    }
}
*/

fun share(title: String?, ctx: Context) {
    Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
        putExtra(Intent.EXTRA_TEXT, title)
        ctx.startActivity(Intent.createChooser(this, "Original"))
    }
}

@Composable
fun ScrollBar(
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    val localColors = MaterialTheme.localColors

    Box(
        modifier = modifier
            .width(6.dp)
            .fillMaxHeight()
            .drawWithContent {
                val maxScrollValue = scrollState.maxValue
                val currentScrollValue = scrollState.value
                val scrollPercent = currentScrollValue.toFloat() / maxScrollValue.toFloat()
                val scrollOffsetY = (size.height - 50.dp.toPx()) * scrollPercent

                drawRoundRect(
                    color = localColors.white_alpha05,
                    topLeft = Offset(
                        x = 0f,
                        y = scrollOffsetY
                    ),
                    style = Stroke(1.dp.toPx()),
                    cornerRadius = CornerRadius(
                        6.dp.toPx(),
                        6.dp.toPx()
                    ),
                    size = Size(
                        width = 6.dp.toPx(),
                        height = 50.dp.toPx()
                    )
                )
            }
    )
}

@Composable
fun HitReadsPageHeader(
    numberOfNotification: Int,
    onMenuClick: () -> Unit,
    onIconCLicked: () -> Unit,
    imgUrl: String,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (image, topBar) = createRefs()
        AsyncImage(
            model = imgUrl,
            contentDescription = null,
            error = painterResource(id = R.drawable.hitreads_placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier.constrainAs(image) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(topBar.bottom, margin = (-5).dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )
        HitReadsTopBar(
            modifier = Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onNotificationClick = {},
            iconResId = R.drawable.ic_bell,
            numberOfNotification = numberOfNotification,
            onMenuClick = onMenuClick,
            onIconClick = onIconCLicked
        )
    }
}

/*@Composable
fun TitleSection(
    title: String,
    subtitle: String,
    isExpanded: Boolean,
    episodeName: String,
    isLiked: Boolean,
    onDotsClick: () -> Unit,
    onLikeClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Titles(
                title = title,
                subtitle = subtitle,
                episodeTitle = episodeName,
                modifier = Modifier.weight(1f)
            )
            SimpleIcon(
                iconResId = if (isLiked) R.drawable.ic_star else R.drawable.ic_star_outlined,
                tint = if (isLiked) MaterialTheme.localColors.yellow else Color.Unspecified,
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.dp12),
                        end = dimensionResource(id = R.dimen.dp15)
                    )
                    .clickable {
                        onLikeClick(isLiked)
                    }
            )
            if (isExpanded) {
                HorizontalSpacer(width = dimensionResource(id = R.dimen.dp15))
                Divider(
                    color = MaterialTheme.localColors.white_alpha05,
                    modifier = Modifier
                        .width(MaterialTheme.localDimens.dp0_5)
                        .fillMaxHeight()
                )
                HorizontalSpacer(width = dimensionResource(id = R.dimen.dp10))
                SimpleIcon(
                    iconResId = R.drawable.ic_menu,
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.dp12),
                            end = dimensionResource(id = R.dimen.dp20)
                        )
                        .clickable { onDotsClick.invoke() })
            }

        }
        if (isExpanded) {
            Divider(
                color = MaterialTheme.localColors.white_alpha05,
                thickness = MaterialTheme.localDimens.dp0_5
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp4_5)
        }
    }
}*/

@Composable
fun TitleSection(
    title: String,
    subtitle: String,
    isExpanded: Boolean,
    isLiked: Boolean,
    onDotsClick: () -> Unit,
    onLikeClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Column(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Titles(
                    title = title,
                    subtitle = subtitle,
                    modifier = Modifier.weight(1f)
                )
                SimpleIcon(
                    iconResId = if (isLiked) R.drawable.ic_star else R.drawable.ic_star_outlined,
                    tint = if (isLiked) MaterialTheme.localColors.yellow else Color.Unspecified,
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            end = dimensionResource(id = R.dimen.dp15),
                        )
                        .clickable {
                            onLikeClick(isLiked)
                        }
                )
                if (isExpanded) {
                    HorizontalSpacer(width = dimensionResource(id = R.dimen.dp15))
                    Divider(
                        color = MaterialTheme.localColors.white_alpha05,
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.dp1))
                            .fillMaxHeight()
                    )
                    HorizontalSpacer(width = dimensionResource(id = R.dimen.dp10))
                    SimpleIcon(
                        iconResId = R.drawable.ic_menu,
                        modifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.dp12),
                                end = dimensionResource(id = R.dimen.dp20)
                            )
                            .clickable { onDotsClick.invoke() })
                }

            }
        }
        if (isExpanded) {
            Divider(
                color = MaterialTheme.localColors.white_alpha05,
                thickness = dimensionResource(id = R.dimen.dp1),
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.dp12))
            )
            VerticalSpacer(height = 5.dp)
        }
    }
}

/*@Composable
fun Titles(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    episodeTitle: String? = null
) {
    Column(
        modifier = modifier
    ) {
        Text(text = title, style = MaterialTheme.localTextStyles.title)
        Text(text = subtitle, style = MaterialTheme.localTextStyles.subtitle)
        episodeTitle?.let {
            Text(
                text = episodeTitle,
                style = MaterialTheme.localTextStyles.readingPurpleSubTitle
            )
        }
    }
}*/

@Composable
fun Titles(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.localTextStyles.title,
            color = MaterialTheme.localColors.white
        )
        Text(
            text = subtitle,
            style = MaterialTheme.localTextStyles.subtitle,
            color = MaterialTheme.localColors.white
        )
    }
}

@Composable
fun ReadingSection(
    text: String,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    SelectionContainer(modifier) {
        Text(
            text = text,
            style = MaterialTheme.localTextStyles.body,
            color = MaterialTheme.localColors.white_alpha08,
            modifier = Modifier.verticalScroll(scrollState)
        )
    }
}

@Composable
fun EpisodeSection(
    episodes: List<ShowEpisode>,
    paddingValues: PaddingValues,
    episode: Episode?,
    modifier: Modifier = Modifier,
    selectedEpisodeIndex: Int,
    onEpisodeClick: (index: Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp13)),
        contentPadding = paddingValues,
        modifier = modifier
    ) {
        itemsIndexed(episodes) { index, item ->
            EpisodeSectionItem(
                episodeNumber = item.sort.orZero(),
                hasBanner = (episode?.id == item.id) && episode?.isBookmarked == true,
                isSelected = index == selectedEpisodeIndex,
                onClick = { onEpisodeClick(index) }
            )
        }
    }
}


@Composable
fun EpisodeSectionItem(
    episodeNumber: Int,
    isSelected: Boolean,
    hasBanner: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val episodeTextStyle = MaterialTheme.localTextStyles.episodeSelectedText

    Column(
        modifier
            .width(IntrinsicSize.Min)
            .clickable { onClick.invoke() }
    ) {
        Row {
            Text(text = stringResource(id = R.string.episode), style = episodeTextStyle)
            HorizontalSpacer(width = dimensionResource(id = R.dimen.dp4))
            Text(text = episodeNumber.toString(), style = episodeTextStyle)
        }
        VerticalSpacer(height = 5.dp)
        if (hasBanner) {
            EpisodeBanner(modifier = Modifier.fillMaxWidth())
        } else {
            Divider(
                color = MaterialTheme.localColors.grey,
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp17))
        }
    }
}

@Composable
fun CommentSection(
    lazyListState: LazyListState,
    comments: List<Comment>,
    modifier: Modifier = Modifier,
    onLikeClick: (Boolean, Int) -> Unit,
    onReplyClick: (Comment) -> Unit,
    onExpanseClicked: (Int) -> Unit
) {

    Column(
        modifier = modifier
    ) {
        LazyColumn(
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp15)),
            contentPadding = PaddingValues(top = dimensionResource(id = R.dimen.dp14)),
        ) {
            items(comments) { comment ->
                CommentItem(
                    model = comment,
                    isChatSelected = false,
                    onLikeClick = onLikeClick,
                    onReplyClick = { onReplyClick.invoke(comment) }
                )
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp12))
                CommentItem(
                    model = comment.replies.firstOrNull() ?: return@items,
                    isChatSelected = false,
                    onLikeClick = onLikeClick,
                    onReplyClick = {
                        onReplyClick.invoke(comment)
                    }
                )
                if (!comment.isExpanded && comment.replies.size > 1) {
                    SeeAll {
                        onExpanseClicked.invoke(comment.id)
                    }
                }
                if (comment.isExpanded) {
                    comment.replies.forEachIndexed { index, comment ->
                        if (index != 0) {
                            CommentItem(
                                model = comment,
                                isChatSelected = false,
                                onLikeClick = onLikeClick,
                                onReplyClick = {
                                    onReplyClick.invoke(comment)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommentSectionTabs(
    tabState: CommentsTabState,
    scrollState: ScrollState = rememberScrollState(),
    onTabSelect: (CommentsTabState) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp9)),
        modifier = Modifier.horizontalScroll(scrollState)
    ) {
        TabItem(
            title = stringResource(id = R.string.all_comments),
            isSelected = tabState == CommentsTabState.AllComments
        ) {
            onTabSelect.invoke(CommentsTabState.AllComments)
        }
        TabItem(
            title = stringResource(id = R.string.my_favorite_comments),
            isSelected = tabState == CommentsTabState.MyFavorites
        ) {
            onTabSelect.invoke(CommentsTabState.MyFavorites)
        }
        TabItem(
            title = stringResource(id = R.string.my_comments),
            isSelected = tabState == CommentsTabState.MyComments
        ) {
            onTabSelect.invoke(CommentsTabState.MyComments)
        }
    }
}

@Composable
private fun TabItem(
    title: String,
    isSelected: Boolean,
    onTabSelect: (CommentsTabState) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.width(IntrinsicSize.Max),
    ) {
        Text(
            text = title,
            style = MaterialTheme.localTextStyles.subtitle,
            color = if (isSelected) MaterialTheme.localColors.white else MaterialTheme.localColors.white_alpha07,
            modifier = Modifier.clickable { onTabSelect.invoke(CommentsTabState.AllComments) }
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.localColors.white_alpha07)
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.dp4))
            )
        }
    }
}

@Composable
fun CommentItem(
    model: Comment,
    isChatSelected: Boolean,
    modifier: Modifier = Modifier,
    onLikeClick: (Boolean, Int) -> Unit,
    onReplyClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (subCommentIcon, commentHeader, comment) = createRefs()
        if (model.isReply) {
            SimpleIcon(
                iconResId = R.drawable.ic_subcomment_arrow,
                modifier = Modifier.constrainAs(subCommentIcon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(commentHeader.bottom)
                }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .constrainAs(commentHeader) {
                    if (model.isReply) {
                        start.linkTo(subCommentIcon.end, 11.dp)
                    } else {
                        start.linkTo(parent.start)
                    }
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 34.dp)
                    width = Dimension.fillToConstraints
                }
        ) {
            CommentImage(
                imgUrl = model.imgUrl,
                letter = if (model.authorName.isNotEmpty()) model.authorName.first()
                    .toString() else "?"
            )
            HorizontalSpacer(width = dimensionResource(id = R.dimen.dp13))
            Column(
                verticalArrangement = Arrangement.Bottom,
            ) {
                Text(
                    text = model.authorName,
                    style = MaterialTheme.localTextStyles.subtitle,
                    color = MaterialTheme.localColors.white
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = model.createdAt,
                        style = MaterialTheme.localTextStyles.dateText,
                        color = MaterialTheme.localColors.white_alpha07
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp12)),
                    ) {
                        SimpleIcon(
                            iconResId = if (model.isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart_outlined,
                            modifier = Modifier.clickable {
                                onLikeClick.invoke(
                                    model.isLiked,
                                    model.id
                                )
                            }
                        )
                        IconWithTextNextTo(
                            iconResId = if (isChatSelected) R.drawable.ic_chat_filled else R.drawable.ic_chat_outlined,
                            text = model.repliesCount.toString(),
                            spacedBy = 6.dp,
                            textStyle = MaterialTheme.localTextStyles.sideBarIconText,
                            isTextVisible = !model.isReply,
                            onIconClick = { onReplyClick.invoke() },
                        )
                        SimpleIcon(iconResId = R.drawable.ic_menu_horizontal)
                    }
                }
            }
        }
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp14))
        Text(
            text = model.content,
            style = MaterialTheme.localTextStyles.episodeText,
            color = MaterialTheme.localColors.white_alpha08,
            modifier = Modifier.constrainAs(comment) {
                start.linkTo(commentHeader.start)
                top.linkTo(commentHeader.bottom)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
fun HashTagSection(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = paddingValues,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp23)),
        modifier = modifier,
    ) {
        item { HasTagItem(hashTag = "TÜMÜ", commentSize = 1678, isSelected = true) }
        item { HasTagItem(hashTag = "#KGD", commentSize = 220, isSelected = true) }
        item { HasTagItem(hashTag = "#MKV", commentSize = 0, isSelected = false) }
        item { HasTagItem(hashTag = "#FRK", commentSize = 47, isSelected = false) }
        item { HasTagItem(hashTag = "#HLS", commentSize = 4, isSelected = false) }
        item { HasTagItem(hashTag = "#BRF", commentSize = 17, isSelected = false) }
    }
}

@Composable
fun HasTagItem(
    hashTag: String,
    commentSize: Int,
    isSelected: Boolean,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.width(IntrinsicSize.Min),
    ) {
        IconWithTextNextTo(
            iconResId = R.drawable.ic_comment,
            text = commentSize.toString(),
            spacedBy = dimensionResource(id = R.dimen.dp3),
            tint = MaterialTheme.localColors.white_alpha04,
            textStyle = MaterialTheme.localTextStyles.topBarIconText,
            onIconClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        )
        Text(
            text = hashTag,
            style = MaterialTheme.localTextStyles.episodeSelectedText
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .height(3.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.localColors.orange)
            )
        } else {
            Box(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dp1))
                    .fillMaxWidth()
                    .background(MaterialTheme.localColors.grey)
            )
        }

    }
}

@Composable
private fun ScrollState.isEpisodesVisible(): Boolean {
    var previousOffset by remember(this) { mutableStateOf(0) }
    return remember(this) {
        derivedStateOf {
            if (value == 0) {
                true
            } else if (value > previousOffset) {
                false
            } else {
                value < maxValue
            }.also {
                previousOffset = value
            }
        }
    }.value
}

@Composable
private fun LazyListState.isEpisodesVisible(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != 0) {
                previousIndex > firstVisibleItemIndex
            } else {
                firstVisibleItemIndex == 0
            }.also {
                previousIndex = firstVisibleItemIndex
            }
        }
    }.value
}

@Composable
fun SeeAll(
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick.invoke() }
    ) {
        Box(
            modifier = Modifier
                .height(1.dp)
                .width(100.dp)
                .background(MaterialTheme.localColors.white_alpha07)
                .fillMaxWidth()
        )
        HorizontalSpacer(width = 12.dp)
        Text(
            text = "Tüm Cevapları Göster",
            color = MaterialTheme.localColors.white_alpha07,
            fontFamily = Poppins,
            fontSize = 10.sp
        )
        HorizontalSpacer(width = 12.dp)
        Box(
            modifier = Modifier
                .height(1.dp)
                .width(100.dp)
                .background(MaterialTheme.localColors.white_alpha07)
                .fillMaxWidth()
        )
    }
}

@Composable
fun ReadingScreenContent(
    body: String,
    original: ShowOriginal?,
) {
    val scrollState = rememberScrollState()
    Column {
        AsyncImage(
            model = R.drawable.woods_image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.14f),
        )
        Row {
            HorizontalSpacer(width = dimensionResource(id = R.dimen.dp31))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                TitleSection(
                    title = original?.title.orEmpty(),
                    subtitle = original?.author?.name.orEmpty(),
                    isExpanded = false,
                    isLiked = false,
                    onDotsClick = { /*TODO*/ },
                    onLikeClick = { },
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.dp12),
                    )
                )
                Text(
                    text = body,
                    style = MaterialTheme.localTextStyles.body,
                    color = MaterialTheme.localColors.white_alpha08,
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(
                            end = dimensionResource(id = R.dimen.dp24)
                        )
                )
            }
            HitReadsSideBar(
                numberOfComments = 0,
                onVisibilityChange = { /*TODO*/ },
                onShowComments = { /*TODO*/ },
                onCreateComment = { /*TODO*/ },
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .padding(
                        top = dimensionResource(id = R.dimen.dp12),
                        end = dimensionResource(id = R.dimen.dp12)
                    )
            ) {

            }
        }
    }
}

@Preview
@Composable
fun ReadingScreenPreview() {

}

