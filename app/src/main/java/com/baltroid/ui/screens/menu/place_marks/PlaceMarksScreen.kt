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
import androidx.compose.foundation.rememberScrollState
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
fun PlaceMarksScreen(
    onBackClick: () -> Unit
) {
    PlaceMarsScreenContent(
        scrollState = rememberScrollState(),
        onBackClick = onBackClick
    )
}

@Composable
fun PlaceMarsScreenContent(
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
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.place_marks),
            iconResId = R.drawable.ic_banner_filled,
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
            VerticalSpacer(height = MaterialTheme.localDimens.dp16)
            StoryItemList(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MaterialTheme.localDimens.dp35)
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp18_5)
            Text(
                text = stringResource(id = R.string.marks),
                style = MaterialTheme.localTextStyles.menuBarSubTitle,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp21)
            MarkItemList(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MaterialTheme.localDimens.dp35)
            )
        }
    }
}

@Composable
fun StoryItemList(
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp29)
    ) {
        item { StoryItem("KİMSE GERÇEK DEĞİL", 25) }
        item { StoryItem("KİMSE GERÇEK DEĞİL", 25) }
        item { StoryItem("KİMSE GERÇEK DEĞİL", 25) }
        item { StoryItem("KİMSE GERÇEK DEĞİL", 25) }
        item { StoryItem("KİMSE GERÇEK DEĞİL", 25) }
    }
}

@Composable
fun MarkItemList(
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp29)
    ) {
        item { MarkItem("KİMSE GERÇEK DEĞİL", 25) }
        item { MarkItem("KİMSE GERÇEK DEĞİL", 25) }
        item { MarkItem("KİMSE GERÇEK DEĞİL", 25) }
        item { MarkItem("KİMSE GERÇEK DEĞİL", 25) }
        item { MarkItem("KİMSE GERÇEK DEĞİL", 25) }
    }
}

@Composable
fun StoryItem(
    title: String,
    episodeNumber: Int
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {

            Text(
                text = stringResource(id = R.string.episode_size, episodeNumber),
                style = MaterialTheme.localTextStyles.episodeSelectedText,
                modifier = Modifier.width(IntrinsicSize.Max)
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp5)
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
        modifier = modifier.height(MaterialTheme.localDimens.dp17)
    )
}

@Composable
fun MarkItem(
    title: String,
    episodeNumber: Int
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
        Column(
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.episode_size, episodeNumber),
                    style = MaterialTheme.localTextStyles.episodeSelectedText,
                    modifier = Modifier.width(IntrinsicSize.Max)
                )
            }
            VerticalSpacer(height = MaterialTheme.localDimens.dp7)
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
            .height(MaterialTheme.localDimens.dp8)
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