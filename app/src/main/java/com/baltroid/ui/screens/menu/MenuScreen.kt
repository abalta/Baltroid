package com.baltroid.ui.screens.menu

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.baltroid.apps.R
import com.baltroid.ui.common.CroppedImage
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.RoundedIconCard
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.conditional

@Composable
fun MenuScreen(
    menuScreenState: MenuScreenState,
    onBackClick: () -> Unit,
    isLoggedIn: Boolean,
    navigate: (route: String) -> Unit,
) {
    if (isLoggedIn) {
        MenuScreenLoggedInContent(
            balance = menuScreenState.diamondBalance,
            currentUserName = menuScreenState.currentUserName,
            imgUrl = menuScreenState.imgUrl,
            scrollState = rememberScrollState(),
            onBackClick = onBackClick,
            navigate = navigate
        )
    } else {
        MenuScreenGuestContent(
            balance = menuScreenState.diamondBalance,
            currentUserName = menuScreenState.currentUserName,
            imgUrl = menuScreenState.imgUrl,
            scrollState = rememberScrollState(),
            onBackClick = onBackClick,
            navigate = navigate
        )
    }
}

@Composable
fun MenuScreenGuestContent(
    balance: Int,
    currentUserName: String,
    imgUrl: String,
    scrollState: ScrollState,
    onBackClick: () -> Unit,
    navigate: (route: String) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .background(MaterialTheme.localColors.black)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            val (
                diamond, image, name,
                profileButton, close, divider,
                scrollSection
            ) = createRefs()

            val bottomGuideLine = createGuidelineFromBottom(0.075f)
            val localDimens = MaterialTheme.localDimens

            RoundedIconCard(
                text = "0",
                iconResId = R.drawable.ic_diamond,
                modifier = Modifier
                    .constrainAs(diamond) {
                        end.linkTo(image.start, margin = localDimens.dp23)
                        top.linkTo(image.top)
                        bottom.linkTo(image.bottom)
                    }
                    .clickable {
                        navigate.invoke(HitReadsScreens.ShopScreen.route)
                    }
            )
            SimpleImage(
                imgResId = R.drawable.ic_member,
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
                text = stringResource(id = R.string.member_login),
                style = MaterialTheme.localTextStyles.subtitleGrotesk,
                modifier = Modifier
                    .constrainAs(profileButton) {
                        top.linkTo(image.bottom, margin = localDimens.dp26)
                        start.linkTo(image.start)
                        end.linkTo(image.end)
                    }
                    .border(
                        MaterialTheme.localDimens.dp1,
                        MaterialTheme.localColors.white,
                        MaterialTheme.localShapes.roundedDp4
                    )
                    .clip(MaterialTheme.localShapes.roundedDp4)
                    .background(MaterialTheme.localColors.black)
                    .clickable {
                        navigate.invoke(HitReadsScreens.LoginScreen.route)
                    }
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
                    .clickable { onBackClick.invoke() }
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
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp16),
                modifier = Modifier
                    .constrainAs(scrollSection) {
                        top.linkTo(divider.bottom, margin = localDimens.dp16)
                        start.linkTo(divider.start)
                        end.linkTo(divider.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .verticalScroll(scrollState)

            ) {
                MenuItem(
                    title = stringResource(id = R.string.all_stories_text),
                    iconResId = R.drawable.ic_open_book,
                    isEnabled = true,
                    modifier = Modifier
                ) {}
                MenuItem(
                    title = stringResource(id = R.string.place_marks),
                    iconResId = R.drawable.ic_banner_filled,
                    isEnabled = false,
                    modifier = Modifier
                ) {
                    navigate.invoke(HitReadsScreens.PlaceMarksScreen.route)
                }
                MenuItem(
                    title = stringResource(id = R.string.favorites),
                    iconResId = R.drawable.ic_star,
                    isEnabled = false,
                    modifier = Modifier
                ) {
                    navigate.invoke(HitReadsScreens.FavoritesScreen.route)
                }
                MenuItem(
                    title = stringResource(id = R.string.comments),
                    iconResId = R.drawable.ic_chat_filled,
                    isEnabled = false,
                    modifier = Modifier
                ) {}
                MenuItem(
                    title = stringResource(id = R.string.settings),
                    iconResId = R.drawable.ic_settings,
                    isEnabled = true,
                    modifier = Modifier
                ) {
                    navigate.invoke(HitReadsScreens.SettingsScreen.route)
                }
            }
        }
    }

}

