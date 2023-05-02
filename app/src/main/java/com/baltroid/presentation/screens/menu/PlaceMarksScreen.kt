package com.baltroid.presentation.screens.menu

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R
import com.baltroid.presentation.common.CroppedImage
import com.baltroid.presentation.common.SimpleIcon
import com.baltroid.presentation.common.VerticalSpacer
import com.baltroid.presentation.components.MenuBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun PlaceMarksScreen() {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .verticalScroll(scrollState)
            .navigationBarsPadding()
    ) {
        VerticalSpacer(height = MaterialTheme.localDimens.dp36)
        MenuBar(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.place_marks),
            iconResId = R.drawable.ic_banner_filled
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp16)
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
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.episode_size, episodeNumber),
                    style = MaterialTheme.localTextStyles.episodeSelectedText,
                    modifier = Modifier.requiredWidth(IntrinsicSize.Max)
                )
            }
            VerticalSpacer(height = MaterialTheme.localDimens.dp5)
            EpisodeBanner(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun EpisodeBanner(
    modifier: Modifier
) {
    val localColor = MaterialTheme.localColors
    Canvas(
        modifier = modifier.height(MaterialTheme.localDimens.dp17),
        onDraw = {
            val path = Path().apply {
                moveTo(size.width / 2f, size.height / 3f)
                lineTo(0f, size.height)
                lineTo(size.width, size.height)
            }
            clipPath(path, ClipOp.Difference) {
                drawRect(localColor.purple_dark)
            }
        }
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
                    modifier = Modifier.requiredWidth(IntrinsicSize.Max)
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
    val localColors = MaterialTheme.localColors
    Canvas(modifier = modifier.height(MaterialTheme.localDimens.dp8), onDraw = {
        drawRect(
            color = localColors.gold
        )
    })
}

@Composable
fun IconlessMenuBar(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "ZEYNEP SEY",
                style = MaterialTheme.localTextStyles.menuBarTitle,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
            SimpleIcon(
                iconResId = R.drawable.ic_close,
                modifier = Modifier.align(Alignment.CenterEnd)
            )

        }
        VerticalSpacer(height = MaterialTheme.localDimens.dp20)
        Divider(
            thickness = MaterialTheme.localDimens.dp0_5,
            color = MaterialTheme.localColors.white_alpha06
        )
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun PlaceMarkScreenPreview() {
    PlaceMarksScreen()
}