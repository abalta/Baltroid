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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.RoundedIconCard
import com.baltroid.ui.common.SetLoadingState
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.screens.viewmodels.AuthenticationViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.conditional

@Composable
fun MenuScreen(
    onBackClick: () -> Unit,
    isLoggedIn: Boolean,
    viewModel: AuthenticationViewModel = hiltViewModel(),
    navigate: (route: String) -> Unit,
) {
    val uiState by viewModel.profileState.collectAsStateWithLifecycle()
    SetLoadingState(isLoading = uiState.isLoading)
    if (isLoggedIn) {
        MenuScreenLoggedInContent(
            balance = uiState.profile?.gem ?: 0,
            currentUserName = uiState.profile?.userName.orEmpty(),
            imgUrl = uiState.profile?.avatar.orEmpty(),
            scrollState = rememberScrollState(),
            onBackClick = onBackClick,
            navigate = navigate
        )
    } else {
        MenuScreenGuestContent(
            balance = uiState.profile?.gem ?: 0,
            currentUserName = uiState.profile?.userName.orEmpty(),
            imgUrl = uiState.profile?.avatar.orEmpty(),
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

            RoundedIconCard(
                text = balance.toString(),
                iconResId = R.drawable.ic_diamond,
                modifier = Modifier
                    .constrainAs(diamond) {
                        end.linkTo(image.start, margin = 23.dp)
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
                        top.linkTo(parent.top, margin = 36.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .size(dimensionResource(id = R.dimen.dp111))
                    .clip(MaterialTheme.localShapes.circleShape)
            )
            Text(
                text = stringResource(id = R.string.member_login),
                style = MaterialTheme.localTextStyles.spaceGrotesk14Regular,
                color = MaterialTheme.localColors.white,
                modifier = Modifier
                    .constrainAs(profileButton) {
                        top.linkTo(image.bottom, margin = 26.dp)
                        start.linkTo(image.start)
                        end.linkTo(image.end)
                    }
                    .border(
                        dimensionResource(id = R.dimen.dp1),
                        MaterialTheme.localColors.white,
                        MaterialTheme.localShapes.roundedDp4
                    )
                    .clip(MaterialTheme.localShapes.roundedDp4)
                    .background(MaterialTheme.localColors.black)
                    .clickable {
                        navigate.invoke(HitReadsScreens.LoginScreen.route)
                    }
                    .padding(
                        vertical = 6.dp,
                        horizontal = dimensionResource(id = R.dimen.dp20)
                    )
            )
            SimpleIcon(
                iconResId = R.drawable.ic_close, modifier = Modifier
                    .constrainAs(close) {
                        end.linkTo(parent.end, margin = 42.dp)
                        top.linkTo(image.top)
                    }
                    .clickable { onBackClick.invoke() }
            )
            Divider(
                color = MaterialTheme.localColors.white,
                thickness = dimensionResource(id = R.dimen.dp1),
                modifier = Modifier.constrainAs(divider) {
                    top.linkTo(profileButton.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.percent(0.8f)
                }
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp16)),
                modifier = Modifier
                    .constrainAs(scrollSection) {
                        top.linkTo(divider.bottom, margin = 16.dp)
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
                ) {
                    navigate.invoke(HitReadsScreens.HomeScreen.route)
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

            RoundedIconCard(
                text = balance.toString(),
                iconResId = R.drawable.ic_diamond,
                modifier = Modifier
                    .constrainAs(diamond) {
                        end.linkTo(image.start, margin = 23.dp)
                        top.linkTo(image.top)
                        bottom.linkTo(image.bottom)
                    }
                    .clickable {
                        navigate.invoke(HitReadsScreens.ShopScreen.route)
                    }
            )
            AsyncImage(
                model = imgUrl,
                error = painterResource(id = R.drawable.ic_profile),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top, margin = 36.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .size(dimensionResource(id = R.dimen.dp111))
                    .clip(MaterialTheme.localShapes.circleShape)
            )
            Text(
                text = currentUserName,
                style = MaterialTheme.localTextStyles.poppins13Medium,
                color = MaterialTheme.localColors.white,
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(image.bottom, margin = 14.dp)
                    start.linkTo(image.start)
                    end.linkTo(image.end)
                }
            )
            Text(
                text = stringResource(id = R.string.profile_text),
                style = MaterialTheme.localTextStyles.spaceGrotesk11Medium,
                color = MaterialTheme.localColors.black,
                modifier = Modifier
                    .constrainAs(profileButton) {
                        top.linkTo(name.bottom, margin = 11.dp)
                        start.linkTo(name.start)
                        end.linkTo(name.end)
                    }
                    .clip(MaterialTheme.localShapes.roundedDp4)
                    .background(MaterialTheme.localColors.purple)
                    .clickable {
                        navigate.invoke(HitReadsScreens.ProfileScreen.route)
                    }
                    .padding(
                        vertical = 6.dp,
                        horizontal = dimensionResource(id = R.dimen.dp20)
                    )
            )
            SimpleIcon(
                iconResId = R.drawable.ic_close, modifier = Modifier
                    .constrainAs(close) {
                        end.linkTo(parent.end, margin = 42.dp)
                        top.linkTo(image.top)
                    }
                    .clickable { onBackClick.invoke() }
            )
            Divider(
                color = MaterialTheme.localColors.white,
                thickness = dimensionResource(id = R.dimen.dp1),
                modifier = Modifier.constrainAs(divider) {
                    top.linkTo(profileButton.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.percent(0.8f)
                }
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp16)),
                modifier = Modifier
                    .constrainAs(scrollSection) {
                        top.linkTo(divider.bottom, margin = 16.dp)
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
                ) {
                    navigate.invoke(HitReadsScreens.HomeScreen.route)
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
        horizontalArrangement = Arrangement.spacedBy(7.dp),
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
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dp24)))
            .background(MaterialTheme.localColors.white_alpha02)
            .padding(
                vertical = dimensionResource(id = R.dimen.dp12),
                horizontal = dimensionResource(id = R.dimen.dp29)
            )
    ) {
        Text(
            text = stringResource(id = R.string.light),
            style = MaterialTheme.localTextStyles.spaceGrotesk20Medium,
            color = MaterialTheme.localColors.black
        )
        HorizontalSpacer(width = dimensionResource(id = R.dimen.dp9))
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
                dimensionResource(id = R.dimen.dp1),
                MaterialTheme.localColors.white_alpha05,
                MaterialTheme.localShapes.roundedDp24
            )
            .padding(
                vertical = dimensionResource(id = R.dimen.dp12),
                horizontal = dimensionResource(id = R.dimen.dp29)
            )
    ) {
        Text(
            text = stringResource(id = R.string.dark),
            style = MaterialTheme.localTextStyles.spaceGrotesk20Medium,
            color = MaterialTheme.localColors.white_alpha09
        )
        HorizontalSpacer(width = dimensionResource(id = R.dimen.dp9))
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
                    .padding(start = dimensionResource(id = R.dimen.dp11))
            )
            Text(
                text = title,
                style = MaterialTheme.localTextStyles.spaceGrotesk24Medium,
                color = if (!isEnabled) MaterialTheme.localColors.white_alpha03 else MaterialTheme.localColors.white_alpha09,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp14))
        Divider(
            color = MaterialTheme.localColors.white,
            thickness = dimensionResource(id = R.dimen.dp1)
        )
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun MenuScreenPreview() {
    MenuScreen(
        isLoggedIn = false,
        onBackClick = {}
    ) {}
}