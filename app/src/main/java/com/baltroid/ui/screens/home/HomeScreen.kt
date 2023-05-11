package com.baltroid.ui.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.HitReadsTopBar
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun HomeScreen(
    screenState: HomeScreenState,
    openMenuScreen: () -> Unit,
    navigate: (route: String, itemId: Int?) -> Unit
) {
    HomeScreenContent(
        screenState = screenState,
        openMenuScreen = openMenuScreen,
        navigate = navigate
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(
    screenState: HomeScreenState,
    openMenuScreen: () -> Unit,
    navigate: (route: String, itemId: Int?) -> Unit
) {
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState()
    var tabState by rememberSaveable {
        mutableStateOf(HomeScreenTabs.AllStories)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
    ) {
        HitReadsTopBar(
            iconResId = R.drawable.ic_bell_outlined,
            iconTint = MaterialTheme.localColors.white,
            numberOfNotification = screenState.numberOfNotification,
            onNotificationClick = {},
            onIconClick = {
                navigate.invoke(HitReadsScreens.OnboardingScreen.route, null)
            },
            onMenuClick = openMenuScreen
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp18_5)
        HomeScreenTabs(
            storiesSize = screenState.numberOfStory,
            favoritesSize = screenState.numberOfFavorites,
            selectedTab = tabState,
            modifier = Modifier.padding(start = MaterialTheme.localDimens.dp32)
        ) { selectedTab ->
            tabState = selectedTab
        }
        VerticalSpacer(height = MaterialTheme.localDimens.dp14)
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            if (tabState == HomeScreenTabs.AllStories) {
                StoriesRow(
                    state = pagerState,
                    imgUrls = screenState.imgUrls,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                ) { id ->
                    if (id == 1) {
                        navigate.invoke(HitReadsScreens.InteractiveScreen.route, id)
                    } else {
                        navigate.invoke(HitReadsScreens.HomeDetailScreen.route, id)
                    }
                }
            } else {
                /*todo Favorites*/
            }
            VerticalSpacer(height = MaterialTheme.localDimens.dp28_5)
            TitleSection(
                author = screenState.author,
                firstName = screenState.firstName,
                secondName = screenState.secondName,
                modifier = Modifier.padding(start = MaterialTheme.localDimens.dp23)
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp20)
            GenreSection(
                genres = screenState.genres,
                episodeSize = screenState.episodeSize,
                modifier = Modifier.padding(start = MaterialTheme.localDimens.dp23)
            )
            SummarySection(
                summary = screenState.summary,
                numberOfStory = screenState.numberOfStory,
                numberOfViews = screenState.numberOfViews,
                numberOfComments = screenState.numberOfComments,
                onCommentsClick = {},
                onFilterClick = { navigate.invoke(HitReadsScreens.FilterScreen.route, null) },
                modifier = Modifier.padding(
                    top = MaterialTheme.localDimens.dp9,
                    start = MaterialTheme.localDimens.dp25,
                    end = MaterialTheme.localDimens.dp39
                )
            )
            VerticalSpacer(
                height = MaterialTheme.localDimens.dp50,
                modifier = Modifier.navigationBarsPadding()
            )
        }
    }
}

@Composable
private fun HomeScreenTabs(
    storiesSize: Int,
    favoritesSize: Int,
    selectedTab: HomeScreenTabs,
    modifier: Modifier = Modifier,
    onTabSelected: (HomeScreenTabs) -> Unit
) {
    Row(
        modifier = modifier
    ) {
        HomeScreenTabItem(
            title = R.string.all_stories,
            size = storiesSize,
            isSelected = selectedTab == HomeScreenTabs.AllStories
        ) {
            onTabSelected.invoke(HomeScreenTabs.AllStories)
        }
        HorizontalSpacer(width = MaterialTheme.localDimens.dp28)
        HomeScreenTabItem(
            title = R.string.favorites_with_size,
            size = favoritesSize,
            isSelected = selectedTab == HomeScreenTabs.Favorites
        ) {
            onTabSelected.invoke(HomeScreenTabs.Favorites)
        }
    }
}

enum class HomeScreenTabs {
    AllStories,
    Favorites
}

@Composable
private fun HomeScreenTabItem(
    @StringRes title: Int,
    size: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Min)
            .clickable { onClick.invoke() }
    ) {
        Text(
            text = stringResource(id = title, size),
            style = MaterialTheme.localTextStyles.homeScreenTabText,
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(bottom = MaterialTheme.localDimens.dp6_5)

        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.localDimens.dp4)
                .clip(MaterialTheme.localShapes.roundedDp3)
                .background(
                    if (isSelected) {
                        MaterialTheme.localColors.purple_alpha08
                    } else {
                        MaterialTheme.localColors.transparent
                    }
                )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StoriesRow(
    imgUrls: List<String>,
    state: PagerState,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit,
) {
    BoxWithConstraints {
        HorizontalPager(
            pageCount = imgUrls.size,
            state = state,
            contentPadding = PaddingValues(
                start = MaterialTheme.localDimens.dp25,
                end = this.maxWidth - MaterialTheme.localDimens.dp249 - MaterialTheme.localDimens.dp25
            ),
            pageSpacing = MaterialTheme.localDimens.dp31,
            pageSize = PageSize.Fixed(MaterialTheme.localDimens.dp249),
            modifier = modifier,
        ) { page ->
            StoryImage(
                imgUrl = imgUrls[page],
                isNew = true,
                onClick = { onClick.invoke(page) }
            )
        }
    }
}

@Composable
private fun StoryImage(
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
    Row(
        modifier = modifier
    ) {
        GenreItem(genres.first(), color = MaterialTheme.localColors.purple)
        HorizontalSpacer(width = MaterialTheme.localDimens.dp10_5)
        GenreItem(text = genres[1], color = MaterialTheme.localColors.pink)
        HorizontalSpacer(width = MaterialTheme.localDimens.dp12_5)
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .align(Alignment.Bottom)
        ) {
            Text(
                text = stringResource(id = R.string.episode_size, episodeSize),
                style = MaterialTheme.localTextStyles.episodeText,
                modifier = Modifier.width(IntrinsicSize.Max)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.localDimens.dp4)
                    .clip(MaterialTheme.localShapes.roundedDp3)
                    .background(MaterialTheme.localColors.white_alpha08)
            )
        }
    }
}

