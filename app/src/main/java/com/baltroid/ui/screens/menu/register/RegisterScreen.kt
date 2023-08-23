package com.baltroid.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.R
import com.baltroid.util.conditional

@Composable
fun RegisterScreen(
    viewModel: AuthenticationViewModel = hiltViewModel(),
    navigate: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val uiState = viewModel.uiStateRegister.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    LaunchedEffect(uiState.errorMsg) {
        uiState.errorMsg?.let {
            Toast.makeText(context, context.getString(it), Toast.LENGTH_LONG).show()
            viewModel.clearErrorMessage()
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            Toast.makeText(context, context.getString(R.string.register_success), Toast.LENGTH_LONG)
                .show()
            navigate.invoke(HitReadsScreens.LoginScreen.route)
            viewModel.clearSuccess()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .systemBarsPadding()
    ) {
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp36))
        IconlessMenuBar(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            title = stringResource(id = R.string.new_user),
            onBackClick = onBackClick
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp18))
            UserInputArea(
                title = R.string.name_surname,
                value = uiState.name.fieldValue,
                onValueChange = viewModel::updateNameField,
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            if (uiState.name.errorMsg != null) {
                Text(
                    text = stringResource(id = uiState.name.errorMsg),
                    style = MaterialTheme.localTextStyles.writingCardInfo,
                    color = MaterialTheme.localColors.orange
                )
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp9))
            UserInputArea(
                title = R.string.email,
                value = uiState.email.fieldValue,
                onValueChange = viewModel::updateEmailField,
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            if (uiState.email.errorMsg != null) {
                Text(
                    text = stringResource(id = uiState.email.errorMsg),
                    style = MaterialTheme.localTextStyles.writingCardInfo,
                    color = MaterialTheme.localColors.orange
                )
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp9))
            UserInputArea(
                title = R.string.password,
                value = uiState.password.fieldValue,
                onValueChange = viewModel::updatePasswordField,
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp9))
            Text(
                text = stringResource(id = R.string.invalid_password),
                style = MaterialTheme.localTextStyles.writingCardInfo,
                color = if (uiState.password.errorMsg == null) MaterialTheme.localColors.white_alpha03
                else MaterialTheme.localColors.orange,
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp9))
            UserInputArea(
                title = R.string.password_confirm,
                value = uiState.passwordConfirm.fieldValue,
                onValueChange = viewModel::updatePasswordConfirmField,
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            if (uiState.passwordConfirm.errorMsg != null) {
                Text(
                    text = stringResource(id = uiState.passwordConfirm.errorMsg),
                    style = MaterialTheme.localTextStyles.writingCardInfo,
                    color = MaterialTheme.localColors.orange
                )
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp30))
            Column {
                CheckBoxWithText(
                    uiState.isPrivacyPolicyChecked.isChecked,
                    stringResource(id = R.string.accept_privacy_policy)
                ) {
                    viewModel.updatePrivacyPolicy(isChecked = !it)
                }
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp15))
                CheckBoxWithText(
                    uiState.isCookiePolicyChecked.isChecked,
                    stringResource(id = R.string.accept_cookie_policy)
                ) {
                    viewModel.updateCookiePolicy(isChecked = !it)
                }
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp38))
            TextBetweenDividers(
                text = stringResource(id = R.string.save),
                textStyle = MaterialTheme.localTextStyles.signInTextWhite,
                onClick = viewModel::register
            )
            TextBetweenDividers(
                text = stringResource(id = R.string.already_member),
                textStyle = MaterialTheme.localTextStyles.signInTextWhite,
                onClick = { /*TODO*/ })
        }
    }
}

@Composable
fun CheckBoxWithText(
    isChecked: Boolean,
    text: String,
    onClick: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp13)),
        modifier = Modifier.clickable { onClick.invoke(isChecked) },
    ) {
        Box(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp14))
                .border(
                    dimensionResource(id = R.dimen.dp1),
                    MaterialTheme.localColors.white_alpha05,
                    RectangleShape
                )
                .conditional(
                    isChecked
                ) {
                    background(Color.Green)
                }
        )

        Text(
            text = text,
            style = MaterialTheme.localTextStyles.signInTextWhite,
            color = MaterialTheme.localColors.white_alpha03
        )
    }
}

@Preview
@Composable
fun PreviewRegister() {
    RegisterScreen(navigate = {}) {}
}