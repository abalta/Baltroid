package com.baltroid.ui.screens.menu.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.RoundedIconCard
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.IconlessMenuBar
import com.baltroid.ui.screens.menu.login.TextBetweenDividers
import com.baltroid.ui.screens.viewmodels.AuthenticationViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.orZero
import com.hitreads.core.model.Profile

@Composable
fun ProfileScreen(
    viewModel: AuthenticationViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getProfile()
    }

    val profile = viewModel.profileState.collectAsStateWithLifecycle().value.profile
    ProfileScreenContent(profile = profile, onBackClick = onBackClick)
}

@Composable
fun ProfileScreenContent(
    profile: Profile?,
    onBackClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .weight(1f)
        ) {
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp36))
            IconlessMenuBar(
                title = stringResource(id = R.string.profile_text),
                onBackClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .statusBarsPadding()
            )
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp29))
                IconsAndProfileImage(
                    diamondValue = profile?.gem.orZero(),
                    imgUrl = profile?.imgUrl.orEmpty(),
                    modifier = Modifier.fillMaxWidth()
                )
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp40))
                ProfileItem(title = profile?.name.orEmpty()) {

                }
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp33))
                ProfileItem(title = profile?.userName.orEmpty()) {

                }
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp33))
                ProfileItem(title = profile?.email.orEmpty()) {

                }
            }
        }
        TextBetweenDividers(
            text = stringResource(id = R.string.forgot_password),
            textStyle = MaterialTheme.localTextStyles.spaceGrotesk18Medium,
            onClick = {

            })
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp50))
    }
}

@Composable
fun ProfileItem(
    title: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick.invoke() }
    ) {
        Text(
            title,
            style = MaterialTheme.localTextStyles.poppins18Regular,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .border(
                    dimensionResource(id = R.dimen.dp1),
                    MaterialTheme.localColors.white_alpha05,
                    MaterialTheme.localShapes.circleShape
                )
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp17))
        SimpleIcon(iconResId = R.drawable.ic_edit)
    }
}

@Composable
fun IconsAndProfileImage(
    diamondValue: Int,
    imgUrl: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(id = R.dimen.dp18),
            alignment = Alignment.CenterHorizontally
        ),
        modifier = modifier,
    ) {
        RoundedIconCard(text = diamondValue.toString(), iconResId = R.drawable.ic_diamond)
        AsyncImage(
            model = imgUrl,
            error = painterResource(id = R.drawable.ic_profile),
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp111))
                .clip(MaterialTheme.localShapes.circleShape)
        )
        RoundedIconCard(iconResId = R.drawable.ic_camera)
    }
}

@Composable
fun UserInfoSection(
    userName: String,
    userEmail: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = userName,
            style = MaterialTheme.localTextStyles.poppins18Regular
        )
        Text(
            text = userEmail,
            style = MaterialTheme.localTextStyles.poppins18Regular
        )
        Text(
            text = stringResource(id = R.string.forgot_password),
            style = MaterialTheme.localTextStyles.poppins14Medium,
            color = MaterialTheme.localColors.orange,
            modifier = Modifier.clickable { onClick.invoke() })
    }
}

@Composable
fun EditProfileSection(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Divider(
            thickness = dimensionResource(id = R.dimen.dp1),
            color = MaterialTheme.localColors.white_alpha06
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp25))
        SimpleIcon(iconResId = R.drawable.ic_edit)
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp17))
        Divider(
            thickness = dimensionResource(id = R.dimen.dp1),
            color = MaterialTheme.localColors.white_alpha06
        )
    }
}

@Preview(device = Devices.DEFAULT)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen {

    }
}

