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
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.compose.LazyPagingItems
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
import com.hitreads.core.model.Original

@Composable
fun HomeScreen(
    uiStates: LazyPagingItems<Original>,
    openMenuScreen: () -> Unit,
    navigate: (route: String, itemId: Int?) -> Unit
) {
    HomeScreenContent(uiStates = uiStates, openMenuScreen = openMenuScreen, navigate = navigate)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(
    uiStates: LazyPagingItems<Original>,
    openMenuScreen: () -> Unit,
    navigate: (route: String, itemId: Int?) -> Unit
) {
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState()
    var tabState by rememberSaveable {
        mutableStateOf(HomeScreenTabs.AllStories)
    }
    var currentItem by remember(uiStates) {
        if (uiStates.itemCount > 0) mutableStateOf(uiStates[pagerState.currentPage])
        else mutableStateOf<Original?>(null)
    }

    if (uiStates.itemCount > 0) {
        currentItem = uiStates[pagerState.currentPage]
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
    ) {
        HitReadsTopBar(
            iconResId = R.drawable.ic_bell_outlined,
            iconTint = MaterialTheme.localColors.white,
            numberOfNotification = 12,
            onNotificationClick = {},
            onIconClick = {
                navigate.invoke(HitReadsScreens.OnboardingScreen.route, null)
            },
            onMenuClick = openMenuScreen
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp18_5)
        HomeScreenTabs(
            storiesSize = 12,
            favoritesSize = 12,
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
                    lazyPagingItems = uiStates,
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
            if (uiStates.itemCount > 0) {
                TitleSection(
                    author = currentItem?.author?.name.toString(),
                    firstName = currentItem?.title.toString(),
                    secondName = "Araf,Aydınlık Ve Aşık",
                    modifier = Modifier.padding(start = MaterialTheme.localDimens.dp23)
                )
                VerticalSpacer(height = MaterialTheme.localDimens.dp20)
                GenreSection(
                    genres = listOf("Romantik", "Gençlik"),
                    episodeSize = currentItem?.episodeCount ?: 0,
                    modifier = Modifier.padding(start = MaterialTheme.localDimens.dp23)
                )
                SummarySection(
                    summary = currentItem?.description.toString(),
                    numberOfStory = 12,
                    numberOfViews = 12,
                    numberOfComments = 12,
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
    lazyPagingItems: LazyPagingItems<Original>,
    state: PagerState,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit,
) {
    BoxWithConstraints {
        HorizontalPager(
            pageCount = lazyPagingItems.itemCount,
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
                imgUrl = lazyPagingItems[page]?.cover.toString(),
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
            model = "https://s3-alpha-sig.figma.com/img/1d56/515a/b14098684701024283a07d386bbb94e7?Expires=1685923200&Signature=c~Ac5-s57idL6G3UdD9fjGazuBxO0oAkOiexIunJhrpPPjdKaXUuMc5u2Jl3FAhQcu4rnM1Vgg-ho59SPzRxE0D~1~vv5iExIEO6bv35dpAUVEwjqixsQSKVc-0W6vzXnFM9Fn0luDUimIPcGC5T0Gm-GGi1YtibF6iCbe3v3Mb~EqruH~h0O43YMh91DUX5rvgle5Q8t2HVPbcJM4kU5o~sjrz04At1N6~33Ghp9YvWbPWkOlFufoaKF59QgRmAHesgc45nRkO6oqXWjBL5YyCT-tRW75meJNlZUjZQE--RmGPPl1rjCRrqB1mR0C1tA4oUnmntCRfg6fHF441Jlw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
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
            maxLines = 4,
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

}