package com.baltroid.ui.screens.menu.login

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun LoginScreen() {
    LoginScreenContent {

    }
}

@Composable
fun LoginScreenContent(
    forgotPassword: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .systemBarsPadding()
    ) {
        VerticalSpacer(height = MaterialTheme.localDimens.dp36)
        Text(
            text = stringResource(id = R.string.member_login),
            style = MaterialTheme.localTextStyles.menuBarTitle
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp20)
        Divider(
            color = MaterialTheme.localColors.white_alpha06,
            thickness = MaterialTheme.localDimens.dp0_5,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp38)
        SimpleImage(imgResId = R.drawable.ic_profile)
        VerticalSpacer(height = MaterialTheme.localDimens.dp16)
        UserInputArea(
            title = R.string.email,
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(0.7f)
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp20)
        UserInputArea(
            title = R.string.password,
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(0.7f)
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp36)
        Text(
            text = stringResource(id = R.string.forgot_password),
            style = MaterialTheme.localTextStyles.forgotPassword,
            modifier = Modifier.clickable { forgotPassword.invoke() }
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            TextBetweenDividers(
                text = stringResource(id = R.string.sign_in),
                textStyle = MaterialTheme.localTextStyles.signInTextWhite
            )
            TextBetweenDividers(
                text = stringResource(id = R.string.sign_up),
                textStyle = MaterialTheme.localTextStyles.signUpTextOrange
            )
        }
    }
}

@Composable
fun UserInputArea(
    @StringRes title: Int,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.localTextStyles.profileScreenUserInfo
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp11)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.localTextStyles.profileScreenUserInfo,
            cursorBrush = SolidColor(MaterialTheme.localColors.white),
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.localDimens.dp43)
                .clip(MaterialTheme.localShapes.roundedDp11_5)
                .border(
                    width = MaterialTheme.localDimens.dp1,
                    color = MaterialTheme.localColors.white_alpha05,
                    shape = MaterialTheme.localShapes.roundedDp11_5
                )
        )
    }
}

@Composable
fun TextBetweenDividers(
    text: String,
    textStyle: TextStyle
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            thickness = MaterialTheme.localDimens.dp0_5,
            color = MaterialTheme.localColors.white_alpha06
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp20)
        Text(text = text, style = textStyle)
        VerticalSpacer(height = MaterialTheme.localDimens.dp20)
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
fun LoginScreenPreview() {
    LoginScreen()
}

