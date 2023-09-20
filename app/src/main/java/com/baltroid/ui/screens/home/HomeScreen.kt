package com.baltroid.ui.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.SetLoadingState
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.HitReadsTopBar
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.screens.menu.favorites.YellowStarBox
import com.baltroid.ui.screens.viewmodels.OriginalViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.INTERACTIVE
import com.baltroid.util.orEmpty
import com.baltroid.util.orZero
import com.hitreads.core.model.FavoriteOriginal
import com.hitreads.core.model.IndexOriginal
import com.hitreads.core.model.IndexTag
import com.hitreads.core.model.TagsWithOriginals
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: OriginalViewModel,
    openMenuScreen: () -> Unit,
    navigate: (route: String, originalId: Int?) -> Unit
) {
    val homeUiState by viewModel.uiStateHome.collectAsStateWithLifecycle()
    val notificationsSize = viewModel.uiStateNotifications.collectAsStateWithLifecycle().value.size

    SetLoadingState(isLoading = homeUiState.isLoading)

    LaunchedEffect(Unit) {
        viewModel.apply {
            loadOriginals()
            isLogged()
        }
    }

    HomeScreenContent(
        homeUiState = homeUiState,
        openMenuScreen = openMenuScreen,
        deleteFavorite = viewModel::deleteFavoriteHome,
        notificationsSize = notificationsSize,
        navigate = navigate
    )
}

@Composable
fun HomeScreenContent(
    homeUiState: HomeUiState,
    notificationsSize: Int,
    openMenuScreen: () -> Unit,
    deleteFavorite: (id: Int) -> Unit,
    navigate: (route: String, originalId: Int?) -> Unit
) {
    var tabState by rememberSaveable {
        mutableStateOf(HomeScreenTabs.AllStories)
    }

    Column(
        modifier = Modifier.navigationBarsPadding()
    ) {
        HitReadsTopBar(
            iconResId = R.drawable.ic_bell_outlined,
            numberOfNotification = notificationsSize,
            isGemEnabled = true,
            isUserLoggedIn = homeUiState.isUserLoggedIn,
            gemCount = homeUiState.profileModel.gem,
            onMenuClick = openMenuScreen,
            onNotificationClick = {
                navigate.invoke(HitReadsScreens.NotificationsScreen.route, null)
            },
            signInClick = { navigate.invoke(HitReadsScreens.LoginScreen.route, null) },
            gemClick = {}
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp19))
        HomeScreenTabs(
            isUserLoggedIn = homeUiState.isUserLoggedIn,
            selectedTab = tabState, modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.dp32)
                )
        ) { tabState = it }
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp31))
        when (tabState) {
            HomeScreenTabs.AllStories -> {
                IndexOriginals(
                    originals = homeUiState.originals,
                    state = rememberLazyListState(),
                    modifier = Modifier.weight(1f)
                ) {
                    navigate.invoke(HitReadsScreens.HomeDetailScreen.route, it?.id)
                }
            }

            HomeScreenTabs.Favorites -> {
                FavoriteOriginals(
                    originals = homeUiState.favorites, state = rememberLazyListState(),
                    onStarClicked = deleteFavorite
                ) {
                    navigate.invoke(HitReadsScreens.HomeDetailScreen.route, it?.id)
                }
            }

            HomeScreenTabs.ContinueReading -> {
                ContinueReading(
                    indexOriginals = homeUiState.continueReading,
                    state = rememberLazyListState()
                ) {
                    if (it?.type == INTERACTIVE) {
                        navigate.invoke(HitReadsScreens.InteractiveScreen.route, it.id)
                    } else {
                        navigate.invoke(HitReadsScreens.ReadingScreen.route, it?.id)
                    }
                }
            }
        }
    }
}

@Composable
private fun IndexOriginals(
    originals: List<TagsWithOriginals>,
    state: LazyListState,
    modifier: Modifier = Modifier,
    onItemClicked: (IndexOriginal?) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp30)),
    ) {
        itemsIndexed(originals) { index, item ->
            GenreItem(
                text = item.tagName.orEmpty().uppercase(Locale("tr", "TR")),
                if (index % 2 == 0) MaterialTheme.localColors.purple
                else MaterialTheme.localColors.pink,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp24))
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp16))
            LazyRow(
                contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.dp24)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp16))
            ) {
                items(item.indexOriginals.orEmpty()) { original ->
                    IndexOriginalItem(indexOriginal = original) {
                        onItemClicked.invoke(original)
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteOriginals(
    originals: List<FavoriteOriginal>,
    state: LazyListState,
    modifier: Modifier = Modifier,
    onStarClicked: (id: Int) -> Unit,
    onItemClicked: (FavoriteOriginal?) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        state = state,
        contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.dp24)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp30)),
    ) {
        items(originals) { original ->
            FavoriteOriginalItem(
                original = original,
                onStarClicked = { onStarClicked.invoke(original.id.orZero()) }) {
                onItemClicked.invoke(original)
            }
        }
    }
}

