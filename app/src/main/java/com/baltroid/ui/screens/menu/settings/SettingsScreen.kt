package com.baltroid.ui.screens.menu.settings

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.baltroid.apps.R
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localTextStyles

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    SettingsScreenContent(
        scrollState = rememberScrollState(),
        onBackClick = onBackClick
    )
}

@Composable
fun SettingsScreenContent(
    scrollState: ScrollState,
    onBackClick: () -> Unit
) {
    BoxWithConstraints {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.localColors.black)
                .systemBarsPadding()
        ) {

            val (
                userInfo, menuBar,
            ) = createRefs()

            val bottomGuideLine = createGuidelineFromBottom(0.075f)

            MenuBar(
                title = stringResource(id = R.string.settings),
                iconResId = R.drawable.ic_settings,
                onBackClick = onBackClick,
                modifier = Modifier.constrainAs(menuBar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 36.dp)
                }
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp18)),
                modifier = Modifier
                    .constrainAs(userInfo) {
                        top.linkTo(menuBar.bottom, margin = 35.dp)
                        start.linkTo(menuBar.start)
                        end.linkTo(menuBar.end)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .verticalScroll(scrollState)
            ) {
                SettingsItem(
                    title = stringResource(id = R.string.user_infos),
                    modifier = Modifier
                )
                SettingsItem(
                    title = stringResource(id = R.string.notifications),
                    modifier = Modifier
                )
                SettingsItem(
                    title = stringResource(id = R.string.app_settings),
                    modifier = Modifier
                )
                SettingsItem(
                    title = stringResource(id = R.string.privacy),
                    modifier = Modifier
                )
                SettingsItem(
                    title = stringResource(id = R.string.personal_datas),
                    modifier = Modifier
                )
            }
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
        Text(
            text = title,
            style = MaterialTheme.localTextStyles.menuBarTitle,
            color = MaterialTheme.localColors.white_alpha09
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp14))
        Divider(
            thickness = dimensionResource(id = R.dimen.dp1),
            color = MaterialTheme.localColors.white
        )
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen {}
}