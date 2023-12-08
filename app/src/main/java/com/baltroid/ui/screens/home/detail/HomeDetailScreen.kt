package com.baltroid.ui.screens.home.detail

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.SetLoadingState
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.common.showLoginToast
import com.baltroid.ui.components.HitReadsTopBar
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.screens.home.GenreSection
import com.baltroid.ui.screens.home.TitleSection
import com.baltroid.ui.screens.interactive.EpisodeButton
import com.baltroid.ui.screens.viewmodels.OriginalViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.orEmpty
import com.baltroid.util.orZero
import com.hitreads.core.domain.model.OriginalType
import com.hitreads.core.model.IndexOriginal
import com.hitreads.core.model.IndexTag
import com.hitreads.core.model.ShowEpisode
import java.util.Locale

@Composable
fun HomeDetailScreen(
    onSessionExpired: () -> Unit,
    viewModel: OriginalViewModel,
    openMenuScreen: () -> Unit,
    navigate: (route: String, episodeId: Int?) -> Unit
) {
    val detailUIState by viewModel.uiStateDetail.collectAsStateWithLifecycle()
    val homeState by viewModel.uiStateHome.collectAsStateWithLifecycle()
    val notificationSize = viewModel.uiStateNotifications.collectAsStateWithLifecycle().value.size
    val context = LocalContext.current

    SetLoadingState(isLoading = detailUIState.isLoading)

    LaunchedEffect(Unit) {
        viewModel.showOriginal()
    }

    LaunchedEffect(viewModel.isSessionExpired) {
        if (viewModel.isSessionExpired) {
            onSessionExpired.invoke()
        }
    }

    LaunchedEffect(detailUIState.originalPurchased) {
        if (detailUIState.originalPurchased == true) {
            Toast.makeText(
                context,
                context.getString(R.string.original_purchased),
                Toast.LENGTH_LONG
            ).show()
        }
        if (detailUIState.originalPurchased == false) {
            Toast.makeText(
                context,
                context.getString(R.string.something_went_wrong),
                Toast.LENGTH_LONG
            ).show()
        }
        viewModel.clearPurchaseState()
    }

    LaunchedEffect(detailUIState.episodePurchased) {
        if (detailUIState.episodePurchased == true) {
            Toast.makeText(
                context,
                context.getString(R.string.episode_purchased),
                Toast.LENGTH_LONG
            ).show()
        }
        if (detailUIState.episodePurchased == false) {
            Toast.makeText(
                context,
                context.getString(R.string.something_went_wrong),
                Toast.LENGTH_LONG
            ).show()
        }
        viewModel.clearEpisodePurchaseState()
    }

    HomeDetailScreenContent(
        navigate = navigate,
        state = detailUIState,
        openMenuScreen = openMenuScreen,
        notificationSize = if (homeState.isUserLoggedIn) notificationSize else 0,
        isLoggedIn = homeState.isUserLoggedIn,
        bulkPurchase = viewModel::bulkPurchase,
        purchaseEpisode = viewModel::purchaseEpisode,
        selectedEpisodeToPurchase = viewModel.selectedEpisodeToPurchase,
        setSelectedEpisodeToPurchase = viewModel::setSelectedEpisodeToPurchaseModel,
    )
}

