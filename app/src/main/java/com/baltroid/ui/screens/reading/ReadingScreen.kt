package com.baltroid.ui.screens.reading

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.R
import com.baltroid.ui.common.CroppedImage
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.IconWithTextNextTo
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.CommentWritingCard
import com.baltroid.ui.components.HitReadsSideBar
import com.baltroid.ui.components.HitReadsTopBar
import com.baltroid.ui.screens.menu.place_marks.EpisodeBanner
import com.baltroid.ui.screens.reading.comments.CommentsTabState
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.orEmpty
import com.hitreads.core.domain.model.OriginalType
import com.hitreads.core.model.ShowEpisode

@Composable
fun ReadingScreen(
    originalId: Int,
    viewModel: ReadingViewModel = hiltViewModel(),
    openMenuScreen: () -> Unit
) {
    val original = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.showOriginal(2)
    }

    LaunchedEffect(original.original) {
        if (original != null) {
            viewModel.showEpisode(
                760,
                OriginalType.TEXT
            )
        }
    }

    ReadingScreenContent(
        screenState = viewModel.uiState.collectAsStateWithLifecycle().value,
        onLikeClick = {
            if (it) viewModel.unlikeOriginal(originalId)
            else viewModel.likeOriginal(originalId)
        },
        openMenuScreen
    )
}

@Composable
private fun ReadingScreenContent(
    screenState: ReadingUiState,
    onLikeClick: (Boolean) -> Unit,
    openMenuScreen: () -> Unit
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

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.localColors.black)
                .navigationBarsPadding()
        ) {
            HitReadsPageHeader(
                numberOfNotification = 10,
                onMenuClick = openMenuScreen
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
                        onDotsClick = { isSideBarVisible = !isSideBarVisible },
                        isLiked = false /*todo isLiked*/,
                        onLikeClick = { onLikeClick(it) },
                        modifier = Modifier.padding(
                            top = MaterialTheme.localDimens.dp12,
                            start = MaterialTheme.localDimens.dp32
                        )
                    )
                    Row(
                        modifier = Modifier.padding(end = MaterialTheme.localDimens.dp8)
                    ) {
                        if (isReadingSection) {
                            ScrollBar(
                                scrollState = scrollState,
                                modifier = Modifier.padding(start = MaterialTheme.localDimens.dp12)
                            )
                        }
                        if (isReadingSection) {
                            ReadingSection(
                                text = screenState.episode?.content.orEmpty(),
                                scrollState = scrollState,
                                modifier = Modifier.padding(start = MaterialTheme.localDimens.dp12)
                            )
                        } else {
                            CommentSection(
                                lazyListState = lazyScrollState,
                                tabState = CommentsTabState.AllComments,
                                onTabSelect = {},
                                modifier = Modifier.padding(start = MaterialTheme.localDimens.dp32)
                            )
                        }
                    }
                }
                AnimatedVisibility(visible = isSideBarVisible) {
                    BoxWithConstraints {
                        HitReadsSideBar(
                            numberOfViews = -1,
                            numberOfComments = -1,
                            hashTag = "#KGD",
                            hasSmallHeight = maxHeight < MaterialTheme.localDimens.minSideBarHeight,
                            isCommentsSelected = !isReadingSection,
                            onDotsClick = { isSideBarVisible = !isSideBarVisible },
                            onCommentsClick = { isReadingSection = !isReadingSection },
                            addComment = { isWriteCardShown = true }
                        )
                    }
                }
            }
            VerticalSpacer(height = MaterialTheme.localDimens.dp16)
            AnimatedVisibility(visible = if (isReadingSection) scrollState.isEpisodesVisible() else lazyScrollState.isEpisodesVisible()) {
                EpisodeSection(
                    episodes = screenState.original?.episodes.orEmpty(),
                    paddingValues = PaddingValues(start = MaterialTheme.localDimens.dp32)
                )
            }
        }
        AnimatedVisibility(visible = isWriteCardShown, enter = fadeIn(), exit = fadeOut()) {
            CommentWritingCard(
                onBackClick = { isWriteCardShown = !isWriteCardShown }
            ) {
                //todo send comment
            }
        }
    }
}

