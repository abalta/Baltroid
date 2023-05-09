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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R
import com.baltroid.ui.common.CroppedImage
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun FavoritesScreen(
    onBackClick: () -> Unit
) {
    FavoritesScreenContent(
        scrollState = rememberScrollState(),
        onBackClick = onBackClick
    )
}

@Composable
fun FavoritesScreenContent(
    scrollState: ScrollState,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .systemBarsPadding()
    ) {
        VerticalSpacer(height = MaterialTheme.localDimens.dp36)
        MenuBar(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            title = stringResource(id = R.string.favorites),
            iconResId = R.drawable.ic_star,
            onBackClick = onBackClick
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp16)
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(id = R.string.stories),
                style = MaterialTheme.localTextStyles.menuBarSubTitle,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp22)
            StoryItemFavoritesList()
            VerticalSpacer(height = MaterialTheme.localDimens.dp25)
            Text(
                text = stringResource(id = R.string.authors),
                style = MaterialTheme.localTextStyles.menuBarSubTitle,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp25)
            AuthorsFavoritesList()
        }
    }
}

@Composable
fun AuthorsItem(
    author: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CroppedImage(
            imgResId = R.drawable.woods_image,
            modifier = Modifier
                .size(MaterialTheme.localDimens.dp111)
                .clip(MaterialTheme.localShapes.circleShape)
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp20)
        Text(
            text = author,
            style = MaterialTheme.localTextStyles.authorText,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp19)
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
                .size(MaterialTheme.localDimens.dp111)
                .clip(MaterialTheme.localShapes.circleShape)
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp20)
        YellowStarBox()
    }
}

@Composable
fun AuthorsFavoritesList(
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = MaterialTheme.localDimens.dp35),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp30)
    ) {
        item { AuthorsItem("ZEYNEP SEY") }
        item { AuthorsItem("ZEYNEP SEY") }
        item { AuthorsItem("ZEYNEP SEY") }
        item { AuthorsItem("ZEYNEP SEY") }
        item { AuthorsItem("ZEYNEP SEY") }
    }
}

@Composable
fun StoryItemFavoritesList(
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = MaterialTheme.localDimens.dp35),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp30)
    ) {
        item { StoryItemFavorites("KİMSE GERÇEK DEĞİL") }
        item { StoryItemFavorites("KİMSE GERÇEK DEĞİL") }
        item { StoryItemFavorites("KİMSE GERÇEK DEĞİL") }
        item { StoryItemFavorites("KİMSE GERÇEK DEĞİL") }
        item { StoryItemFavorites("KİMSE GERÇEK DEĞİL") }
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
                MaterialTheme.localDimens.dp0_5,
                color = MaterialTheme.localColors.white,
                RoundedCornerShape(MaterialTheme.localDimens.dp3)
            )
            .padding(
                vertical = MaterialTheme.localDimens.dp6,
                horizontal = MaterialTheme.localDimens.dp8
            )
    )
}

@Composable
fun StoryItemFavorites(
    title: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        CroppedImage(
            imgResId = R.drawable.woods_image,
            modifier = Modifier
                .size(
                    MaterialTheme.localDimens.dp127,
                    MaterialTheme.localDimens.dp177
                )
                .clip(MaterialTheme.localShapes.roundedDp18)
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp13)
        Text(text = title, style = MaterialTheme.localTextStyles.storyItemTitle)
        VerticalSpacer(height = MaterialTheme.localDimens.dp11)
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