@Composable
private fun HomeDetailScreenContent(
    isLoggedIn: Boolean,
    notificationSize: Int,
    state: HomeDetailUIState,
    bulkPurchase: () -> Unit,
    openMenuScreen: () -> Unit,
    purchaseEpisode: (ShowEpisode) -> Unit,
    selectedEpisodeToPurchase: ShowEpisode?,
    navigate: (route: String, episodeId: Int?) -> Unit,
    setSelectedEpisodeToPurchase: (ShowEpisode?) -> Unit,
) {

    var isEpisodesEnabled by rememberSaveable {
        mutableStateOf(false)
    }
    var isBarcodeEnabled by rememberSaveable {
        mutableStateOf(false)
    }
    var isEpisodePurchaseDialogVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val context = LocalContext.current

    BackHandler(
        enabled = isEpisodesEnabled
    ) { isEpisodesEnabled = false }

    BackHandler(
        enabled = isBarcodeEnabled
    ) { isBarcodeEnabled = false }

    Box(
        modifier = Modifier
            .navigationBarsPadding()
    ) {
        AsyncImage(
            model = state.original.cover,
            contentDescription = null,
            fallback = painterResource(id = R.drawable.woods_image),
            placeholder = painterResource(id = R.drawable.woods_image),
            error = painterResource(id = R.drawable.woods_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(10.dp)
                .clickable(interactionSource = interactionSource, indication = null) {
                    isEpisodesEnabled = false
                }
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.localColors.black_alpha05)
        )

        if (!isEpisodesEnabled) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.localColors.black_alpha05)
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f)
                    .align(Alignment.BottomCenter)
            )
        }
        if (isBarcodeEnabled) {
            OriginalBarcode(original = state.original) {
                isBarcodeEnabled = false
            }
        }
        when {
            !isEpisodesEnabled && !isBarcodeEnabled -> {
                Column {
                    HitReadsTopBar(
                        iconResId = R.drawable.ic_bell,
                        numberOfNotification = notificationSize,
                        onMenuClick = openMenuScreen,
                        onIconClick = {},
                        iconTint = MaterialTheme.localColors.white,
                        onNotificationClick = {
                            if (isLoggedIn) {
                                navigate.invoke(HitReadsScreens.NotificationsScreen.route, null)
                            } else {
                                context.showLoginToast()
                            }
                        },
                        gemClick = {},
                        signInClick = {},
                    )
                    VerticalSpacer(height = dimensionResource(id = R.dimen.dp18))
                    AsyncImage(
                        model = state.original.cover,
                        contentDescription = null,
                        fallback = painterResource(id = R.drawable.hitreads_placeholder),
                        placeholder = painterResource(id = R.drawable.woods_image),
                        error = painterResource(id = R.drawable.hitreads_placeholder),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .size(
                                dimensionResource(id = R.dimen.dp219),
                                dimensionResource(id = R.dimen.dp308)
                            )
                            .scale(0.9f)
                            .clip(MaterialTheme.localShapes.roundedDp9)
                            .clickable {
                                val episode = state.original.episodes.firstOrNull()
                                if (episode?.isReadable == true) {
                                    if (!episode.isPurchase) {
                                        isEpisodePurchaseDialogVisible = true
                                        setSelectedEpisodeToPurchase.invoke(episode)
                                    } else {
                                        navigate.invoke(
                                            if (state.original.type == OriginalType.INTERACTIVE) HitReadsScreens.InteractiveScreen.route
                                            else HitReadsScreens.ReadingScreen.route,
                                            state.original.episodes.firstOrNull()?.id
                                        )
                                    }
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            context.getString(R.string.isnot_readable),
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                }
                            }
                    )
                    VerticalSpacer(height = dimensionResource(id = R.dimen.dp16))
                    Column(
                        Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        TitleSection(
                            author = state.original.indexAuthor.name.orEmpty()
                                .uppercase(Locale.getDefault()),
                            title = state.original.title.orEmpty().uppercase(Locale.getDefault()),
                            subTitle = state.original.subtitle.orEmpty(),
                            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp23))
                        ) {
                            navigate.invoke(
                                HitReadsScreens.AuthorScreen.route + "/${state.original.indexAuthor.id}",
                                null
                            )
                        }
                        VerticalSpacer(height = dimensionResource(id = R.dimen.dp11))
                        Column(
                            Modifier
                                .padding(start = dimensionResource(id = R.dimen.dp24))
                        ) {
                            GenreAndInteractions(
                                episodeSize = state.original.episodes.size,
                                genres = state.original.indexTags,
                                numberOfViews = state.original.viewCount.orZero(),
                                numberOfComments = state.original.commentCount.orZero(),
                                onInfoClicked = { isBarcodeEnabled = !isBarcodeEnabled },
                                modifier = Modifier.width(IntrinsicSize.Max)
                            )
                            VerticalSpacer(height = dimensionResource(id = R.dimen.dp21))
                            Row(
                                Modifier.padding(end = dimensionResource(id = R.dimen.dp24))
                            ) {
                                Text(
                                    text = state.original.description.orEmpty(),
                                    style = MaterialTheme.localTextStyles.spaceGrotesk14Regular,
                                    color = MaterialTheme.localColors.white,
                                    modifier = Modifier.weight(1f),
                                )
                                SimpleImage(
                                    imgResId = R.drawable.ic_read,
                                    modifier = Modifier
                                        .padding(
                                            top = dimensionResource(id = R.dimen.dp24),
                                            start = dimensionResource(
                                                id = R.dimen.dp11
                                            )
                                        )
                                        .wrapContentWidth(Alignment.End)
                                        .clickable {
                                            val episode = state.original.episodes.firstOrNull()
                                            if (episode?.isReadable == true) {
                                                if (!episode.isPurchase) {
                                                    isEpisodePurchaseDialogVisible = true
                                                    setSelectedEpisodeToPurchase.invoke(episode)
                                                } else {
                                                    navigate.invoke(
                                                        if (state.original.type == OriginalType.INTERACTIVE) HitReadsScreens.InteractiveScreen.route
                                                        else HitReadsScreens.ReadingScreen.route,
                                                        state.original.episodes.firstOrNull()?.id
                                                    )
                                                }
                                            } else {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        context.getString(R.string.isnot_readable),
                                                        Toast.LENGTH_LONG
                                                    )
                                                    .show()
                                            }
                                        })
                            }
                            VerticalSpacer(height = dimensionResource(id = R.dimen.dp8))
                            Text(
                                text = state.original.hashtag.orEmpty(),
                                style = MaterialTheme.localTextStyles.spaceGrotesk14Bold,
                                color = MaterialTheme.localColors.white
                            )
                        }
                        VerticalSpacer(height = dimensionResource(id = R.dimen.dp7))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = dimensionResource(id = R.dimen.dp24),
                                    end = dimensionResource(id = R.dimen.dp47)
                                )
                        ) {
                            if (state.original.isBulkPurchasable) {
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.Bottom)
                                ) {

                                    SimpleIcon(iconResId = R.drawable.ic_lock)
                                    HorizontalSpacer(width = dimensionResource(id = R.dimen.dp8))

                                    Text(
                                        text = stringResource(id = R.string.purchase_all_book),
                                        style = MaterialTheme.localTextStyles.poppins12Regular,
                                        color = MaterialTheme.localColors.white,
                                        modifier = Modifier.clickable {
                                            bulkPurchase.invoke()
                                        }
                                    )
                                }
                            }
                        }
                        VerticalSpacer(height = dimensionResource(id = R.dimen.dp30))
                    }
                    DetailEpisodes {
                        isEpisodesEnabled = true
                    }
                }
            }

            isEpisodesEnabled -> {
                EpisodeSheet(
                    episodes = state.original.episodes,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                        .background(MaterialTheme.localColors.black_alpha05)
                        .align(Alignment.BottomCenter),
                    closeSheet = {
                        isEpisodesEnabled = false
                    },
                    onEpisodeClick = {
                        if (it.isReadable) {
                            if (!it.isPurchase) {
                                isEpisodePurchaseDialogVisible = true
                                setSelectedEpisodeToPurchase.invoke(it)
                            } else {
                                navigate.invoke(
                                    if (state.original.type == OriginalType.INTERACTIVE) {
                                        HitReadsScreens.InteractiveScreen.route
                                    } else {
                                        HitReadsScreens.ReadingScreen.route
                                    },
                                    it.id
                                )
                            }
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.isnot_readable),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                )
            }
        }
        if (isEpisodePurchaseDialogVisible) {
            EpisodePurchasePopup(
                episodeName = selectedEpisodeToPurchase?.episodeName.toString(),
                onDialogDismissed = { isEpisodePurchaseDialogVisible = false },
                onAccept = {
                    selectedEpisodeToPurchase?.let { purchaseEpisode(it) }
                    isEpisodePurchaseDialogVisible = false
                },
                onDecline = {
                    isEpisodePurchaseDialogVisible = false
                }
            )
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
            modifier = Modifier.size(dimensionResource(id = R.dimen.dp16))
        )
        Text(
            text = numberOfViews.toString(),
            style = MaterialTheme.localTextStyles.poppins10SemiBold,
            color = MaterialTheme.localColors.white
        )
    }
    HorizontalSpacer(width = dimensionResource(id = R.dimen.dp12))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        SimpleIcon(
            iconResId = R.drawable.ic_comment,
            tint = MaterialTheme.localColors.white,
            modifier = Modifier.size(
                dimensionResource(id = R.dimen.dp16),
                dimensionResource(id = R.dimen.dp15)
            )
        )
        Text(
            text = numberOfComments.toString(),
            style = MaterialTheme.localTextStyles.poppins10SemiBold,
            color = MaterialTheme.localColors.white,
        )
    }
}

