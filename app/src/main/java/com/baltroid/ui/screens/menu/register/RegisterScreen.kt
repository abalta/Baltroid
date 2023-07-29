package com.baltroid.ui.screens.menu.register

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.R
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.IconlessMenuBar
import com.baltroid.ui.screens.menu.login.TextBetweenDividers
import com.baltroid.ui.screens.menu.login.UserInputArea
import com.baltroid.ui.screens.viewmodels.AuthenticationViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.conditional

@Composable
fun RegisterScreen(
    viewModel: AuthenticationViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState = viewModel.uiStateRegister.collectAsStateWithLifecycle().value
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .systemBarsPadding()
    ) {
        VerticalSpacer(height = MaterialTheme.localDimens.dp36)
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
            VerticalSpacer(height = MaterialTheme.localDimens.dp18)
            UserInputArea(
                title = R.string.name_surname,
                value = uiState.name.fieldValue,
                onValueChange = viewModel::updateNameField,
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            if (uiState.name.errorMsg != null) {
                Text(
                    text = stringResource(id = uiState.name.errorMsg),
                    style = MaterialTheme.localTextStyles.signUpTextOrangeGrotesk
                )
            }
            VerticalSpacer(height = MaterialTheme.localDimens.dp9)
            UserInputArea(
                title = R.string.email,
                value = uiState.email.fieldValue,
                onValueChange = viewModel::updateEmailField,
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            if (uiState.email.errorMsg != null) {
                Text(
                    text = stringResource(id = uiState.email.errorMsg),
                    style = MaterialTheme.localTextStyles.signUpTextOrangeGrotesk
                )
            }
            VerticalSpacer(height = MaterialTheme.localDimens.dp9)
            UserInputArea(
                title = R.string.password,
                value = uiState.password.fieldValue,
                onValueChange = viewModel::updatePasswordField,
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp9)
            if (uiState.password.errorMsg != null) {
                Text(
                    text = stringResource(id = uiState.password.errorMsg),
                    style = MaterialTheme.localTextStyles.passwordInfo,
                    modifier = Modifier.fillMaxWidth(0.65f)
                )
            }
            VerticalSpacer(height = MaterialTheme.localDimens.dp9)
            UserInputArea(
                title = R.string.password_confirm,
                value = uiState.passwordConfirm.fieldValue,
                onValueChange = viewModel::updatePasswordConfirmField,
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            if (uiState.passwordConfirm.errorMsg != null) {
                Text(
                    text = stringResource(id = uiState.passwordConfirm.errorMsg),
                    style = MaterialTheme.localTextStyles.signUpTextOrangeGrotesk
                )
            }
            VerticalSpacer(height = MaterialTheme.localDimens.dp30)
            Column {
                CheckBoxWithText(
                    uiState.isPrivacyPolicyChecked.isChecked,
                    "Kullanım şartlarını kabul ediyorum"
                )
                VerticalSpacer(height = MaterialTheme.localDimens.dp15)
                CheckBoxWithText(uiState.isCookiePolicyChecked.isChecked, "Çerez politikası ")
            }
            VerticalSpacer(height = MaterialTheme.localDimens.dp38)
            TextBetweenDividers(
                text = stringResource(id = R.string.save),
                textStyle = MaterialTheme.localTextStyles.signInTextWhite,
                onClick = viewModel::regiter
            )
            TextBetweenDividers(
                text = stringResource(id = R.string.already_member),
                textStyle = MaterialTheme.localTextStyles.signUpTextOrange,
                onClick = { /*TODO*/ })
        }
    }
}

@Composable
fun CheckBoxWithText(
    isChecked: Boolean,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp13),
    ) {
        Box(
            modifier = Modifier
                .size(MaterialTheme.localDimens.dp14)
                .conditional(
                    isChecked
                ) {
                    background(Color.Green)
                }
        )

        Text(
            text = text,
            style = MaterialTheme.localTextStyles.passwordInfo
        )
    }
}

@Preview
@Composable
fun PreviewRegister() {
    RegisterScreen() {}
}