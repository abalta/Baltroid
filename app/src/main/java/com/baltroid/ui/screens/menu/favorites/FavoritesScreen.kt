package com.baltroid.ui.screens.menu.favorites

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.CroppedImage
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.orZero
import com.hitreads.core.model.Favorite

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getFavoriteAuthors()
        viewModel.getFavoriteEpisodes()
    }

    val favorites = viewModel.uiStateFavorites.collectAsStateWithLifecycle().value
    FavoritesScreenContent(
        scrollState = rememberScrollState(),
        authors = favorites.authors,
        episodes = favorites.episodes,
        onBackClick = onBackClick
    )
}

@Composable
fun FavoritesScreenContent(
    scrollState: ScrollState,
    authors: List<Favorite>,
    episodes: List<Favorite>,
    onBackClick: () -> Unit
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
            Text(
                text = stringResource(id = R.string.stories),
                style = MaterialTheme.localTextStyles.spaceGrotesk20Medium,
                color = MaterialTheme.localColors.white_alpha09,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp22))
            StoryItemFavoritesList(
                episodes = episodes
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp25))
            Text(
                text = stringResource(id = R.string.authors),
                style = MaterialTheme.localTextStyles.spaceGrotesk20Medium,
                color = MaterialTheme.localColors.white_alpha09,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp25))
            AuthorsFavoritesList(
                authors = authors
            )
        }
    }
}

@Composable
fun AuthorsItem(
    favoriteAuthor: Favorite
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
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
        Text(
            text = favoriteAuthor.authorName.orEmpty(),
            style = MaterialTheme.localTextStyles.spaceGrotesk15Medium,
            color = MaterialTheme.localColors.white_alpha09,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp19))
        YellowStarBox()
    }
}

@Composable
fun NamelessAuthorItem(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CroppedImage(
            imgResId = R.drawable.woods_image,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp111))
                .clip(MaterialTheme.localShapes.circleShape)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
        YellowStarBox()
    }
}

@Composable
fun AuthorsFavoritesList(
    authors: List<Favorite>,
    modifier: Modifier = Modifier
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
            AuthorsItem(favoriteAuthor = author)
        }
    }
}

@Composable
fun StoryItemFavoritesList(
    episodes: List<Favorite>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.dp35)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp30))
    ) {
        items(
            episodes,
            key = { it.id.orZero() }
        ) { item ->
            StoryItemFavorites(favoriteEpisode = item)
        }
    }
}

@Composable
fun YellowStarBox(
    modifier: Modifier = Modifier
) {
    SimpleIcon(
        iconResId = R.drawable.ic_star,
        tint = MaterialTheme.localColors.yellow,
        modifier = modifier
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
    favoriteEpisode: Favorite
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        AsyncImage(
            model = favoriteEpisode.assetContents,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.hitreads_placeholder),
            modifier = Modifier
                .size(
                    dimensionResource(id = R.dimen.dp127),
                    dimensionResource(id = R.dimen.dp177)
                )
                .clip(MaterialTheme.localShapes.roundedDp18)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp13))
        Text(
            text = favoriteEpisode.episodeName.orEmpty(),
            style = MaterialTheme.localTextStyles.poppins12Medium,
            color = MaterialTheme.localColors.white_alpha05, textAlign = TextAlign.Center
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp11))
        YellowStarBox(modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen {}
}

