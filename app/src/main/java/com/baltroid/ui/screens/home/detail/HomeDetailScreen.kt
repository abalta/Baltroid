package com.baltroid.ui.screens.home.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R
import com.baltroid.ui.common.CroppedImage
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.HitReadsTopBar
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.screens.home.GenreSection
import com.baltroid.ui.screens.home.TitleSection
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.conditional

@Composable
fun HomeDetailScreen(
    screenState: HomeDetailScreenState,
    openMenuScreen: () -> Unit,
    navigate: (route: String, itemId: Int) -> Unit
) {
    HomeDetailScreenContent(
        screenState = screenState,
        openMenuScreen = openMenuScreen,
        navigate = navigate
    )
}

@Composable
private fun HomeDetailScreenContent(
    screenState: HomeDetailScreenState,
    openMenuScreen: () -> Unit,
    navigate: (route: String, itemId: Int) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CroppedImage(imgResId = R.drawable.woods_image, modifier = Modifier.fillMaxSize())
        HitReadsTopBar(
            iconResId = R.drawable.ic_bell,
            numberOfNotification = screenState.numberOfNotification,
            onMenuClick = openMenuScreen,
            onIconClick = {},
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
        ) {
            Text(
                text = stringResource(id = R.string.hashtag_kgd),
                style = MaterialTheme.localTextStyles.hashTag,
                modifier = Modifier.padding(start = MaterialTheme.localDimens.dp17)
            )
            TitleSection(
                author = screenState.author,
                firstName = screenState.firstName,
                secondName = screenState.secondName,
                modifier = Modifier.padding(start = MaterialTheme.localDimens.dp23)
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp10_5)
            GenreAndInteractions(
                episodeSize = screenState.episodeSize,
                genres = screenState.genres,
                numberOfViews = screenState.numberOfViews,
                numberOfComments = screenState.numberOfComments
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp20_5)
            HomeDetailSummarySection(
                summary = screenState.summary,
                modifier = Modifier.padding(start = MaterialTheme.localDimens.dp25)
            ) {
                val id = 0
                navigate.invoke(HitReadsScreens.ReadingScreen.route, id)
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
    genres: List<String>,
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

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun HomeDetailScreenPreview() {
    HomeDetailScreen(
        screenState = HomeDetailScreenState(
            id = 0,
            author = "ZEYNEP SEY",
            firstName = "KİMSE GERÇEK DEĞİL",
            secondName = "Araf, Aydınlık Ve Aşık",
            genres = listOf("ROMANTİK", "GENÇLİK"),
            numberOfNotification = 12,
            numberOfViews = 1002,
            numberOfComments = 142,
            episodeSize = 35,
            summary = "Kim olduğunu sorguladıkça dünyasının sahtelikten İbaret olduğunu anlamaya başlayan Işıl Özsoydan, öğrendiği gerçekleri..."
        ),
        openMenuScreen = {}
    ) { _, _ -> }
}