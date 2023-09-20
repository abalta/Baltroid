package com.baltroid.ui.screens.menu.favorites

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.SetLoadingState
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.orZero
import com.hitreads.core.model.Favorite
import com.hitreads.core.model.FavoriteOriginal

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navigate: (route: String, id: Int?) -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getFavoriteAuthors()
        viewModel.getFavoriteEpisodes()
    }

    val favorites by viewModel.uiStateFavorites.collectAsStateWithLifecycle()
    SetLoadingState(isLoading = favorites.isLoading)

    FavoritesScreenContent(
        scrollState = rememberScrollState(),
        authors = favorites.authors,
        originals = favorites.originals,
        onBackClick = onBackClick,
        navigate = navigate,
        removeFavoriteAuthor = viewModel::deleteFavoriteAuthor,
        removeFavoriteOriginal = viewModel::deleteFavoriteOriginal
    )
}

@Composable
fun FavoritesScreenContent(
    scrollState: ScrollState,
    authors: List<Favorite>,
    originals: List<FavoriteOriginal>,
    removeFavoriteAuthor: (Int) -> Unit,
    removeFavoriteOriginal: (Int) -> Unit,
    onBackClick: () -> Unit,
    navigate: (route: String, id: Int?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .systemBarsPadding()
    ) {
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp36))
        MenuBar(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            title = stringResource(id = R.string.favorites),
            iconResId = R.drawable.ic_star,
            onBackClick = onBackClick
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp16))
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            if (originals.isNotEmpty()) {
                Text(
                    text = stringResource(id = R.string.stories),
                    style = MaterialTheme.localTextStyles.spaceGrotesk20Medium,
                    color = MaterialTheme.localColors.white_alpha09,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp22))
                StoryItemFavoritesList(
                    originals = originals,
                    onItemClick = {
                        navigate.invoke(HitReadsScreens.HomeDetailScreen.route, it)
                    },
                    removeFavoriteOriginal = removeFavoriteOriginal
                )
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp25))
            }
            if (authors.isNotEmpty()) {
                Text(
                    text = stringResource(id = R.string.authors),
                    style = MaterialTheme.localTextStyles.spaceGrotesk20Medium,
                    color = MaterialTheme.localColors.white_alpha09,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp25))
                AuthorsFavoritesList(
                    authors = authors,
                    onItemClick = {
                        navigate.invoke(HitReadsScreens.AuthorScreen.route + "/$it", null)
                    },
                    removeFavoriteAuthor = removeFavoriteAuthor
                )
            }
        }
    }
}

@Composable
fun AuthorsItem(
    favoriteAuthor: Favorite,
    onClick: () -> Unit,
    onRemoveFavorite: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = favoriteAuthor.image,
            error = painterResource(id = R.drawable.hitreads_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp111))
                .clip(MaterialTheme.localShapes.circleShape)
                .clickable(onClick = onClick)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
        Text(
            text = favoriteAuthor.authorName.orEmpty(),
            style = MaterialTheme.localTextStyles.spaceGrotesk15Medium,
            color = MaterialTheme.localColors.white_alpha09,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp19))
        YellowStarBox(onClick = onRemoveFavorite)
    }
}

@Composable
fun NamelessAuthorItem(
    imgUrl: String?,
    isFavorite: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = imgUrl,
            error = painterResource(id = R.drawable.hitreads_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp111))
                .clip(MaterialTheme.localShapes.circleShape)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
        if (isFavorite) {
            YellowStarBox {

            }
        }
    }
}

@Composable
fun AuthorsFavoritesList(
    authors: List<Favorite>,
    modifier: Modifier = Modifier,
    onItemClick: (id: Int) -> Unit,
    removeFavoriteAuthor: (Int) -> Unit
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.dp35)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp30))
    ) {
        items(
            authors,
            key = { it.id.orZero() }
        ) { author ->
            AuthorsItem(
                favoriteAuthor = author,
                onClick = { onItemClick.invoke(author.id ?: -1) },
                onRemoveFavorite = {
                    removeFavoriteAuthor.invoke(author.id ?: -1)
                }
            )
        }
    }
}

@Composable
fun StoryItemFavoritesList(
    originals: List<FavoriteOriginal>,
    modifier: Modifier = Modifier,
    onItemClick: (id: Int) -> Unit,
    removeFavoriteOriginal: (id: Int) -> Unit
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.dp35)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp30))
    ) {
        items(
            originals,
            key = { it.id.orZero() }
        ) { item ->
            StoryItemFavorites(
                favoriteOriginal = item,
                onClick = { onItemClick.invoke(item.id) }
            ) {
                removeFavoriteOriginal.invoke(item.id)
            }
        }
    }
}

@Composable
fun YellowStarBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    SimpleIcon(
        iconResId = R.drawable.ic_star,
        tint = MaterialTheme.localColors.yellow,
        modifier = modifier
            .clickable { onClick.invoke() }
            .border(
                dimensionResource(id = R.dimen.dp1),
                color = MaterialTheme.localColors.white,
                RoundedCornerShape(3.dp)
            )
            .padding(
                vertical = 6.dp,
                horizontal = 8.dp
            )
    )
}

@Composable
fun StoryItemFavorites(
    favoriteOriginal: FavoriteOriginal,
    onClick: () -> Unit,
    removeFavoriteOriginal: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        AsyncImage(
            model = favoriteOriginal.cover,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.hitreads_placeholder),
            modifier = Modifier
                .size(
                    dimensionResource(id = R.dimen.dp127),
                    dimensionResource(id = R.dimen.dp177)
                )
                .clip(MaterialTheme.localShapes.roundedDp18)
                .clickable(onClick = onClick)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp13))
        Text(
            text = favoriteOriginal.title,
            style = MaterialTheme.localTextStyles.poppins12Medium,
            color = MaterialTheme.localColors.white_alpha05,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp11))
        YellowStarBox(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = removeFavoriteOriginal
        )
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun FavoritesScreenPreview() {

}