@Composable
fun ContinueReading(
    indexOriginals: List<IndexOriginal>,
    state: LazyListState,
    modifier: Modifier = Modifier,
    onItemClicked: (IndexOriginal?) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        state = state,
        contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.dp24)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp30)),
    ) {
        items(indexOriginals) { original ->
            ContinueReadingItem(
                indexOriginal = original
            ) {
                onItemClicked.invoke(original)
            }
        }
    }
}

@Composable
fun ContinueReadingItem(
    indexOriginal: IndexOriginal,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        AsyncImage(
            model = indexOriginal.cover,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.hitreads_placeholder),
            error = painterResource(id = R.drawable.hitreads_placeholder),
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp129), dimensionResource(id = R.dimen.dp180))
                .clip(MaterialTheme.localShapes.roundedDp9)
                .clickable { onClick.invoke() }
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp13))
        Text(
            text = indexOriginal.title,
            style = MaterialTheme.localTextStyles.poppins12Bold,
            color = MaterialTheme.localColors.white,
            maxLines = 2,
            minLines = 2,
            textAlign = TextAlign.Center,
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp12))
        Text(
            text = stringResource(
                id = R.string.episode_number,
                indexOriginal.continueReadingEpisode?.episodeSort.orZero()
            ),
            style = MaterialTheme.localTextStyles.poppins12Regular,
            color = MaterialTheme.localColors.white
        )
    }
}

@Composable
private fun IndexOriginalItem(
    indexOriginal: IndexOriginal,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .height(dimensionResource(id = R.dimen.dp244))
            .clickable { onClick.invoke() }
    ) {
        Box {
            SubcomposeAsyncImage(
                model = indexOriginal.cover,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                loading = { CircularProgressIndicator() },
                error = { SimpleImage(imgResId = R.drawable.hitreads_placeholder) },
                modifier = Modifier
                    .size(
                        dimensionResource(id = R.dimen.dp129),
                        dimensionResource(id = R.dimen.dp180)
                    )
                    .clip(MaterialTheme.localShapes.roundedDp9)
            )
            if (indexOriginal.isNew) {
                Text(
                    text = stringResource(id = R.string.new_),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(
                            bottom = dimensionResource(id = R.dimen.dp12),
                            end = dimensionResource(id = R.dimen.dp14)
                        )
                        .clip(MaterialTheme.localShapes.roundedDp4)
                        .background(MaterialTheme.localColors.orange)
                        .padding(
                            vertical = dimensionResource(id = R.dimen.dp6),
                            horizontal = dimensionResource(id = R.dimen.dp10)
                        )
                )
            }
        }
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp6))
        Text(
            text = indexOriginal.title,
            style = MaterialTheme.localTextStyles.poppins12Bold,
            color = MaterialTheme.localColors.white,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = indexOriginal.indexAuthor.name,
            style = MaterialTheme.localTextStyles.poppins10SemiBold,
            color = MaterialTheme.localColors.white
        )
    }
}

@Composable
fun FavoriteOriginalItem(
    original: FavoriteOriginal,
    onStarClicked: () -> Unit,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        AsyncImage(
            model = original.cover,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.hitreads_placeholder),
            error = painterResource(id = R.drawable.hitreads_placeholder),
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp129), dimensionResource(id = R.dimen.dp180))
                .clip(MaterialTheme.localShapes.roundedDp9)
                .clickable { onClick.invoke() }
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp13))
        Text(
            text = original.title.orEmpty(),
            style = MaterialTheme.localTextStyles.poppins12Bold,
            color = MaterialTheme.localColors.white,
            maxLines = 2,
            minLines = 2,
            textAlign = TextAlign.Center,
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp28))
        YellowStarBox {
            onStarClicked.invoke()
        }
    }
}