@Composable
fun GenreAndInteractions(
    episodeSize: Int,
    genres: List<IndexTag>,
    numberOfViews: Int,
    numberOfComments: Int,
    onInfoClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GenreSection(
            episodeSize = episodeSize,
            genres = genres,
        )
        HorizontalSpacer(width = dimensionResource(id = R.dimen.dp13))
        Interactions(
            numberOfViews = numberOfViews,
            numberOfComments = numberOfComments,
            modifier = Modifier.align(Alignment.Bottom)
        )
        HorizontalSpacer(width = dimensionResource(id = R.dimen.dp15))
        SimpleIcon(iconResId = R.drawable.ic_info, modifier = Modifier.clickable {
            onInfoClicked.invoke()
        })
    }
}

@Composable
fun EpisodeSheet(
    episodes: List<ShowEpisode>,
    modifier: Modifier = Modifier,
    lazyColumnHeightFraction: Float = 1f,
    closeSheet: () -> Unit,
    onEpisodeClick: (ShowEpisode) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp19))
        Text(
            text = stringResource(id = R.string.episodes),
            style = MaterialTheme.localTextStyles.poppins17Regular,
            color = MaterialTheme.localColors.white_black,
            modifier = Modifier.clickable { closeSheet.invoke() }
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp6))
        SimpleIcon(
            iconResId = R.drawable.ic_menu_horizontal,
            tint = MaterialTheme.localColors.white_bb,
            modifier = Modifier.clickable { closeSheet.invoke() }
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp33))
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(lazyColumnHeightFraction)
        ) {
            items(episodes) { episode ->
                EpisodeItem(episode = episode) {
                    onEpisodeClick.invoke(it)
                }
                Divider(
                    color = MaterialTheme.localColors.white_alpha03,
                    thickness = dimensionResource(id = R.dimen.dp1)
                )
            }
        }
    }
}