@Composable
fun ScrollBar(
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    val localColors = MaterialTheme.localColors
    val localDimens = MaterialTheme.localDimens

    Box(
        modifier = modifier
            .width(MaterialTheme.localDimens.dp6)
            .fillMaxHeight()
            .drawWithContent {
                val maxScrollValue = scrollState.maxValue
                val currentScrollValue = scrollState.value
                val scrollPercent = currentScrollValue.toFloat() / maxScrollValue.toFloat()
                val scrollOffsetY = (size.height - localDimens.dp50.toPx()) * scrollPercent

                drawRoundRect(
                    color = localColors.white_alpha05,
                    topLeft = Offset(
                        x = 0f,
                        y = scrollOffsetY
                    ),
                    style = Stroke(localDimens.dp0_5.toPx()),
                    cornerRadius = CornerRadius(
                        localDimens.dp6.toPx(),
                        localDimens.dp6.toPx()
                    ),
                    size = Size(
                        width = localDimens.dp6.toPx(),
                        height = localDimens.dp50.toPx()
                    )
                )
            }
    )
}

@Composable
fun ShadowBox(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.localColors.black_alpha05)
            .aspectRatio(1f)
    )
}

@Composable
fun HitReadsPageHeader(
    numberOfNotification: Int,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (image, topBar) = createRefs()
        val localDimens = MaterialTheme.localDimens
        CroppedImage(
            imgResId = R.drawable.header_image,
            modifier = Modifier.constrainAs(image) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(topBar.bottom, margin = -localDimens.dp5)
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
            onMenuClick = onMenuClick
        )
    }
}

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
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Titles(title = title, subtitle = subtitle, modifier = Modifier.weight(1f))
            SimpleIcon(
                iconResId = if (isLiked) R.drawable.ic_star else R.drawable.ic_star_outlined,
                tint = if (isLiked) MaterialTheme.localColors.yellow else Color.Unspecified,
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.localDimens.dp12,
                        end = MaterialTheme.localDimens.dp15
                    )
                    .clickable {
                        onLikeClick(isLiked)
                    }
            )
            if (isExpanded) {
                HorizontalSpacer(width = MaterialTheme.localDimens.dp15)
                Divider(
                    color = MaterialTheme.localColors.white_alpha05,
                    modifier = Modifier
                        .width(MaterialTheme.localDimens.dp0_5)
                        .fillMaxHeight()
                )
                HorizontalSpacer(width = MaterialTheme.localDimens.dp10)
                SimpleIcon(
                    iconResId = R.drawable.ic_menu,
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.localDimens.dp12,
                            end = MaterialTheme.localDimens.dp20
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
}

@Composable
fun Titles(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(text = title, style = MaterialTheme.localTextStyles.title)
        Text(text = subtitle, style = MaterialTheme.localTextStyles.subtitle)
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
            modifier = Modifier.verticalScroll(scrollState)
        )
    }
}

@Composable
fun EpisodeSection(
    episodes: List<ShowEpisode>,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp13),
        contentPadding = paddingValues,
        modifier = modifier
    ) {
        itemsIndexed(episodes) { index, item ->
            EpisodeSectionItem(
                numberOfComment = -1,
                episodeNumber = index + 1,
                isSelected = false,
                hasBanner = false,
                isLocked = false
            )
        }
    }
}


@Composable
fun EpisodeSectionItem(
    numberOfComment: Int,
    episodeNumber: Int,
    isSelected: Boolean,
    hasBanner: Boolean,
    isLocked: Boolean,
    modifier: Modifier = Modifier
) {
    val episodeTextStyle = if (isSelected) MaterialTheme.localTextStyles.episodeSelectedText
    else MaterialTheme.localTextStyles.episodeUnselectedText

    Column(
        modifier
            .width(IntrinsicSize.Min)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            SimpleIcon(
                iconResId = R.drawable.ic_comment,
                tint = MaterialTheme.localColors.white_alpha04,
                modifier = Modifier.size(MaterialTheme.localDimens.dp16)
            )
            HorizontalSpacer(width = MaterialTheme.localDimens.dp3)
            Text(
                text = numberOfComment.toString(),
                style = MaterialTheme.localTextStyles.episodeSectionIconText,
                modifier = Modifier.weight(1f)
            )
            if (isLocked) {
                SimpleIcon(iconResId = R.drawable.ic_lock)
            }
        }

        VerticalSpacer(height = MaterialTheme.localDimens.dp3_5)
        Row {
            Text(text = stringResource(id = R.string.episode), style = episodeTextStyle)
            HorizontalSpacer(width = MaterialTheme.localDimens.dp4)
            Text(text = episodeNumber.toString(), style = episodeTextStyle)
        }
        VerticalSpacer(height = MaterialTheme.localDimens.dp5)
        if (hasBanner) {
            EpisodeBanner(modifier = Modifier.fillMaxWidth())
        } else {
            Divider(
                color = MaterialTheme.localColors.grey,
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp17)
        }
    }
}