@Composable
private fun HomeScreenTabs(
    selectedTab: HomeScreenTabs,
    isUserLoggedIn: Boolean,
    modifier: Modifier = Modifier,
    onTabSelected: (HomeScreenTabs) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp34)),
        modifier = modifier.horizontalScroll(rememberScrollState())
    ) {
        HomeScreenTabItem(
            title = R.string.all_stories,
            isSelected = selectedTab == HomeScreenTabs.AllStories
        ) {
            onTabSelected.invoke(HomeScreenTabs.AllStories)
        }
        if (isUserLoggedIn) {
            HomeScreenTabItem(
                title = R.string.favorites_with_size,
                isSelected = selectedTab == HomeScreenTabs.Favorites
            ) {
                onTabSelected.invoke(HomeScreenTabs.Favorites)
            }
            HomeScreenTabItem(
                title = R.string.continue_reading,
                isSelected = selectedTab == HomeScreenTabs.ContinueReading
            ) {
                onTabSelected.invoke(HomeScreenTabs.ContinueReading)
            }
        }
    }
}

enum class HomeScreenTabs {
    AllStories,
    Favorites,
    ContinueReading
}

@Composable
private fun HomeScreenTabItem(
    @StringRes title: Int,
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
            text = stringResource(id = title),
            style = MaterialTheme.localTextStyles.spaceGrotesk13Medium,
            color = MaterialTheme.localColors.white_alpha07,
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(bottom = dimensionResource(id = R.dimen.dp2))

        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dp4))
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


@Composable
fun TitleSection(
    author: String,
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
    onAuthorClicked: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = author,
            style = MaterialTheme.localTextStyles.poppins17Medium,
            color = MaterialTheme.localColors.white,
            modifier = Modifier.clickable(onClick = onAuthorClicked)
        )
        Text(
            text = title,
            style = MaterialTheme.localTextStyles.poppins20ExtraBold,
            color = MaterialTheme.localColors.white
        )
        Text(
            text = subTitle,
            style = MaterialTheme.localTextStyles.spaceGrotesk14Regular,
            color = MaterialTheme.localColors.white
        )
    }
}

@Composable
fun GenreSection(
    episodeSize: Int,
    genres: List<IndexTag>?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        genres?.firstOrNull()?.let {
            GenreItem(
                it.name,
                color = MaterialTheme.localColors.purple
            )
            HorizontalSpacer(width = dimensionResource(id = R.dimen.dp19))
        }
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .align(Alignment.Bottom)
        ) {
            Text(
                text = stringResource(id = R.string.episode_size_detail, episodeSize),
                style = MaterialTheme.localTextStyles.poppins12Regular,
                color = MaterialTheme.localColors.white,
                modifier = Modifier.width(IntrinsicSize.Max)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.dp4))
                    .clip(MaterialTheme.localShapes.roundedDp3)
                    .background(MaterialTheme.localColors.white_alpha08)
            )
        }
    }
}

@Composable
fun GenreItem(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.localTextStyles.spaceGrotesk11Medium,
        color = MaterialTheme.localColors.black,
        modifier = modifier
            .clip(MaterialTheme.localShapes.roundedDp4)
            .background(color)
            .padding(
                vertical = dimensionResource(id = R.dimen.dp6),
                horizontal = dimensionResource(id = R.dimen.dp10)
            )
    )
}

/*@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StoriesRow(
    lazyPagingItems: List<Original>,
    state: PagerState,
    modifier: Modifier = Modifier,
    onClick: (Original?) -> Unit,
) {
    BoxWithConstraints {
        HorizontalPager(
            pageCount = lazyPagingItems.size,
            state = state,
            contentPadding = PaddingValues(
                start = dimensionResource(id = R.dimen.dp25),
                end = this.maxWidth - dimensionResource(id = R.dimen.dp24)9 - dimensionResource(id = R.dimen.dp25)
            ),
            pageSpacing = dimensionResource(id = R.dimen.dp31),
            pageSize = PageSize.Fixed(dimensionResource(id = R.dimen.dp24)9),
            modifier = modifier,
        ) { page ->
            StoryImage(
                imgUrl = lazyPagingItems[page].cover.toString(),
                isNew = lazyPagingItems[page].isNew ?: false,
                onClick = { onClick.invoke(lazyPagingItems[page]) }
            )
        }
    }
}*/

