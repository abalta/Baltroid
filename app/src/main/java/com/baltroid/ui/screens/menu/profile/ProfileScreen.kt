package com.baltroid.ui.screens.menu.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.baltroid.apps.R
import com.baltroid.ui.common.CroppedImage
import com.baltroid.ui.common.RoundedIconCard
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.IconlessMenuBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit
) {
    ProfileScreenContent(onBackClick = onBackClick)
}

@Composable
fun ProfileScreenContent(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
    ) {
        VerticalSpacer(height = MaterialTheme.localDimens.dp36)
        IconlessMenuBar(
            title = stringResource(id = R.string.profile_text),
            onBackClick = onBackClick,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally)
                .statusBarsPadding()
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp29)
        IconsAndProfileImage(diamondValue = 4500, modifier = Modifier.fillMaxWidth())
        VerticalSpacer(height = MaterialTheme.localDimens.dp67)
        UserInfoSection(
            userName = "SELEN PEKMEZCİ",
            userEmail = "selen22@gmail.com",
            modifier = Modifier.fillMaxWidth()
        ) {}
        EditProfileSection(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentHeight(Alignment.Bottom)
                .padding(bottom = MaterialTheme.localDimens.dp67)
                .navigationBarsPadding()
        )
    }
}

@Composable
fun IconsAndProfileImage(
    diamondValue: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = MaterialTheme.localDimens.dp18,
            alignment = Alignment.CenterHorizontally
        ),
        modifier = modifier,
    ) {
        RoundedIconCard(text = diamondValue.toString(), iconResId = R.drawable.ic_diamond)
        CroppedImage(
            imgResId = R.drawable.woods_image,
            modifier = Modifier
                .size(MaterialTheme.localDimens.dp111)
                .clip(MaterialTheme.localShapes.circleShape)
        )
        RoundedIconCard(iconResId = R.drawable.ic_edit)
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
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp7),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = userName,
            style = MaterialTheme.localTextStyles.profileScreenUserInfo
        )
        Text(
            text = userEmail,
            style = MaterialTheme.localTextStyles.profileScreenUserInfo
        )
        Text(
            text = stringResource(id = R.string.forgot_password),
            style = MaterialTheme.localTextStyles.forgotPassword,
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
            thickness = MaterialTheme.localDimens.dp0_5,
            color = MaterialTheme.localColors.white_alpha06
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp25)
        SimpleIcon(iconResId = R.drawable.ic_edit)
        VerticalSpacer(height = MaterialTheme.localDimens.dp16_5)
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
fun ProfileScreenPreview() {
    ProfileScreen {}
}

