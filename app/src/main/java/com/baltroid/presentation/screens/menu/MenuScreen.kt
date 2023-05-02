package com.baltroid.presentation.screens.menu

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.baltroid.apps.R
import com.baltroid.presentation.common.CroppedImage
import com.baltroid.presentation.common.IconWithTextBelow
import com.baltroid.presentation.common.SimpleIcon
import com.baltroid.presentation.common.VerticalSpacer
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun MenuScreen(
    diamondValue: Int,
    currentUserName: String
) {
    val verticalScroll = rememberScrollState()
    BoxWithConstraints(
        modifier = Modifier.background(MaterialTheme.localColors.black)

    ) {
        val maxHeight = maxHeight
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(verticalScroll)
                .navigationBarsPadding()
        ) {
            val (
                diamond, image, name,
                profileButton, close, divider,
                allStories, placeMarks, favorites,
                comments, settings, themeButtons
            ) = createRefs()

            val bottomGuideLine = createGuidelineFromBottom(0.075f)
            val localDimens = MaterialTheme.localDimens

            RoundedIconCard(
                text = diamondValue.toString(),
                modifier = Modifier.constrainAs(diamond) {
                    end.linkTo(image.start, margin = localDimens.dp23)
                    top.linkTo(image.top)
                    bottom.linkTo(image.bottom)
                }
            )
            CroppedImage(
                imgResId = R.drawable.woods_image,
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top, margin = localDimens.dp36)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .size(MaterialTheme.localDimens.dp111)
                    .clip(MaterialTheme.localShapes.circleShape)
            )
            Text(
                text = currentUserName,
                style = MaterialTheme.localTextStyles.subtitle,
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(image.bottom, margin = localDimens.dp14)
                    start.linkTo(image.start)
                    end.linkTo(image.end)
                }
            )
            Text(
                text = stringResource(id = R.string.profile_text),
                style = MaterialTheme.localTextStyles.isStoryNewText,
                modifier = Modifier
                    .constrainAs(profileButton) {
                        top.linkTo(name.bottom, margin = localDimens.dp11)
                        start.linkTo(name.start)
                        end.linkTo(name.end)
                    }
                    .clip(MaterialTheme.localShapes.roundedDp4)
                    .background(MaterialTheme.localColors.purple)
                    .padding(
                        vertical = MaterialTheme.localDimens.dp6,
                        horizontal = MaterialTheme.localDimens.dp20
                    )
            )
            SimpleIcon(
                iconResId = R.drawable.ic_close, modifier = Modifier
                    .constrainAs(close) {
                        end.linkTo(parent.end, margin = localDimens.dp42)
                        top.linkTo(image.top)
                    }
            )
            Divider(
                color = MaterialTheme.localColors.white,
                thickness = MaterialTheme.localDimens.dp0_5,
                modifier = Modifier.constrainAs(divider) {
                    top.linkTo(profileButton.bottom, margin = localDimens.dp20)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.percent(0.8f)
                }
            )
            MenuItem(
                title = stringResource(id = R.string.all_stories_text),
                iconResId = R.drawable.ic_open_book,
                modifier = Modifier.constrainAs(allStories) {
                    top.linkTo(divider.bottom, margin = localDimens.dp16)
                    start.linkTo(divider.start)
                    end.linkTo(divider.end)
                    width = Dimension.fillToConstraints
                }
            )
            MenuItem(
                title = stringResource(id = R.string.place_marks),
                iconResId = R.drawable.ic_banner_filled,
                modifier = Modifier.constrainAs(placeMarks) {
                    top.linkTo(allStories.bottom, margin = localDimens.dp16)
                    start.linkTo(divider.start)
                    end.linkTo(divider.end)
                    width = Dimension.fillToConstraints
                }
            )
            MenuItem(
                title = stringResource(id = R.string.favorites),
                iconResId = R.drawable.ic_star,
                modifier = Modifier.constrainAs(favorites) {
                    top.linkTo(placeMarks.bottom, margin = localDimens.dp16)
                    start.linkTo(divider.start)
                    end.linkTo(divider.end)
                    width = Dimension.fillToConstraints
                }
            )
            MenuItem(
                title = stringResource(id = R.string.comments),
                iconResId = R.drawable.ic_chat_filled,
                modifier = Modifier.constrainAs(comments) {
                    top.linkTo(favorites.bottom, margin = localDimens.dp16)
                    start.linkTo(divider.start)
                    end.linkTo(divider.end)
                    width = Dimension.fillToConstraints
                }
            )
            MenuItem(
                title = stringResource(id = R.string.settings),
                iconResId = R.drawable.ic_settings,
                modifier = Modifier.constrainAs(settings) {
                    top.linkTo(comments.bottom, margin = localDimens.dp16)
                    start.linkTo(divider.start)
                    end.linkTo(divider.end)
                    width = Dimension.fillToConstraints
                }
            )
            ThemeButtons(
                modifier = Modifier
                    .constrainAs(themeButtons) {
                        if (maxHeight < localDimens.minScreenHeight) {
                            top.linkTo(settings.bottom, margin = localDimens.dp117)
                        } else {
                            bottom.linkTo(bottomGuideLine)
                        }
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(
                        bottom = if (maxHeight < localDimens.minScreenHeight) {
                            MaterialTheme.localDimens.dp78
                        } else {
                            MaterialTheme.localDimens.default
                        }
                    )
            )
        }
    }
}

@Composable
fun ThemeButtons(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp7),
        modifier = modifier
    ) {
        LightThemeButton()
        DarkThemeButton()
    }
}

@Composable
fun RoundedIconCard(
    text: String,
    modifier: Modifier = Modifier
) {
    IconWithTextBelow(
        iconResId = R.drawable.ic_diamond,
        text = text,
        spacedBy = MaterialTheme.localDimens.dp3,
        textStyle = MaterialTheme.localTextStyles.imageCardText,
        modifier = modifier
            .clip(MaterialTheme.localShapes.roundedDp16)
            .border(
                width = MaterialTheme.localDimens.dp1,
                color = MaterialTheme.localColors.white,
                shape = MaterialTheme.localShapes.roundedDp16
            )
            .padding(
                horizontal = MaterialTheme.localDimens.dp20,
                vertical = MaterialTheme.localDimens.dp14_5
            )
    )
}

@Composable
fun MenuItem(
    title: String,
    @DrawableRes iconResId: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            SimpleIcon(
                iconResId = iconResId,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = MaterialTheme.localDimens.dp11)
            )
            Text(
                text = title,
                style = MaterialTheme.localTextStyles.menuBarTitle,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        VerticalSpacer(height = MaterialTheme.localDimens.dp14)
        Divider(
            color = MaterialTheme.localColors.white,
            thickness = MaterialTheme.localDimens.dp0_5
        )
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun MenuScreenPreview() {
    MenuScreen(
        diamondValue = 4500,
        currentUserName = "SELEN PEKMEZCİ"
    )
}