/*@Composable
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
            fallback = painterResource(id = R.drawable.hitreads_placeholder),
            placeholder = painterResource(id = R.drawable.hitreads_placeholder),
            error = painterResource(id = R.drawable.hitreads_placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(
                    dimensionResource(id = R.dimen.dp24)9,
                    dimensionResource(id = R.dimen.dp34)8
                )
                .clip(MaterialTheme.localShapes.roundedDp20)
        )
        if (isNew) {
            Text(
                text = stringResource(id = R.string.new_),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = dimensionResource(id = R.dimen.dp12),
                        end = dimensionResource(id = R.dimen.dp14)
                    )
                    .clip(MaterialTheme.localShapes.roundedDp4)
                    .background(MaterialTheme.localColors.orange)
                    .padding(
                        vertical = 6.dp,
                        horizontal = MaterialTheme.localDimens.dp10
                    )
            )
        }
    }
}*/

/*@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(
    uiStates: List<Original>,
    uiStatesFavorites: List<Original>,
    openMenuScreen: () -> Unit,
    navigate: (route: String, item: Original?) -> Unit
) {
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var tabState by rememberSaveable {
        mutableStateOf(HomeScreenTabs.AllStories)
    }

    val currentItem by remember(
        uiStates.size,
        uiStatesFavorites.size,
        tabState,
        pagerState.currentPage
    ) {
        when (tabState) {
            HomeScreenTabs.AllStories -> {
                if (uiStates.isNotEmpty()) mutableStateOf(uiStates[pagerState.currentPage])
                else mutableStateOf<Original?>(null)
            }

            HomeScreenTabs.Favorites -> {
                if (uiStatesFavorites.isNotEmpty()) mutableStateOf(uiStatesFavorites[pagerState.currentPage])
                else mutableStateOf<Original?>(null)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
    ) {
        HitReadsTopBar(
            iconResId = R.drawable.ic_bell_outlined,
            iconTint = MaterialTheme.localColors.white,
            numberOfNotification = -1,
            onNotificationClick = {},
            isGemEnabled = true,
            onIconClick = {
                navigate.invoke(HitReadsScreens.OnboardingScreen.route, null)
            },
            gemCount = 4500,
            onMenuClick = openMenuScreen
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp18)_5)
        HomeScreenTabs(
            storiesSize = uiStates.firstOrNull()?.dataCount.orZero(),
            favoritesSize = uiStatesFavorites.firstOrNull()?.dataCount.orZero(),
            selectedTab = tabState,
            modifier = Modifier.padding(start = MaterialTheme.localDimens.dp32)
        ) { selectedTab ->
            scope.launch { pagerState.scrollToPage(0) }
            tabState = selectedTab
        }
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp14))
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            StoriesRow(
                state = pagerState,
                lazyPagingItems = if (tabState == HomeScreenTabs.AllStories) uiStates else uiStatesFavorites,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            ) { original ->
                navigate.invoke(HitReadsScreens.HomeDetailScreen.route, original)
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp28)_5)
            if (uiStates.isNotEmpty()) {
                TitleSection(
                    author = currentItem?.author?.name.toString(),
                    title = currentItem?.title.toString(),
                    subTitle = currentItem?.subtitle.toString(),
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp23))
                )
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
                GenreSection(
                    genres = currentItem?.tags,
                    episodeSize = currentItem?.episodeCount ?: 0,
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp23))
                )
                SummarySection(
                    summary = currentItem?.description.toString(),
                    numberOfViews = currentItem?.viewCount.orZero(),
                    numberOfComments = currentItem?.commentCount.orZero(),
                    onCommentsClick = {},
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.dp9),
                        start = dimensionResource(id = R.dimen.dp25),
                        end = MaterialTheme.localDimens.dp39
                    )
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.dp24))
        ) {
            Text(
                text = stringResource(id = R.string.story_summary, currentItem?.dataCount.orZero()),
                style = MaterialTheme.localTextStyles.summaryInfoText
            )
            SimpleIcon(
                iconResId = R.drawable.ic_list,
                modifier = Modifier
                    .clickable { navigate.invoke(HitReadsScreens.FilterScreen.route, null) }
            )
        }
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp24))
    }
}*/

/*@Composable
private fun SummarySection(
    summary: String,
    numberOfViews: Int,
    numberOfComments: Int,
    modifier: Modifier = Modifier,
    onCommentsClick: () -> Unit,
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
    }
}*/

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(
        homeUiState = HomeUiState(
            originals = listOf(),
            favorites = listOf(),
            error = null
        ),
        openMenuScreen = {},
        deleteFavorite = {},
        notificationsSize = 0,
        navigate = { _: String, _: Int? -> }
    )
}