package com.baltroid.ui.screens.menu.settings

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.BuildConfig
import com.baltroid.apps.R
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.screens.interactive.EpisodeButton
import com.baltroid.ui.screens.viewmodels.AuthenticationViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localTextStyles
import com.hitreads.core.model.Profile


@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: AuthenticationViewModel,
) {
    val state by viewModel.profileState.collectAsStateWithLifecycle()
    SettingsScreenContent(
        scrollState = rememberScrollState(),
        onBackClick = onBackClick,
        profile = state.profile
    )
}

@Composable
fun SettingsScreenContent(
    scrollState: ScrollState,
    profile: Profile?,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    var soundEffect by remember {
        mutableStateOf(true)
    }

    var notifications by remember {
        mutableStateOf(true)
    }

    val uriHandler = LocalUriHandler.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .systemBarsPadding()
    ) {

        val (
            userInfo, menuBar,
        ) = createRefs()

        MenuBar(
            title = stringResource(id = R.string.settings),
            iconResId = R.drawable.ic_settings,
            widthFraction = 0.75f,
            onBackClick = onBackClick,
            modifier = Modifier.constrainAs(menuBar) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top, margin = 36.dp)
            }
        )
        Column(
            modifier = Modifier
                .constrainAs(userInfo) {
                    top.linkTo(menuBar.bottom, margin = 23.dp)
                    start.linkTo(menuBar.start)
                    end.linkTo(menuBar.end)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .verticalScroll(scrollState)
        ) {
            SwitchWithText(
                title = stringResource(id = R.string.notifications),
                isChecked = notifications,
                modifier = Modifier.fillMaxWidth(),
            ) {
                notifications = !notifications
            }
            VerticalSpacer(height = R.dimen.dp17)
            Divider(
                thickness = dimensionResource(id = R.dimen.dp0_5),
                color = MaterialTheme.localColors.white
            )
            VerticalSpacer(height = R.dimen.dp39)
            EpisodeButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                buttonTitle = stringResource(id = R.string.privacy_policy)
            ) {
                uriHandler.openUri("http://3.73.140.195/agreements/hitreads-privacy-policy")
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp28))
            EpisodeButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                buttonTitle = stringResource(id = R.string.user_agreement)
            ) {
                uriHandler.openUri("http://3.73.140.195/agreements/hitreads-terms-of-use")
            }
            VerticalSpacer(height = R.dimen.dp43)
            Text(
                text = "KULLANICI ID",
                style = MaterialTheme.localTextStyles.spaceGrotesk18Medium,
                color = MaterialTheme.localColors.white_alpha09
            )
            Text(
                text = profile?.id.toString(),
                style = MaterialTheme.localTextStyles.spaceGrotesk18Medium,
                color = MaterialTheme.localColors.white_alpha05
            )
            VerticalSpacer(height = R.dimen.dp11)
            Text(
                text = stringResource(id = R.string.version),
                style = MaterialTheme.localTextStyles.spaceGrotesk18Medium,
                color = MaterialTheme.localColors.white_alpha09
            )
            Text(
                text = BuildConfig.VERSION_NAME,
                style = MaterialTheme.localTextStyles.spaceGrotesk18Medium,
                color = MaterialTheme.localColors.white_alpha05
            )
            VerticalSpacer(height = R.dimen.dp25)
            EpisodeButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                buttonTitle = stringResource(id = R.string.share)
            ) {
                composeEmail(
                    context,
                    arrayOf(""),
                    "Hitreads share",
                    "This message come from Hitreads App"
                )
            }
            VerticalSpacer(height = R.dimen.dp16)
            EpisodeButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                buttonTitle = stringResource(id = R.string.support)
            ) {
                composeEmail(
                    context,
                    arrayOf("test@hitreads.com"),
                    "Hitreads email support",
                    "This message come from Hitreads App"
                )
            }
            VerticalSpacer(height = R.dimen.dp50)
        }
    }
}

@Composable
fun SwitchWithText(
    title: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onSwitchChange: () -> Unit,
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = title,
            style = MaterialTheme.localTextStyles.spaceGrotesk22Medium,
            color = MaterialTheme.localColors.white,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = isChecked,
            onCheckedChange = { onSwitchChange.invoke() },
            colors = SwitchDefaults.colors(checkedTrackColor = MaterialTheme.localColors.green)
        )
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
            style = MaterialTheme.localTextStyles.spaceGrotesk24Medium,
            color = MaterialTheme.localColors.white_alpha09
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp14))
        Divider(
            thickness = dimensionResource(id = R.dimen.dp1),
            color = MaterialTheme.localColors.white
        )
    }
}

fun composeEmail(
    context: Context,
    emailIds: Array<String>,
    subject: String,
    textMessage: String
) {
    Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_EMAIL, emailIds)
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, textMessage)
        type = "message/rfc822"
        context.startActivity(
            Intent.createChooser(
                this,
                "Send email using..."
            )
        )
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun SettingsScreenPreview() {
}