package com.baltroid.presentation.screens.reading

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.baltroid.apps.R
import com.baltroid.presentation.common.CroppedImage
import com.baltroid.presentation.common.HorizontalSpacer
import com.baltroid.presentation.common.IconWithTextBelow
import com.baltroid.presentation.common.IconWithTextNextTo
import com.baltroid.presentation.common.SimpleIcon
import com.baltroid.presentation.common.VerticalSpacer
import com.baltroid.presentation.components.HitReadsSideBar
import com.baltroid.presentation.components.HitReadsTopBar
import com.baltroid.presentation.screens.menu.EpisodeBanner
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun ReadingScreen(
    bodyText: String,
    title: String,
    subtitle: String,
    numberOfComments: Int,
    numberOfViews: Int,
    numberOfNotification: Int,
    isReadingSection: Boolean,
) {
    val scrollState = rememberScrollState()
    val lazyListState = rememberLazyListState()

    BoxWithConstraints {

        val maxHeight = this.maxHeight
        val localDimens = MaterialTheme.localDimens

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.localColors.black)
                .navigationBarsPadding()
        ) {
            val (
                header, titleSection, articleSection, sideBar,
                episodeSection, shadowBox, scrollBar
            ) = createRefs()

            HitReadsPageHeader(
                numberOfNotification = numberOfNotification,
                Modifier.constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )
            HitReadsSideBar(
                numberOfComments = numberOfComments,
                numberOfViews = numberOfViews,
                hasSmallHeight = maxHeight < MaterialTheme.localDimens.minScreenHeight,
                modifier = Modifier.constrainAs(sideBar) {
                    top.linkTo(header.bottom)
                    end.linkTo(parent.end, margin = localDimens.dp12_5)
                    bottom.linkTo(articleSection.bottom)
                    height = Dimension.fillToConstraints
                }
            )
            TitleSection(
                title = title,
                subtitle = subtitle,
                modifier = Modifier.constrainAs(titleSection) {
                    top.linkTo(header.bottom, margin = localDimens.dp11_5)
                    start.linkTo(scrollBar.end, margin = localDimens.dp12)
                    end.linkTo(sideBar.start, margin = localDimens.dp15)
                    width = Dimension.fillToConstraints
                }
            )
            if (isReadingSection) {
                ReadingSection(
                    scrollState = scrollState,
                    modifier = Modifier.constrainAs(articleSection) {
                        top.linkTo(titleSection.bottom)
                        start.linkTo(scrollBar.end, margin = localDimens.dp12)

                        end.linkTo(sideBar.start, margin = localDimens.dp5)
                        bottom.linkTo(episodeSection.top)

                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                    text = bodyText
                )
            } else {
                CommentSection(
                    lazyListState = lazyListState,
                    modifier = Modifier.constrainAs(articleSection) {
                        top.linkTo(titleSection.bottom)
                        start.linkTo(scrollBar.end, margin = localDimens.dp12)
                        end.linkTo(sideBar.start, margin = localDimens.dp5)
                        bottom.linkTo(episodeSection.top)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                )
            }
            EpisodeSection(
                paddingValues = PaddingValues(MaterialTheme.localDimens.default),
                modifier = Modifier.constrainAs(episodeSection) {
                    top.linkTo(articleSection.bottom, margin = localDimens.dp20)
                    start.linkTo(articleSection.start)
                    end.linkTo(sideBar.end)
                    bottom.linkTo(parent.bottom, margin = localDimens.dp51)
                    width = Dimension.fillToConstraints
                }
            )
            ShadowBox(
                modifier = Modifier.constrainAs(shadowBox) {
                    top.linkTo(episodeSection.top)
                    bottom.linkTo(episodeSection.bottom)
                    end.linkTo(episodeSection.end)
                    start.linkTo(sideBar.start)
                    height = Dimension.fillToConstraints
                }
            )
            ScrollBar(
                scrollState = scrollState,
                modifier = Modifier.constrainAs(scrollBar) {
                    top.linkTo(articleSection.top)
                    start.linkTo(parent.start, margin = localDimens.dp13)
                    bottom.linkTo(articleSection.bottom)
                    height = Dimension.fillToConstraints
                }
            )
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
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        CroppedImage(
            imgResId = R.drawable.header_image,
            modifier = Modifier
                .fillMaxHeight(0.14f)
                .fillMaxWidth()
        )
        HitReadsTopBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    bottom = MaterialTheme.localDimens.dp5
                ),
            onMenuCLicked = {},
            onNotificationClicked = {},
            iconResId = R.drawable.ic_bell,
            numberOfNotification = numberOfNotification
        )
    }
}