@Composable
fun CommentSection(
    lazyListState: LazyListState,
    tabState: CommentsTabState,
    modifier: Modifier = Modifier,
    onTabSelect: (CommentsTabState) -> Unit
) {

    Column(
        modifier = modifier
    ) {
        CommentSectionTabs(tabState = tabState, onTabSelect = onTabSelect)
        LazyColumn(
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp15),
            contentPadding = PaddingValues(top = MaterialTheme.localDimens.dp14),
        ) {
            items(15) {
                CommentItem(
                    owner = "SELEN PEKMEZCİ",
                    date = "04/01/2023 20:29",
                    chatSize = 3,
                    isSubComment = true,
                    isLiked = false,
                    isChatSelected = false
                )
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
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp9),
        modifier = Modifier.horizontalScroll(scrollState)
    ) {
        Text(
            text = stringResource(id = R.string.all_comments),
            style = if (tabState == CommentsTabState.AllComments) MaterialTheme.localTextStyles.subtitle
            else MaterialTheme.localTextStyles.interactiveEpisode,
            modifier = Modifier.clickable { onTabSelect.invoke(CommentsTabState.AllComments) }
        )
        Text(
            text = stringResource(id = R.string.my_favorite_comments),
            style = if (tabState == CommentsTabState.MyFavorites) MaterialTheme.localTextStyles.subtitle
            else MaterialTheme.localTextStyles.interactiveEpisode,
            modifier = Modifier.clickable { onTabSelect.invoke(CommentsTabState.MyFavorites) }
        )
        Text(
            text = stringResource(id = R.string.my_comments),
            style = if (tabState == CommentsTabState.MyComments) MaterialTheme.localTextStyles.subtitle
            else MaterialTheme.localTextStyles.interactiveEpisode,
            modifier = Modifier.clickable { onTabSelect.invoke(CommentsTabState.MyComments) }
        )
    }
}

@Composable
fun CommentItem(
    owner: String,
    date: String,
    chatSize: Int,
    isSubComment: Boolean,
    isLiked: Boolean,
    isChatSelected: Boolean,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (subCommentIcon, commentHeader, comment) = createRefs()
        if (isSubComment) {
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
                    if (isSubComment) {
                        start.linkTo(subCommentIcon.end, 11.dp)
                    } else {
                        start.linkTo(parent.start)
                    }
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 34.dp)
                    width = Dimension.fillToConstraints
                }
        ) {
            CroppedImage(
                imgResId = R.drawable.woods_image,
                modifier
                    .size(MaterialTheme.localDimens.dp48)
                    .clip(MaterialTheme.localShapes.circleShape)
            )
            HorizontalSpacer(width = MaterialTheme.localDimens.dp13)
            Column(
                verticalArrangement = Arrangement.Bottom,
            ) {
                Text(text = owner, style = MaterialTheme.localTextStyles.subtitle)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = date,
                        style = MaterialTheme.localTextStyles.dateText
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp12),
                    ) {
                        SimpleIcon(iconResId = if (isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart_outlined)
                        IconWithTextNextTo(
                            iconResId = if (isChatSelected) R.drawable.ic_chat_filled else R.drawable.ic_chat_outlined,
                            text = chatSize.toString(),
                            spacedBy = MaterialTheme.localDimens.dp6,
                            textStyle = MaterialTheme.localTextStyles.sideBarIconText
                        )
                        SimpleIcon(iconResId = R.drawable.ic_menu_horizontal)
                    }
                }
            }
        }
        Text(
            text = "First of all please publish this so I can buy it for my library! second, there definitely needs to be a part 2 or second book because I’m hooked.",
            style = MaterialTheme.localTextStyles.commentText,
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
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp23),
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
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp4_5),
        modifier = Modifier.width(IntrinsicSize.Min),
    ) {
        IconWithTextNextTo(
            iconResId = R.drawable.ic_comment,
            text = commentSize.toString(),
            spacedBy = MaterialTheme.localDimens.dp3,
            tint = MaterialTheme.localColors.white_alpha04,
            textStyle = MaterialTheme.localTextStyles.episodeSectionIconText,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        )
        Text(
            text = hashTag,
            style = if (isSelected) MaterialTheme.localTextStyles.episodeSelectedText
            else MaterialTheme.localTextStyles.episodeUnselectedText
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .height(MaterialTheme.localDimens.dp3)
                    .fillMaxWidth()
                    .background(MaterialTheme.localColors.orange)
            )
        } else {
            Box(
                modifier = Modifier
                    .height(MaterialTheme.localDimens.dp1)
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
            if (value > previousOffset) {
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

@Preview(heightDp = 700)
@Preview(heightDp = 530)
@Preview
@Composable
fun ReadingScreenPreview() {

}

