package com.baltroid.ui.screens.menu.place_marks

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.hitreads.core.domain.model.BookmarkModel
import com.hitreads.core.model.Bookmark

@Composable
fun PlaceMarksScreen(
    viewModel: BookmarkViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    PlaceMarsScreenContent(
        state = viewModel.bookMarks.collectAsStateWithLifecycle().value.bookmarks,
        scrollState = rememberScrollState(),
        onBackClick = onBackClick
    )
}

@Composable
fun PlaceMarsScreenContent(
    state: List<Bookmark>,
    scrollState: ScrollState,
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
            title = stringResource(id = R.string.place_marks),
            iconResId = R.drawable.ic_banner_filled,
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
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp16))
            StoryItemList(
                state,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimensionResource(id = R.dimen.dp35))
            )
            /* VerticalSpacer(height = dimensionResource(id = R.dimen.dp18)_5)
             Text(
                 text = stringResource(id = R.string.marks),
                 style = MaterialTheme.localTextStyles.menuBarSubTitle,
                 modifier = Modifier.align(Alignment.CenterHorizontally)
             )
             VerticalSpacer(height = dimensionResource(id = R.dimen.dp21))
             MarkItemList(
                 state,
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(start = dimensionResource(id = R.dimen.dp35))
             )*/
        }
    }
}

@Composable
fun StoryItemList(
    state: List<Bookmark>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp29))
    ) {
        items(state) { item ->
            StoryItem(
                title = item.original?.title.orEmpty(),
                imgUrl = item.cover,
                episodeNumber = item.episode?.id.toString()
            )
        }
    }
}

@Composable
fun MarkItemList(
    state: List<BookmarkModel>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp29))
    ) {
        items(state) { item ->
            MarkItem(
                title = item.original?.title.orEmpty(),
                imgUrl = item.cover,
                episodeNumber = item.episode?.id.toString()
            )
        }
    }
}

@Composable
fun StoryItem(
    title: String,
    imgUrl: String,
    episodeNumber: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        AsyncImage(
            model = imgUrl,
            contentDescription = null,
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
            text = title,
            style = MaterialTheme.localTextStyles.poppins12Medium,
            color = MaterialTheme.localColors.white_alpha05,
            textAlign = TextAlign.Center
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {

            Text(
                text = stringResource(id = R.string.episode_size, episodeNumber),
                style = MaterialTheme.localTextStyles.spaceGrotesk16Medium,
                color = MaterialTheme.localColors.white,
                modifier = Modifier.width(IntrinsicSize.Max)
            )
            VerticalSpacer(height = 5.dp)
            EpisodeBanner(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun EpisodeBanner(
    modifier: Modifier
) {
    SimpleIcon(
        iconResId = R.drawable.ic_banner_long,
        modifier = modifier.height(dimensionResource(id = R.dimen.dp17))
    )
}

@Composable
fun MarkItem(
    title: String,
    imgUrl: String,
    episodeNumber: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        AsyncImage(
            model = imgUrl,
            contentDescription = null,
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
            text = title,
            style = MaterialTheme.localTextStyles.poppins12Medium,
            color = MaterialTheme.localColors.white_alpha05,
            textAlign = TextAlign.Center
        )
        Column(
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.episode_size, episodeNumber),
                    style = MaterialTheme.localTextStyles.spaceGrotesk16Medium,
                    color = MaterialTheme.localColors.white,
                    modifier = Modifier.width(IntrinsicSize.Max)
                )
            }
            VerticalSpacer(height = 7.dp)
            YellowBar(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun YellowBar(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .height(8.dp)
            .background(MaterialTheme.localColors.gold)
    )
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun PlaceMarkScreenPreview() {
    PlaceMarksScreen {}
}