@Composable
fun TitleSection(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Titles(title = title, subtitle = subtitle, modifier = Modifier.weight(1f))
        SimpleIcon(iconResId = R.drawable.ic_star, tint = MaterialTheme.localColors.yellow)
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
    //todo custom selection container is needed
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
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp13),
        contentPadding = paddingValues,
        modifier = modifier
    ) {
        repeat(10) {
            item {
                EpisodeSectionItem(
                    numberOfComment = (it + 12) * it + 1,
                    episodeNumber = it + 11,
                    isSelected = it == 0,
                    hasBanner = it == 0 || it == 2,
                    isLocked = it == 2
                )
            }
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
    hasSmallHeight: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(IntrinsicSize.Min)
    ) {
        SimpleIcon(
            iconResId = R.drawable.ic_menu,
            modifier = Modifier.padding(
                vertical = MaterialTheme.localDimens.dp12,
                horizontal = MaterialTheme.localDimens.dp8
            )
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
            modifier = Modifier.padding(
                vertical = MaterialTheme.localDimens.dp12,
                horizontal = MaterialTheme.localDimens.dp8
            )
        )
        SideBarHorizontalDivider()
    }
}

@Composable
fun SideBarBottomSection(
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
            modifier = Modifier.padding(
                vertical = MaterialTheme.localDimens.dp12,
                horizontal = MaterialTheme.localDimens.dp8
            )
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

@Composable
fun CommentSection(
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {
        CommentSectionTabs()
        LazyColumn(
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp15),
            modifier = Modifier.padding(top = MaterialTheme.localDimens.dp14)
        ) {
            item {
                CommentItem(
                    owner = "SELEN PEKMEZCİ",
                    date = "04/01/2023 20:29",
                    chatSize = 3,
                    isSubComment = false,
                    isLiked = false,
                    isChatSelected = false
                )
            }

            item {
                CommentItem(
                    owner = "SELEN PEKMEZCİ",
                    date = "04/01/2023 20:29",
                    chatSize = 3,
                    isSubComment = false,
                    isLiked = true,
                    isChatSelected = false
                )
            }

            item {
                CommentItem(
                    owner = "SELEN PEKMEZCİ",
                    date = "04/01/2023 20:29",
                    chatSize = 3,
                    isSubComment = false,
                    isLiked = false,
                    isChatSelected = true
                )
            }

            item {
                CommentItem(
                    owner = "SELEN PEKMEZCİ",
                    date = "04/01/2023 20:29",
                    chatSize = 3,
                    isSubComment = true,
                    isLiked = false,
                    isChatSelected = false
                )
            }
            repeat(10) {
                item {
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
}

@Composable
fun CommentSectionTabs() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp9)
    ) {
        Text(
            text = "TÜM YORUMLAR", style = MaterialTheme.localTextStyles.subtitle,
            modifier = Modifier
        )
        Text(text = "BEĞENDİKLERİM", style = MaterialTheme.localTextStyles.interactiveEpisode)
        Text(text = "YORUMLARIM", style = MaterialTheme.localTextStyles.interactiveEpisode)
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
                })
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

@Preview(heightDp = 700)
@Preview(heightDp = 530)
@Preview
@Composable
fun ReadingScreenPreview() {
    ReadingScreen(
        bodyText = LoremIpsum(200).values.joinToString(),
        title = "KİMSE GERÇEK DEĞİL",
        subtitle = "ZEYNEP SEY",
        numberOfComments = 12,
        numberOfViews = 4412,
        numberOfNotification = 14,
        isReadingSection = true,
    )
}

