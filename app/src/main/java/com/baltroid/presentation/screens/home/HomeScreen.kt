package com.baltroid.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.presentation.common.HorizontalSpacer
import com.baltroid.presentation.common.SimpleIcon
import com.baltroid.presentation.common.VerticalSpacer
import com.baltroid.presentation.components.HitReadsTopBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun HomeScreen(
    author: String,
    firstName: String,
    secondName: String,
    summary: String,
    genres: List<String>,
    numberOfNotification: Int,
    numberOfStory: Int,
    numberOfViews: Int,
    numberOfComments: Int,
    numberOfFavorites: Int,
    episodeSize: Int
) {

    val lazyListState = rememberLazyListState()
    val scrollState = rememberScrollState()
    val tabState by remember {
        mutableStateOf(
            HomeScreenTabState(
                allStories = HomeScreenTabInfo(numberOfStory, true),
                favorites = HomeScreenTabInfo(numberOfFavorites, false)
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.localColors.black)
    ) {
        HitReadsTopBar(
            iconResId = R.drawable.ic_bell_outlined,
            iconTint = MaterialTheme.localColors.white,
            modifier = Modifier.padding(
                start = MaterialTheme.localDimens.dp32,
                end = MaterialTheme.localDimens.dp32,
                top = MaterialTheme.localDimens.dp12,
            ),
            numberOfNotification = numberOfNotification,
            onMenuCLicked = {},
            onNotificationClicked = {}
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp18_5)
        HomeScreenTabs(
            modifier = Modifier.padding(start = MaterialTheme.localDimens.dp32),
            tabState = tabState
        ) {}
        VerticalSpacer(height = MaterialTheme.localDimens.dp14)
        StoriesRow(
            modifier = Modifier.padding(start = MaterialTheme.localDimens.dp25),
            lazyListState = lazyListState
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp28_5)
        TitleSection(
            author = author,
            firstName = firstName,
            secondName = secondName,
            modifier = Modifier.padding(start = MaterialTheme.localDimens.dp23)
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp20)
        GenreSection(
            genres = genres,
            episodeSize = episodeSize,
            modifier = Modifier.padding(start = MaterialTheme.localDimens.dp23)
        )
        SummarySection(
            summary = summary,
            numberOfStory = numberOfStory,
            numberOfViews,
            numberOfComments
        )
        VerticalSpacer(
            height = MaterialTheme.localDimens.dp50,
            modifier = Modifier.navigationBarsPadding()
        )
    }
}

@Composable
fun HomeScreenTabs(
    tabState: HomeScreenTabState,
    modifier: Modifier = Modifier,
    onTabSelected: (HomeScreenTabs) -> Unit
) {
    val localColors = MaterialTheme.localColors
    val localDimens = MaterialTheme.localDimens

    Row(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.all_episodes, tabState.allStories.contentSize),
            style = MaterialTheme.localTextStyles.homeScreenTabText,
            modifier = Modifier
                .drawBehind {
                    if (tabState.allStories.isSelected) {
                        val cylinderHeight = localDimens.dp4.toPx()
                        drawRoundRect(
                            localColors.purple,
                            size = Size(size.width, cylinderHeight),
                            cornerRadius = CornerRadius(localDimens.dp3.toPx()),
                            topLeft = Offset(
                                localDimens.dp2.toPx(),
                                size.height - cylinderHeight
                            )
                        )
                    }
                }
                .clickable { onTabSelected.invoke(HomeScreenTabs.AllStories) }
                .padding(bottom = MaterialTheme.localDimens.dp6_5)

        )
        HorizontalSpacer(width = MaterialTheme.localDimens.dp28)
        Text(
            text = stringResource(
                id = R.string.favorites_with_size,
                tabState.favorites.contentSize
            ),
            style = MaterialTheme.localTextStyles.homeScreenTabText,
            modifier = Modifier
                .drawBehind {
                    if (tabState.favorites.isSelected) {
                        drawRoundRect(
                            localColors.purple,
                            size = Size(size.width, localDimens.dp4.toPx()),
                            cornerRadius = CornerRadius(localDimens.dp3.toPx()),
                            topLeft = Offset(
                                localDimens.dp2.toPx(),
                                size.height - localDimens.dp4.toPx()
                            )
                        )
                    }
                }
                .clickable { onTabSelected.invoke(HomeScreenTabs.Favorites) }
                .padding(bottom = MaterialTheme.localDimens.dp6_5)
        )
    }
}

@Composable
fun StoriesRow(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
) {
    LazyRow(
        state = lazyListState,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp31),
    ) {
        item {
            StoryImage(
                imgUrl = "https://www.figma.com/file/MYxJBHOTh2JfbmrYbojuxc/image/1d56515ab14098684701024283a07d386bbb94e7?fuid=1097272770330818914",
                isNew = true
            ) {}
        }
        item {
            StoryImage(
                imgUrl = "https://www.figma.com/file/MYxJBHOTh2JfbmrYbojuxc/image/d7ac3769304cdecbbe3a0ff5b327d15512547d7e?fuid=1097272770330818914",
                isNew = false
            ) {}
        }
    }
}

@Composable
fun StoryImage(
    imgUrl: String,
    isNew: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.clickable { onClick.invoke() }
    ) {
        AsyncImage(
            model = imgUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(
                    MaterialTheme.localDimens.dp249,
                    MaterialTheme.localDimens.dp348
                )
                .clip(MaterialTheme.localShapes.roundedDp20)
        )
        if (isNew) {
            Text(
                text = stringResource(id = R.string.new_),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = MaterialTheme.localDimens.dp12,
                        end = MaterialTheme.localDimens.dp14
                    )
                    .clip(MaterialTheme.localShapes.roundedDp4)
                    .background(MaterialTheme.localColors.orange)
                    .padding(
                        vertical = MaterialTheme.localDimens.dp6,
                        horizontal = MaterialTheme.localDimens.dp10
                    )
            )
        }
    }
}