@Composable
fun DetailEpisodes(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick.invoke() }
    ) {
        Divider(
            thickness = dimensionResource(id = R.dimen.dp1),
            color = MaterialTheme.localColors.white_alpha03
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp14))
        Text(
            text = stringResource(id = R.string.episodes),
            style = MaterialTheme.localTextStyles.poppins17Medium,
            color = MaterialTheme.localColors.white
        )
        SimpleImage(imgResId = R.drawable.ic_menu_horizontal)
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp27))
    }
}

@Composable
fun EpisodeItem(
    episode: ShowEpisode,
    showEpisodeName: Boolean = true,
    onClick: (ShowEpisode) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick.invoke(episode) }
            .padding(
                start = dimensionResource(id = R.dimen.dp28),
                end = dimensionResource(id = R.dimen.dp22),
                top = dimensionResource(id = R.dimen.dp12),
                bottom = dimensionResource(id = R.dimen.dp12)
            )
    ) {
        Text(
            text = stringResource(id = R.string.episode_number, episode.episodeSort.orZero()),
            style = MaterialTheme.localTextStyles.poppins15Medium,
            color = MaterialTheme.localColors.white
        )
        HorizontalSpacer(width = dimensionResource(id = R.dimen.dp20))
        if (showEpisodeName) {
            Text(
                text = episode.episodeName.orEmpty(),
                style = MaterialTheme.localTextStyles.spaceGrotesk14Regular,
                color = MaterialTheme.localColors.white,
                modifier = Modifier.weight(1f)
            )
        }
        if (!episode.isReadable) {
            SimpleIcon(
                iconResId = R.drawable.ic_lock,
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.dp22))
            )
        }
    }
}