@Composable
private fun MenuScreenLoggedInContent(
    balance: Int,
    currentUserName: String,
    imgUrl: String,
    scrollState: ScrollState,
    onBackClick: () -> Unit,
    navigate: (route: String) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .background(MaterialTheme.localColors.black)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            val (
                diamond, image, name,
                profileButton, close, divider,
                scrollSection, themeButtons
            ) = createRefs()

            val bottomGuideLine = createGuidelineFromBottom(0.075f)
            val localDimens = MaterialTheme.localDimens

            RoundedIconCard(
                text = balance.toString(),
                iconResId = R.drawable.ic_diamond,
                modifier = Modifier
                    .constrainAs(diamond) {
                        end.linkTo(image.start, margin = localDimens.dp23)
                        top.linkTo(image.top)
                        bottom.linkTo(image.bottom)
                    }
                    .clickable {
                        navigate.invoke(HitReadsScreens.ShopScreen.route)
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
                    .clickable {
                        navigate.invoke(HitReadsScreens.ProfileScreen.route)
                    }
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
                    .clickable { onBackClick.invoke() }
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
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp16),
                modifier = Modifier
                    .constrainAs(scrollSection) {
                        top.linkTo(divider.bottom, margin = localDimens.dp16)
                        start.linkTo(divider.start)
                        end.linkTo(divider.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .verticalScroll(scrollState)

            ) {
                MenuItem(
                    title = stringResource(id = R.string.all_stories_text),
                    iconResId = R.drawable.ic_open_book,
                    isEnabled = true,
                    modifier = Modifier
                ) {}
                MenuItem(
                    title = stringResource(id = R.string.place_marks),
                    iconResId = R.drawable.ic_banner_filled,
                    isEnabled = true,
                    modifier = Modifier
                ) {
                    navigate.invoke(HitReadsScreens.PlaceMarksScreen.route)
                }
                MenuItem(
                    title = stringResource(id = R.string.favorites),
                    iconResId = R.drawable.ic_star,
                    isEnabled = true,
                    modifier = Modifier
                ) {
                    navigate.invoke(HitReadsScreens.FavoritesScreen.route)
                }
                MenuItem(
                    title = stringResource(id = R.string.comments),
                    iconResId = R.drawable.ic_chat_filled,
                    isEnabled = true,
                    modifier = Modifier
                ) {
                    navigate.invoke(HitReadsScreens.CommentsScreen.route)
                }
                MenuItem(
                    title = stringResource(id = R.string.settings),
                    iconResId = R.drawable.ic_settings,
                    isEnabled = true,
                    modifier = Modifier
                ) {
                    navigate.invoke(HitReadsScreens.SettingsScreen.route)
                }
            }
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
fun LightThemeButton(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(MaterialTheme.localDimens.dp24))
            .background(MaterialTheme.localColors.white_alpha02)
            .padding(
                vertical = MaterialTheme.localDimens.dp12,
                horizontal = MaterialTheme.localDimens.dp29
            )
    ) {
        Text(
            text = stringResource(id = R.string.light),
            style = MaterialTheme.localTextStyles.menuBarSubTitle,
            color = MaterialTheme.localColors.black
        )
        HorizontalSpacer(width = MaterialTheme.localDimens.dp9)
        SimpleIcon(iconResId = R.drawable.ic_sun)
    }
}

@Composable
fun DarkThemeButton(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .border(
                MaterialTheme.localDimens.dp1,
                MaterialTheme.localColors.white_alpha05,
                MaterialTheme.localShapes.roundedDp24
            )
            .padding(
                vertical = MaterialTheme.localDimens.dp12,
                horizontal = MaterialTheme.localDimens.dp29
            )
    ) {
        Text(
            text = stringResource(id = R.string.dark),
            style = MaterialTheme.localTextStyles.menuBarSubTitle
        )
        HorizontalSpacer(width = MaterialTheme.localDimens.dp9)
        SimpleIcon(iconResId = R.drawable.ic_moon)
    }
}

@Composable
fun MenuItem(
    title: String,
    @DrawableRes iconResId: Int,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.conditional(isEnabled) {
            clickable { onClick.invoke() }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            SimpleIcon(
                iconResId = iconResId,
                tint = if (!isEnabled) MaterialTheme.localColors.white_alpha03 else Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = MaterialTheme.localDimens.dp11)
            )
            Text(
                text = title,
                style = MaterialTheme.localTextStyles.menuBarTitle,
                color = if (!isEnabled) MaterialTheme.localColors.white_alpha03 else Color.Unspecified,
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
        menuScreenState = MenuScreenState(
            200,
            "SELEN PEKMEZCİ",
            imgUrl = ""
        ),
        isLoggedIn = false,
        onBackClick = {}
    ) {}
}