@Composable
fun GenreItem(
    text: String,
    color: Color
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.localTextStyles.isStoryNewText,
        modifier = Modifier
            .clip(MaterialTheme.localShapes.roundedDp4)
            .background(color)
            .padding(
                vertical = MaterialTheme.localDimens.dp6,
                horizontal = MaterialTheme.localDimens.dp10
            )
    )
}

@Composable
private fun SummarySection(
    summary: String,
    numberOfStory: Int,
    numberOfViews: Int,
    numberOfComments: Int,
    modifier: Modifier = Modifier,
    onCommentsClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    val localDimens = MaterialTheme.localDimens

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (summaryText, views, comments, info, listIcon) = createRefs()

        Text(
            text = summary,
            style = MaterialTheme.localTextStyles.subtitleGrotesk,
            modifier = Modifier
                .heightIn(min = localDimens.dp50)
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
            modifier = Modifier
                .clickable { onCommentsClick.invoke() }
                .constrainAs(comments) {
                    start.linkTo(views.start)
                    end.linkTo(views.end)
                    top.linkTo(views.bottom, margin = localDimens.dp8)
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
                end.linkTo(listIcon.start, localDimens.dp26)
                width = Dimension.fillToConstraints
            }
        )
        SimpleIcon(
            iconResId = R.drawable.ic_list,
            modifier = Modifier
                .clickable { onFilterClick.invoke() }
                .constrainAs(listIcon) {
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
        screenState = HomeScreenState(
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
            summary = LoremIpsum(16).values.joinToString(),
            imgUrls = listOf(""),
            selectedFilters = listOf()
        ),
        openMenuScreen = {}
    ) { _, _ -> }
}