@Composable
fun OriginalBarcode(
    original: IndexOriginal?,
    onCloseClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .padding(horizontal = dimensionResource(id = R.dimen.dp22))
            .fillMaxSize()
            .clip(MaterialTheme.localShapes.roundedDp20)
            .background(MaterialTheme.localColors.white)
    ) {
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp43))
        SimpleImage(
            imgResId = R.drawable.ic_circular_close,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
                .padding(end = dimensionResource(id = R.dimen.dp24))
                .clickable { onCloseClicked.invoke() }
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp30))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .width(IntrinsicSize.Min)
        ) {
            AsyncImage(
                model = original?.cover,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(
                        dimensionResource(id = R.dimen.dp269),
                        dimensionResource(id = R.dimen.dp376)
                    )
                    .clip(MaterialTheme.localShapes.roundedDp20)
                    .align(Alignment.CenterHorizontally)
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp30))
            Text(
                text = stringResource(id = R.string.barcode),
                style = MaterialTheme.localTextStyles.poppins20ExtraBold,
                color = MaterialTheme.localColors.black
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp2))
            Text(
                text = original?.barcode.orEmpty(),
                style = MaterialTheme.localTextStyles.spaceGrotesk14Regular,
                color = MaterialTheme.localColors.black,
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp7))
            Text(
                text = stringResource(id = R.string.summary),
                style = MaterialTheme.localTextStyles.poppins20ExtraBold,
                color = MaterialTheme.localColors.black,
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp3))
            Text(
                text = original?.description.orEmpty(),
                style = MaterialTheme.localTextStyles.spaceGrotesk14Regular,
                color = MaterialTheme.localColors.black,
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        }
    }
}

@Composable
fun EpisodePurchasePopup(
    episodeName: String,
    onDialogDismissed: () -> Unit,
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    Dialog(onDismissRequest = { onDialogDismissed.invoke() }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.localShapes.roundedDp24)
                .background(MaterialTheme.localColors.black)
                .border(
                    dimensionResource(id = R.dimen.dp1),
                    MaterialTheme.localColors.white,
                    MaterialTheme.localShapes.roundedDp24
                )
        ) {
            VerticalSpacer(height = R.dimen.dp24)
            Text(
                text = stringResource(id = R.string.buy_episode, episodeName),
                style = MaterialTheme.localTextStyles.poppins18Regular,
                color = MaterialTheme.localColors.white,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.dp24))
            )
            VerticalSpacer(height = R.dimen.dp24)
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.dp12),
                    Alignment.CenterHorizontally
                )
            ) {
                EpisodeButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = dimensionResource(id = R.dimen.dp24)),
                    buttonTitle = stringResource(id = R.string.decline)
                ) {
                    onDecline.invoke()
                }
                EpisodeButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = dimensionResource(id = R.dimen.dp24)),
                    buttonTitle = stringResource(id = R.string.buy)
                ) {
                    onAccept.invoke()
                }
            }
            VerticalSpacer(height = R.dimen.dp24)
        }
    }
}