@Composable
fun TitleSection(
    author: String,
    firstName: String,
    secondName: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(text = author, style = MaterialTheme.localTextStyles.mediumTitle)
        Text(text = firstName, style = MaterialTheme.localTextStyles.extraBoldTitle)
        Text(text = secondName, style = MaterialTheme.localTextStyles.subtitleGrotesk)
    }
}

@Composable
fun GenreSection(
    episodeSize: Int,
    genres: List<String>,
    modifier: Modifier = Modifier
) {
    val firstGenre = genres.first()
    val secondGenre = genres[1]
    val localColors = MaterialTheme.localColors
    val localDimens = MaterialTheme.localDimens
    Row(
        modifier = modifier
    ) {
        Text(
            text = firstGenre,
            style = MaterialTheme.localTextStyles.isStoryNewText,
            modifier = Modifier
                .clip(MaterialTheme.localShapes.roundedDp4)
                .background(MaterialTheme.localColors.purple)
                .padding(
                    vertical = MaterialTheme.localDimens.dp6,
                    horizontal = MaterialTheme.localDimens.dp10
                )
        )
        HorizontalSpacer(width = MaterialTheme.localDimens.dp10_5)
        Text(
            text = secondGenre, style = MaterialTheme.localTextStyles.isStoryNewText,
            modifier = Modifier
                .clip(MaterialTheme.localShapes.roundedDp4)
                .background(MaterialTheme.localColors.pink)
                .padding(
                    vertical = MaterialTheme.localDimens.dp6,
                    horizontal = MaterialTheme.localDimens.dp10
                )
        )
        HorizontalSpacer(width = MaterialTheme.localDimens.dp12_5)
        Text(
            text = stringResource(id = R.string.episode_size, episodeSize),
            style = MaterialTheme.localTextStyles.episodeText,
            modifier = Modifier
                .align(Alignment.Bottom)
                .drawBehind {
                    val roundRectHeight = localDimens.dp4.toPx()
                    drawRoundRect(
                        color = localColors.white_alpha08,
                        size = Size(width = size.width, height = roundRectHeight),
                        cornerRadius = CornerRadius(localDimens.dp3.toPx()),
                        topLeft = Offset(0f, size.height - roundRectHeight)
                    )
                }
                .padding(bottom = 0.5.dp)
        )
    }
}

@Composable
fun SummarySection(
    summary: String,
    numberOfStory: Int,
    numberOfViews: Int,
    numberOfComments: Int,
) {
    val localDimens = MaterialTheme.localDimens
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = MaterialTheme.localDimens.dp9,
                start = MaterialTheme.localDimens.dp25,
                end = MaterialTheme.localDimens.dp39
            )
    ) {
        val (summaryText, views, comments, info, listIcon) = createRefs()

        Text(
            text = summary,
            style = MaterialTheme.localTextStyles.subtitleGrotesk,
            maxLines = 4,
            modifier = Modifier
                .constrainAs(summaryText) {
                    top.linkTo(parent.top, margin = localDimens.dp9)
                    start.linkTo(parent.start)
                    end.linkTo(views.start, margin = localDimens.dp17)
                    width = Dimension.fillToConstraints
                }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.constrainAs(views) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        ) {
            SimpleIcon(
                iconResId = R.drawable.ic_eye,
                tint = MaterialTheme.localColors.white
            )
            Text(
                text = numberOfViews.toString(),
                style = MaterialTheme.localTextStyles.summaryIconText
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.constrainAs(comments) {
                start.linkTo(views.start)
                end.linkTo(views.end)
                bottom.linkTo(summaryText.bottom)
            }
        ) {
            SimpleIcon(
                iconResId = R.drawable.ic_comment,
                tint = MaterialTheme.localColors.white,
            )
            Text(
                text = numberOfComments.toString(),
                style = MaterialTheme.localTextStyles.summaryIconText
            )
        }
        Text(
            text = stringResource(id = R.string.story_summary, numberOfStory),
            style = MaterialTheme.localTextStyles.summaryInfoText,
            modifier = Modifier.constrainAs(info) {
                top.linkTo(summaryText.bottom, localDimens.dp36)
                start.linkTo(parent.start)
                end.linkTo(listIcon.start)
                width = Dimension.fillToConstraints
            }
        )
        SimpleIcon(
            iconResId = R.drawable.ic_list,
            modifier = Modifier.constrainAs(listIcon) {
                top.linkTo(info.top)
                bottom.linkTo(info.bottom)
                start.linkTo(comments.start)
                end.linkTo(comments.end)
            }
        )
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        author = "ZEYNEP SEY",
        firstName = "KİMSE GERÇEK DEĞİL",
        secondName = "Araf, Aydınlık Ve Aşık",
        genres = listOf("ROMANTİK", "GENÇLİK"),
        numberOfNotification = 12,
        numberOfStory = 12,
        numberOfViews = 1002,
        numberOfComments = 142,
        numberOfFavorites = 5,
        episodeSize = 35,
        summary = "Kim olduğunu sorguladıkça dünyasının sahtelikten İbaret olduğunu anlamaya başlayan Işıl Özsoydan, öğrendiği gerçekleri..."
    )
}

data class HomeScreenTabState(
    val allStories: HomeScreenTabInfo,
    val favorites: HomeScreenTabInfo
)

data class HomeScreenTabInfo(
    val contentSize: Int,
    val isSelected: Boolean
)

enum class HomeScreenTabs {
    AllStories,
    Favorites
}
