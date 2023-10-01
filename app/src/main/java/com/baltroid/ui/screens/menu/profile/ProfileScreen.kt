package com.baltroid.ui.screens.menu.profile

import android.widget.Toast
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.RoundedIconCard
import com.baltroid.ui.common.SetLoadingState
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.IconlessMenuBar
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.screens.interactive.EpisodeButton
import com.baltroid.ui.screens.menu.login.IconBetweenDividers
import com.baltroid.ui.screens.menu.login.UserInputArea
import com.baltroid.ui.screens.viewmodels.AuthenticationViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.orZero
import com.hitreads.core.model.Profile

@Composable
fun ProfileScreen(
    viewModel: AuthenticationViewModel,
    onBackClick: () -> Unit,
    navigate: (route: String) -> Unit
) {
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    SetLoadingState(isLoading = profileState.isLoading)
    LaunchedEffect(profileState.isProfileUpdated) {
        if (profileState.isProfileUpdated == true) {
            Toast.makeText(context, context.getString(R.string.profile_updated), Toast.LENGTH_LONG)
                .show()
        }
        if (profileState.isProfileUpdated == false) {
            Toast.makeText(
                context,
                context.getString(R.string.something_went_wrong),
                Toast.LENGTH_LONG
            ).show()
        }
        viewModel.clearProfileUpdatedState()
    }
    ProfileScreenContent(
        profile = profileState.profile,
        onBackClick = onBackClick,
        updateUserProfile = viewModel::updateUserProfile,
        navigate = navigate
    )
}

@Composable
fun ProfileScreenContent(
    profile: Profile?,
    onBackClick: () -> Unit,
    updateUserProfile: (username: String, nickname: String, email: String) -> Unit,
    navigate: (route: String) -> Unit
) {
    var showForgotPasswordPopup by remember {
        mutableStateOf(false)
    }
    if (showForgotPasswordPopup) {
        EditProfilePopup(
            onDialogDismissed = {
                showForgotPasswordPopup = false
            },
            onClick = { username, nickname, email ->
                updateUserProfile.invoke(username, nickname, email)
                showForgotPasswordPopup = false
            }
        )
    }
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
                    imgUrl = profile?.avatar.orEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    onCameraClick = {
                        navigate.invoke(HitReadsScreens.AvatarsScreen.route)
                    },
                    onPurchaseClick = {
                        navigate.invoke(HitReadsScreens.ShopScreen.route)
                    }
                )
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp40))
                ProfileItem(title = profile?.name.orEmpty())
                VerticalSpacer(height = R.dimen.dp22)
                Divider(
                    thickness = dimensionResource(id = R.dimen.dp0_5),
                    color = MaterialTheme.localColors.white_alpha06,
                )
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp22))
                ProfileItem(title = profile?.userName.orEmpty())
                VerticalSpacer(height = R.dimen.dp22)
                Divider(
                    thickness = dimensionResource(id = R.dimen.dp0_5),
                    color = MaterialTheme.localColors.white_alpha06,
                )
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp22))
                ProfileItem(title = profile?.email.orEmpty())
                VerticalSpacer(height = R.dimen.dp36)
            }
        }
        Divider(
            thickness = dimensionResource(id = R.dimen.dp1),
            color = MaterialTheme.localColors.white_alpha06
        )
        IconBetweenDividers(
            R.drawable.ic_edit
        ) {
            showForgotPasswordPopup = true
        }
        Divider(
            thickness = dimensionResource(id = R.dimen.dp1),
            color = MaterialTheme.localColors.white_alpha06
        )
        /*  TextBetweenDividers(
              text = stringResource(id = R.string.forgot_password),
              textStyle = MaterialTheme.localTextStyles.spaceGrotesk18Medium,
              onClick = {

              })
          Divider(
              thickness = dimensionResource(id = R.dimen.dp1),
              color = MaterialTheme.localColors.white_alpha06
          )*/
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp50))
    }
}

@Composable
fun ProfileItem(
    title: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            title,
            style = MaterialTheme.localTextStyles.poppins18Regular,
            textAlign = TextAlign.Center,
            color = MaterialTheme.localColors.white,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

/*@Composable
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
            color = MaterialTheme.localColors.white,
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
}*/

@Composable
fun IconsAndProfileImage(
    diamondValue: Int,
    imgUrl: String,
    onCameraClick: () -> Unit,
    onPurchaseClick: () -> Unit,
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
        RoundedIconCard(
            text = diamondValue.toString(),
            iconResId = R.drawable.ic_diamond,
            Modifier.clickable { onPurchaseClick.invoke() })
        AsyncImage(
            model = imgUrl,
            error = painterResource(id = R.drawable.ic_profile),
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp111))
                .clip(MaterialTheme.localShapes.circleShape)
        )
        RoundedIconCard(
            iconResId = R.drawable.ic_camera,
            modifier = Modifier.clickable(onClick = onCameraClick)
        )
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

@Composable
fun EditProfilePopup(
    onClick: (username: String, nickname: String, email: String) -> Unit,
    onDialogDismissed: () -> Unit
) {
    var username by remember {
        mutableStateOf("")
    }
    var nickname by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = { onDialogDismissed.invoke() }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.localShapes.roundedDp24)
                .background(MaterialTheme.localColors.black)
                .border(
                    dimensionResource(id = R.dimen.dp1),
                    MaterialTheme.localColors.white,
                    MaterialTheme.localShapes.roundedDp24
                )
        ) {
            VerticalSpacer(height = R.dimen.dp24)
            UserInputArea(
                title = R.string.name_surname,
                value = username,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth(0.7f)
            )
            VerticalSpacer(height = R.dimen.dp24)
            UserInputArea(
                title = R.string.username,
                value = nickname,
                onValueChange = { nickname = it },
                modifier = Modifier.fillMaxWidth(0.7f)
            )
            VerticalSpacer(height = R.dimen.dp24)
            UserInputArea(
                title = R.string.email,
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(0.7f)
            )
            VerticalSpacer(height = R.dimen.dp24)

            EpisodeButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.dp24))
                    .align(Alignment.CenterHorizontally),
                buttonTitle = stringResource(id = R.string.send)
            ) {
                onClick.invoke(username, nickname, email)
            }
            VerticalSpacer(height = R.dimen.dp24)
        }
    }
}

@Preview(device = Devices.DEFAULT)
@Composable
fun ProfileScreenPreview() {

}

