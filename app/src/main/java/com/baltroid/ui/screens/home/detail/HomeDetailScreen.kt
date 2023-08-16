package com.baltroid.ui.screens.home.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.HitReadsTopBar
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.screens.home.GenreSection
import com.baltroid.ui.screens.home.TitleSection
import com.baltroid.ui.screens.viewmodels.OriginalViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.conditional
import com.baltroid.util.orEmpty
import com.baltroid.util.orZero
import com.hitreads.core.model.Author
import com.hitreads.core.model.Original
import com.hitreads.core.model.Tag
import com.hitreads.core.model.UserData

@Composable
fun HomeDetailScreen(
    viewModel: OriginalViewModel,
    navigateBack: () -> Unit,
    openMenuScreen: () -> Unit,
    navigate: (route: String) -> Unit
) {
    HomeDetailScreenContent(
        screenState = viewModel.sharedUIState
            .collectAsStateWithLifecycle().value,
        openMenuScreen = openMenuScreen,
        navigateBack = navigateBack,
        navigate = navigate
    )
}

@Composable
private fun HomeDetailScreenContent(
    screenState: Original?,
    navigateBack: () -> Unit,
    openMenuScreen: () -> Unit,
    navigate: (route: String) -> Unit
) {

    val verticalScrollState = rememberScrollState()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = screenState?.cover,
            contentDescription = null,
            fallback = painterResource(id = R.drawable.hitreads_placeholder),
            placeholder = painterResource(id = R.drawable.hitreads_placeholder),
            error = painterResource(id = R.drawable.hitreads_placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        HitReadsTopBar(
            iconResId = R.drawable.ic_bell,
            numberOfNotification = -1,
            onMenuClick = openMenuScreen,
            onIconClick = navigateBack,
            onNotificationClick = {}
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .conditional(maxHeight >= MaterialTheme.localDimens.minDetailScreenHeight) {
                    height(maxHeight / 1.6f)
                }
                .background(MaterialTheme.localColors.black_alpha05)
                .align(Alignment.BottomCenter)
                .verticalScroll(verticalScrollState)
                .padding(bottom = MaterialTheme.localDimens.dp155)

        ) {
            Text(
                text = screenState?.hashtag.orEmpty(),
                style = MaterialTheme.localTextStyles.hashTag,
                modifier = Modifier.padding(start = MaterialTheme.localDimens.dp17)
            )
            TitleSection(
                author = screenState?.author?.name.orEmpty(),
                title = screenState?.title.orEmpty(),
                subTitle = screenState?.subtitle.orEmpty(),
                modifier = Modifier.padding(start = MaterialTheme.localDimens.dp23)
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp10_5)
            GenreAndInteractions(
                episodeSize = screenState?.episodeCount.orZero(),
                genres = screenState?.tags.orEmpty(),
                numberOfViews = screenState?.viewCount.orZero(),
                numberOfComments = screenState?.commentCount.orZero()
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp20_5)
            HomeDetailSummarySection(
                summary = screenState?.description.orEmpty(),
                modifier = Modifier.padding(start = MaterialTheme.localDimens.dp25)
            ) {
                if (screenState?.type == "interactive") {
                    navigate.invoke(HitReadsScreens.InteractiveScreen.route)
                } else {
                    navigate.invoke(HitReadsScreens.ReadingScreen.route.plus("/${screenState?.id}"))
                }
            }
            if (this@BoxWithConstraints.maxHeight < MaterialTheme.localDimens.minDetailScreenHeight) {
                VerticalSpacer(height = MaterialTheme.localDimens.dp50)
            }
        }
    }
}

@Composable
fun Interactions(
    numberOfViews: Int,
    numberOfComments: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        SimpleIcon(
            iconResId = R.drawable.ic_eye,
            tint = MaterialTheme.localColors.white,
            modifier = Modifier.size(MaterialTheme.localDimens.dp16)
        )
        Text(
            text = numberOfViews.toString(),
            style = MaterialTheme.localTextStyles.summaryIconText
        )
    }
    HorizontalSpacer(width = MaterialTheme.localDimens.dp11_5)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        SimpleIcon(
            iconResId = R.drawable.ic_comment,
            tint = MaterialTheme.localColors.white,
            modifier = Modifier.size(
                MaterialTheme.localDimens.dp16,
                MaterialTheme.localDimens.dp14_5
            )
        )
        Text(
            text = numberOfComments.toString(),
            style = MaterialTheme.localTextStyles.summaryIconText
        )
    }
}

@Composable
fun GenreAndInteractions(
    episodeSize: Int,
    genres: List<Tag>,
    numberOfViews: Int,
    numberOfComments: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        GenreSection(
            episodeSize = episodeSize,
            genres = genres,
            modifier = Modifier.padding(start = MaterialTheme.localDimens.dp22)
        )
        HorizontalSpacer(width = MaterialTheme.localDimens.dp13)
        Interactions(
            numberOfViews = numberOfViews,
            numberOfComments = numberOfComments,
            modifier = Modifier.align(Alignment.Bottom)
        )
    }
}

