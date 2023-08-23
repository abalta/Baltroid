package com.baltroid.ui.screens.menu.login

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.R
import com.baltroid.presentation.screens.menu.login.LoginViewModel
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navigate: (String) -> Unit,
    onLoggedIn: () -> Unit
) {
    val loginState = viewModel.uiState.collectAsStateWithLifecycle().value.loginUiModel
    LaunchedEffect(loginState) {
        if (loginState != null) {
            onLoggedIn.invoke()
        }
    }

    LoginScreenContent(
        loginViewModel = viewModel,
        forgotPassword = {},
        navigate = navigate
    )
}

@Composable
fun LoginScreenContent(
    loginViewModel: LoginViewModel,
    forgotPassword: () -> Unit,
    navigate: (String) -> Unit
) {
    val loginData = loginViewModel.uiStateLoginFields.collectAsStateWithLifecycle().value
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .systemBarsPadding()
    ) {
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp36))
        Text(
            text = stringResource(id = R.string.member_login),
            style = MaterialTheme.localTextStyles.menuBarTitle,
            color = MaterialTheme.localColors.white_alpha09
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
        Divider(
            color = MaterialTheme.localColors.white_alpha06,
            thickness = dimensionResource(id = R.dimen.dp1),
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp38))
        SimpleImage(imgResId = R.drawable.ic_profile)
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp16))
        UserInputArea(
            title = R.string.email,
            value = loginData.email ?: "",
            onValueChange = loginViewModel::updateEmail,
            modifier = Modifier.fillMaxWidth(0.7f)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
        UserInputArea(
            title = R.string.password,
            value = loginData.password ?: "",
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = loginViewModel::updatePassword,
            modifier = Modifier.fillMaxWidth(0.7f)
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp36))
        Text(
            text = stringResource(id = R.string.forgot_password),
            style = MaterialTheme.localTextStyles.imageCardText,
            color = MaterialTheme.localColors.orange,
            modifier = Modifier.clickable { forgotPassword.invoke() }
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            TextBetweenDividers(
                text = stringResource(id = R.string.sign_in),
                textStyle = MaterialTheme.localTextStyles.signInTextWhite,
                onClick = { loginViewModel.login() }
            )
            TextBetweenDividers(
                text = stringResource(id = R.string.sign_up),
                textStyle = MaterialTheme.localTextStyles.signInTextWhite,
                onClick = { navigate.invoke(HitReadsScreens.RegisterScreen.route) }
            )
        }
    }
}

@Composable
fun UserInputArea(
    @StringRes title: Int,
    value: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.localTextStyles.profileScreenUserInfo,
            color = MaterialTheme.localColors.white
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp11))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.localTextStyles.profileScreenUserInfo,
            cursorBrush = SolidColor(MaterialTheme.localColors.white),
            maxLines = 1,
            visualTransformation = visualTransformation,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.localShapes.roundedDp11_5)
                .border(
                    width = dimensionResource(id = R.dimen.dp1),
                    color = MaterialTheme.localColors.white_alpha05,
                    shape = MaterialTheme.localShapes.roundedDp11_5
                )
                .padding(dimensionResource(id = R.dimen.dp10))
        )
    }
}

@Composable
fun TextBetweenDividers(
    text: String,
    textStyle: TextStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            thickness = dimensionResource(id = R.dimen.dp1),
            color = MaterialTheme.localColors.white_alpha06
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
        Text(text = text, style = textStyle, color = MaterialTheme.localColors.orange)
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
        Divider(
            thickness = dimensionResource(id = R.dimen.dp1),
            color = MaterialTheme.localColors.white_alpha06
        )
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun LoginScreenPreview() {

}