@Preview
@Composable
fun HomeDetailScreenPreview() {
    /*HomeDetailScreenContent(
        screenState = Original(
            author = Author(id = 8088, name = "Tommy Simmons"),
            banner = "maximus",
            cover = "invenire",
            description = "as dsadas dsadasndsadas dsadas dsadass dsadasndsadas dsadas dsadass dsadasndsadas dsadas ddas dsadass dsadasndsadas dsadas dsadass dsadasndsadas dsadas ddas dsadass dsadasndsadas dsadas dsadass dsadasndsadas dsadas ddas dsadass dsadasndsadas dsadas dsadass dsadasndsadas dsadas ddas dsadass dsadasndsadas dsdas dsadass dsadasndsadas dsadas dsadass dsadasndsadas dsadas ddas dsadass dsadasndsadas dsadas dsadass dsadasndsadas dsadas ddas dsadass dsadasndsadas dsadas dsadass dsadasndsadas dsadas ddas dsadass dsadasndsadas dsadas dsadass dsadasndsadas dsadas dadas dsadass dsadasndsadas dsadas dsadass dsadasndsadas dsadas dsadas",
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
    )*/
}

/*@Composable
private fun HomeDetailScreenContent(
    screenState: original,
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
                .padding(bottom = dimensionResource(id = R.dimen.dp15)5)

        ) {
            Text(
                text = screenState?.hashtag.orEmpty(),
                style = MaterialTheme.localTextStyles.hashTag,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp17))
            )
            TitleSection(
                author = screenState?.author?.name.orEmpty(),
                title = screenState?.title.orEmpty(),
                subTitle = screenState?.subtitle.orEmpty(),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp23))
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp10_5)
            GenreAndInteractions(
                episodeSize = screenState?.episodeCount.orZero(),
                genres = screenState?.tags.orEmpty(),
                numberOfViews = screenState?.viewCount.orZero(),
                numberOfComments = screenState?.commentCount.orZero()
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp20)_5)
            HomeDetailSummarySection(
                summary = screenState?.description.orEmpty(),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp25))
            ) {
                if (screenState?.type == "interactive") {
                    navigate.invoke(HitReadsScreens.InteractiveScreen.route)
                } else {
                    navigate.invoke(HitReadsScreens.ReadingScreen.route.plus("/${screenState?.id}"))
                }
            }
            if (this@BoxWithConstraints.maxHeight < MaterialTheme.localDimens.minDetailScreenHeight) {
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp50))
            }
        }
    }
}*/

/*Box {
    AsyncImage(
        model = "screenState?.cover",
        contentDescription = null,
        fallback = painterResource(id = R.drawable.woods_image),
        placeholder = painterResource(id = R.drawable.woods_image),
        error = painterResource(id = R.drawable.woods_image),
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black_alpha05)
    )
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (toolbar, image, background, episodes, content) = createRefs()
        HitReadsTopBar(
            iconResId = R.drawable.ic_bell,
            numberOfNotification = -1,
            onMenuClick = {},
            onIconClick = {},
            iconTint = MaterialTheme.localColors.white,
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
                .size(dimensionResource(id = R.dimen.dp21)9, dimensionResource(id = R.dimen.dp30)8)
                .constrainAs(image) {
                    top.linkTo(toolbar.bottom, 18.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
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
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp23))
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp10_5)
            GenreAndInteractions(
                episodeSize = screenState?.episodeCount.orZero(),
                genres = screenState?.tags.orEmpty(),
                numberOfViews = screenState?.viewCount.orZero(),
                numberOfComments = screenState?.commentCount.orZero(),
                modifier = Modifier
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp20)_5)
            Text(
                text = screenState?.description.orEmpty(),
                style = MaterialTheme.localTextStyles.detailSummaryText,
                modifier = Modifier,
            )
            VerticalSpacer(height = 8.dp)
            Column(
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp24))
            ) {
                Text(
                    text = "#kimsegercekdegil",
                    style = MaterialTheme.localTextStyles.subtitleGrotesk
                )
                VerticalSpacer(height = 7.dp)
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
}*/

/*@Composable
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
            style = MaterialTheme.localTextStyles.spaceGrotesk14Regular,
            color = MaterialTheme.localColors.white,
            modifier = Modifier.weight(1f)
        )
        HorizontalSpacer(width = dimensionResource(id = R.dimen.dp9))
        SimpleImage(
            imgResId = R.drawable.ic_arrow_right,
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.dp33))
                .clickable { onClick.invoke() }
                .align(Alignment.CenterVertically)
        )
    }
}*/