@Composable
private fun HomeDetailSummarySection(
    summary: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = summary,
            style = MaterialTheme.localTextStyles.detailSummaryText,
            modifier = Modifier.weight(1f)
        )
        HorizontalSpacer(width = MaterialTheme.localDimens.dp9)
        SimpleImage(
            imgResId = R.drawable.ic_arrow_right,
            modifier = Modifier
                .padding(end = MaterialTheme.localDimens.dp33)
                .clickable { onClick.invoke() }
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun Example(
    screenState: Original?
) {
    Box {
        AsyncImage(
            model = "screenState?.cover",
            contentDescription = null,
            fallback = painterResource(id = R.drawable.hitreads_placeholder),
            placeholder = painterResource(id = R.drawable.hitreads_placeholder),
            error = painterResource(id = R.drawable.hitreads_placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (toolbar, image, leftArrow, background, episodes, content) = createRefs()
            HitReadsTopBar(
                iconResId = R.drawable.ic_bell,
                numberOfNotification = -1,
                onMenuClick = {},
                onIconClick = {},
                onNotificationClick = {},
                modifier = Modifier.constrainAs(toolbar) {
                    top.linkTo(parent.top)
                    width = Dimension.matchParent
                }
            )
            Box(
                modifier = Modifier
                    .background(MaterialTheme.localColors.black_alpha05)
                    .constrainAs(background) {
                        bottom.linkTo(parent.bottom)
                        width = Dimension.matchParent
                        height = Dimension.percent(0.7f)
                    }
            )
            AsyncImage(
                model = "screenState?.cover",
                contentDescription = null,
                fallback = painterResource(id = R.drawable.hitreads_placeholder),
                placeholder = painterResource(id = R.drawable.woods_image),
                error = painterResource(id = R.drawable.hitreads_placeholder),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(MaterialTheme.localShapes.roundedDp9)
                    .size(MaterialTheme.localDimens.dp219, MaterialTheme.localDimens.dp308)
                    .constrainAs(image) {
                        top.linkTo(toolbar.bottom, 18.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            SimpleImage(
                imgResId = R.drawable.ic_arrow_left,
                modifier = Modifier.constrainAs(leftArrow) {
                    top.linkTo(background.top)
                    start.linkTo(parent.start)
                    end.linkTo(image.start)
                    bottom.linkTo(image.bottom)
                }
            )
            Column(
                modifier = Modifier.constrainAs(content) {
                    top.linkTo(image.bottom)
                    width = Dimension.matchParent
                    bottom.linkTo(episodes.top, 16.dp)
                    height = Dimension.fillToConstraints
                }
            ) {
                TitleSection(
                    author = screenState?.author?.name.orEmpty(),
                    title = screenState?.title.orEmpty(),
                    subTitle = screenState?.subtitle.orEmpty(),
                    modifier = Modifier.padding(start = MaterialTheme.localDimens.dp23)
                )
                VerticalSpacer(height = MaterialTheme.localDimens.dp10_5)
                GenreAndInteractions(
                    episodeSize = screenState?.episodeCount.orZero(),
                    genres = screenState?.tags.orEmpty(),
                    numberOfViews = screenState?.viewCount.orZero(),
                    numberOfComments = screenState?.commentCount.orZero()
                )
                VerticalSpacer(height = MaterialTheme.localDimens.dp20_5)
                Text(
                    text = screenState?.description.orEmpty(),
                    style = MaterialTheme.localTextStyles.detailSummaryText,
                    modifier = Modifier.padding(start = MaterialTheme.localDimens.dp24)
                )
                VerticalSpacer(height = MaterialTheme.localDimens.dp8)
                Column(
                    modifier = Modifier.padding(start = MaterialTheme.localDimens.dp24)
                ) {
                    Text(
                        text = "#kimsegercekdegil",
                        style = MaterialTheme.localTextStyles.subtitleGrotesk
                    )
                    VerticalSpacer(height = MaterialTheme.localDimens.dp7)
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = MaterialTheme.localDimens.dp47)
                    ) {
                        Row(
                            modifier = Modifier.align(Alignment.Bottom)
                        ) {
                            SimpleIcon(iconResId = R.drawable.ic_lock)
                            Text(
                                text = "tüm kİtabı satın al",
                                style = MaterialTheme.localTextStyles.episodeText
                            )
                        }
                        SimpleImage(imgResId = R.drawable.ic_read)
                    }
                }
            }
            DetailEpisodes(
                modifier = Modifier.constrainAs(episodes) {
                    bottom.linkTo(parent.bottom)
                    width = Dimension.matchParent
                }
            )
        }
    }
}

@Composable
fun DetailEpisodes(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Divider(
            thickness = MaterialTheme.localDimens.dp0_5,
            color = MaterialTheme.localColors.white
        )
        MaterialTheme.colors
        VerticalSpacer(height = MaterialTheme.localDimens.dp14)
        Text(
            text = stringResource(id = R.string.episodes),
            style = MaterialTheme.localTextStyles.mediumTitle
        )
        SimpleImage(imgResId = R.drawable.ic_menu_horizontal)
        VerticalSpacer(height = MaterialTheme.localDimens.dp27)
    }
}

@Preview
@Composable
fun HomeDetailScreenPreview() {
    Example(
        screenState = Original(
            author = Author(id = 8088, name = "Tommy Simmons"),
            banner = "maximus",
            cover = "invenire",
            description = "sinwqeqweqwewqeqwewqsaddasdasdasdsadsadsadsadsadasdasqwewqeqwewqsaddasdasdasdsadsadsadsadsadasdasqwewqeqwewqsaddasdasdasdsadsadsadsadsadasdasqwewqeqwewqsaddasdasdasdsadsadsadsadsadasdasqwewqeqwewqsaddasdasdasdsadsadsadsadsadasdasdasdsadasgulis",
            id = 8163,
            isActual = false,
            isLocked = false,
            likeCount = 5160,
            commentCount = 2613,
            viewCount = 9325,
            `package` = null,
            sort = 9022,
            status = false,
            title = "quaestio",
            type = "saepe",
            userData = UserData(isLike = false, isPurchase = false),
            tags = listOf(Tag(id = 5921, name = "Beatrice Hartman", icon = "luptatum")),
            hashtag = "sanctus",
            subtitle = "vestibulum",
            episodeCount = 7126,
            seasons = listOf(),
            isNew = false,
            dataCount = 6355
        )
    )
}