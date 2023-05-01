package com.baltroid.presentation.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.baltroid.presentation.common.HorizontalSpacer
import com.baltroid.presentation.common.SimpleIcon
import com.baltroid.presentation.common.VerticalSpacer
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun HitReadsMenuSettings() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
    ) {

        val localDimens = MaterialTheme.localDimens

        val (
            userInfo, notification, appSettings,
            privacy, privateDatas, text,
            close, divider, leftIcon,
            themeButtons,
        ) = createRefs()

        createHorizontalChain(leftIcon, text, close)
        val bottomGuideLine = createGuidelineFromBottom(0.075f)

        Text(
            text = stringResource(id = R.string.settings),
            style = MaterialTheme.localTextStyles.menuBarTitle,
            modifier = Modifier.constrainAs(text) {
                top.linkTo(parent.top, margin = localDimens.dp36)
            }
        )
        SimpleIcon(iconResId = R.drawable.ic_settings, modifier = Modifier.constrainAs(leftIcon) {
            top.linkTo(text.top)
            bottom.linkTo(text.bottom)
        })
        SimpleIcon(iconResId = R.drawable.ic_close, modifier = Modifier.constrainAs(close) {
            top.linkTo(text.top)
            bottom.linkTo(text.bottom)
        })
        Divider(
            thickness = MaterialTheme.localDimens.dp0_5,
            color = MaterialTheme.localColors.white_alpha06,
            modifier = Modifier.constrainAs(divider) {
                start.linkTo(leftIcon.start)
                end.linkTo(close.end)
                top.linkTo(text.bottom, localDimens.dp20)
                width = Dimension.fillToConstraints
            }
        )
        SettingsItem(
            title = stringResource(id = R.string.user_infos),
            modifier = Modifier.constrainAs(userInfo) {
                top.linkTo(divider.bottom, margin = localDimens.dp35)
                start.linkTo(divider.start)
                end.linkTo(divider.end)
                width = Dimension.fillToConstraints
            })
        SettingsItem(
            title = stringResource(id = R.string.notifications),
            modifier = Modifier.constrainAs(notification) {
                top.linkTo(userInfo.bottom, margin = localDimens.dp18)
                start.linkTo(divider.start)
                end.linkTo(divider.end)
                width = Dimension.fillToConstraints
            })
        SettingsItem(
            title = stringResource(id = R.string.app_settings),
            modifier = Modifier.constrainAs(appSettings) {
                top.linkTo(notification.bottom, margin = localDimens.dp18)
                start.linkTo(divider.start)
                end.linkTo(divider.end)
                width = Dimension.fillToConstraints
            })
        SettingsItem(
            title = stringResource(id = R.string.privacy),
            modifier = Modifier.constrainAs(privacy) {
                top.linkTo(appSettings.bottom, margin = localDimens.dp18)
                start.linkTo(divider.start)
                end.linkTo(divider.end)
                width = Dimension.fillToConstraints
            })
        SettingsItem(
            title = stringResource(id = R.string.personal_datas),
            modifier = Modifier.constrainAs(privateDatas) {
                top.linkTo(privacy.bottom, margin = localDimens.dp18)
                start.linkTo(divider.start)
                end.linkTo(divider.end)
                width = Dimension.fillToConstraints
            })
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp7),
            modifier = Modifier.constrainAs(themeButtons) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottomGuideLine)
            }
        ) {
            LightThemeButton()
            DarkThemeButton()
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = title, style = MaterialTheme.localTextStyles.menuBarTitle)
        VerticalSpacer(height = MaterialTheme.localDimens.dp14)
        Divider(
            thickness = MaterialTheme.localDimens.dp0_5,
            color = MaterialTheme.localColors.white
        )
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

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun HitReadsMenuSettingsPreview() {
    HitReadsMenuSettings()
}