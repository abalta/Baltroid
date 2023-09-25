package com.baltroid.ui.screens.menu.login

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.R
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.IconlessMenuBar
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.screens.interactive.EpisodeButton
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navigate: (String) -> Unit,
    navigateBack: () -> Unit
) {
    val loginState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(loginState.loginUiModel) {
        if (loginState.loginUiModel != null) {
            navigateBack.invoke()
        }
    }

    LaunchedEffect(loginState.sendResetPasswordMessage) {
        loginState.sendResetPasswordMessage?.let {
            Toast.makeText(context, context.getString(it), Toast.LENGTH_LONG).show()
            viewModel.resetInfoMessage()
        }
    }

    LoginScreenContent(
        loginViewModel = viewModel,
        navigate = navigate,
        navigateBack = navigateBack,
        sendResetPasswordEmail = viewModel::sendResetPassword
    )

    LaunchedEffect(loginState.error) {
        loginState.error?.let {
            Toast.makeText(context, context.getString(it), Toast.LENGTH_LONG).show()
            viewModel.clearLoginError()
        }
    }
}

@Composable
fun LoginScreenContent(
    loginViewModel: LoginViewModel,
    navigate: (String) -> Unit,
    navigateBack: () -> Unit,
    sendResetPasswordEmail: (email: String) -> Unit
) {
    val loginData = loginViewModel.uiStateLoginFields.collectAsStateWithLifecycle().value
    var showForgotPasswordPopup by remember {
        mutableStateOf(false)
    }
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.localColors.black)
                .systemBarsPadding()
        ) {
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp36))
            IconlessMenuBar(title = stringResource(id = R.string.member_login)) {
                navigateBack.invoke()
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
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
                    style = MaterialTheme.localTextStyles.poppins14Medium,
                    color = MaterialTheme.localColors.orange,
                    modifier = Modifier.clickable {
                        showForgotPasswordPopup = true
                    }
                )
                VerticalSpacer(height = R.dimen.dp24)
                Divider(
                    thickness = dimensionResource(id = R.dimen.dp0_5),
                    color = MaterialTheme.localColors.white_alpha06
                )
                TextBetweenDividers(
                    text = stringResource(id = R.string.sign_in),
                    textStyle = MaterialTheme.localTextStyles.spaceGrotesk18Medium,
                    onClick = { loginViewModel.login() }
                )
                Divider(
                    thickness = dimensionResource(id = R.dimen.dp0_5),
                    color = MaterialTheme.localColors.white_alpha06
                )
                TextBetweenDividers(
                    text = stringResource(id = R.string.sign_up),
                    textStyle = MaterialTheme.localTextStyles.spaceGrotesk18Medium,
                    onClick = { navigate.invoke(HitReadsScreens.RegisterScreen.route) }
                )
                Divider(
                    thickness = dimensionResource(id = R.dimen.dp0_5),
                    color = MaterialTheme.localColors.white_alpha06
                )
            }
        }
        if (showForgotPasswordPopup) {
            ForgotPasswordPopup(
                onDialogDismissed = {
                    showForgotPasswordPopup = false
                },
                onClick = {
                    sendResetPasswordEmail.invoke(it)
                    showForgotPasswordPopup = false
                }
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
            style = MaterialTheme.localTextStyles.poppins18Regular,
            color = MaterialTheme.localColors.white
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp11))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.localTextStyles.poppins18Regular.copy(color = MaterialTheme.localColors.white),
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
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
        Text(text = text, style = textStyle, color = MaterialTheme.localColors.orange)
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
    }
}

@Composable
fun IconBetweenDividers(
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
        SimpleIcon(iconResId = iconRes)
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
    }
}

@Composable
fun ForgotPasswordPopup(
    onClick: (email: String) -> Unit,
    onDialogDismissed: () -> Unit
) {
    var email by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = { onDialogDismissed.invoke() }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f)
                .clip(MaterialTheme.localShapes.roundedDp24)
                .background(MaterialTheme.localColors.black)
                .border(
                    dimensionResource(id = R.dimen.dp1),
                    MaterialTheme.localColors.white,
                    MaterialTheme.localShapes.roundedDp24
                )
        ) {
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
                onClick.invoke(email)
            }
        }
    }

}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun LoginScreenPreview() {

}

