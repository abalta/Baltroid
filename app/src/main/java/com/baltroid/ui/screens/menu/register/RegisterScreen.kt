package com.baltroid.ui.screens.menu.register

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.R
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.IconlessMenuBar
import com.baltroid.ui.navigation.HitReadsScreens
import com.baltroid.ui.screens.menu.login.TextBetweenDividers
import com.baltroid.ui.screens.menu.login.UserInputArea
import com.baltroid.ui.screens.viewmodels.AuthenticationViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.conditional
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogButtons
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun RegisterScreen(
    viewModel: AuthenticationViewModel = hiltViewModel(),
    navigate: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val uiState = viewModel.uiStateRegister.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    val dateDialogState = rememberMaterialDialogState()

    var selectedDate by remember {
        mutableStateOf(SelectedDate())
    }

    LaunchedEffect(uiState.errorMsg) {
        uiState.errorMsg?.let {
            Toast.makeText(context, context.getString(it), Toast.LENGTH_LONG).show()
            viewModel.clearErrorMessage()
        }
    }
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess == true) {
            Toast.makeText(context, context.getString(R.string.register_success), Toast.LENGTH_LONG)
                .show()
            navigate.invoke(HitReadsScreens.LoginScreen.route)
            viewModel.clearSuccess()
        }
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = { DefaultDateTimeDialogButtons() }
    ) {
        datepicker(
            title = stringResource(id = R.string.birthdate),
            colors = DatePickerDefaults.colors(headerBackgroundColor = MaterialTheme.localColors.orange)
        ) {
            selectedDate = selectedDate.copy(
                day = it.dayOfMonth.toString(),
                month = it.monthValue.toString(),
                year = it.year.toString()
            )
            viewModel.updateBirthDateField(it.toString())
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
                    style = MaterialTheme.localTextStyles.spaceGrotesk14Medium,
                    color = MaterialTheme.localColors.orange
                )
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp9))
            UserInputArea(
                title = R.string.username,
                value = uiState.username.fieldValue,
                onValueChange = viewModel::updateUsernameField,
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            if (uiState.username.errorMsg != null) {
                Text(
                    text = stringResource(id = uiState.username.errorMsg),
                    style = MaterialTheme.localTextStyles.spaceGrotesk14Medium,
                    color = MaterialTheme.localColors.orange
                )
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp9))
            BirthdateField(
                date = selectedDate,
                hasError = uiState.birthdate.errorMsg != null,
                modifier = Modifier
                    .fillMaxWidth(0.65f)
            ) {
                dateDialogState.show()
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
                    style = MaterialTheme.localTextStyles.spaceGrotesk14Medium,
                    color = MaterialTheme.localColors.orange
                )
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp9))
            UserInputArea(
                title = R.string.password,
                value = uiState.password.fieldValue,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = viewModel::updatePasswordField,
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp9))
            Text(
                text = stringResource(id = R.string.invalid_password),
                style = MaterialTheme.localTextStyles.spaceGrotesk14Medium,
                color = if (uiState.password.errorMsg == null) MaterialTheme.localColors.white_alpha03
                else MaterialTheme.localColors.orange,
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            /* VerticalSpacer(height = dimensionResource(id = R.dimen.dp9))
             UserInputArea(
                 title = R.string.password_confirm,
                 value = uiState.passwordConfirm.fieldValue,
                 onValueChange = viewModel::updatePasswordConfirmField,
                 modifier = Modifier.fillMaxWidth(0.65f)
             )
             if (uiState.passwordConfirm.errorMsg != null) {
                 Text(
                     text = stringResource(id = uiState.passwordConfirm.errorMsg),
                     style = MaterialTheme.localTextStyles.spaceGrotesk14Medium,
                     color = MaterialTheme.localColors.orange
                 )
             }*/
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp30))
            Column {
                CheckBoxWithText(
                    isChecked = uiState.userAgreement.isChecked,
                    text = stringResource(id = R.string.accept_privacy_policy),
                    hasError = uiState.userAgreement.errorMsg != null
                ) {
                    viewModel.updatePrivacyPolicy(isChecked = !it)
                }
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp15))
                CheckBoxWithText(
                    isChecked = uiState.cookiePolicy.isChecked,
                    hasError = uiState.cookiePolicy.errorMsg != null,
                    text = stringResource(id = R.string.accept_cookie_policy)
                ) {
                    viewModel.updateCookiePolicy(isChecked = !it)
                }
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp38))
            Divider(
                thickness = dimensionResource(id = R.dimen.dp0_5),
                color = MaterialTheme.localColors.white_alpha06
            )
            TextBetweenDividers(
                text = stringResource(id = R.string.save),
                textStyle = MaterialTheme.localTextStyles.spaceGrotesk18Medium,
                onClick = viewModel::register
            )
            Divider(
                thickness = dimensionResource(id = R.dimen.dp0_5),
                color = MaterialTheme.localColors.white_alpha06
            )
            TextBetweenDividers(
                text = stringResource(id = R.string.already_member),
                textStyle = MaterialTheme.localTextStyles.spaceGrotesk18Medium,
                onClick = { navigate.invoke(HitReadsScreens.LoginScreen.route) })
            Divider(
                thickness = dimensionResource(id = R.dimen.dp0_5),
                color = MaterialTheme.localColors.white_alpha06
            )
        }
    }
}

@Composable
private fun MaterialDialogButtons.DefaultDateTimeDialogButtons() {
    positiveButton(stringResource(id = R.string.ok))
    negativeButton(stringResource(id = R.string.cancel))
}

@Composable
fun CheckBoxWithText(
    isChecked: Boolean,
    text: String,
    hasError: Boolean,
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
            style = MaterialTheme.localTextStyles.spaceGrotesk14Medium,
            color = if (!hasError) MaterialTheme.localColors.white_alpha03
            else MaterialTheme.localColors.orange
        )
    }
}

@Composable
fun BirthdateField(
    date: SelectedDate,
    modifier: Modifier = Modifier,
    hasError: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick.invoke() }
    ) {
        Text(
            text = stringResource(id = R.string.birthdate),
            style = MaterialTheme.localTextStyles.poppins18Regular,
            color = MaterialTheme.localColors.white
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp11))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dp52))
                .clip(MaterialTheme.localShapes.roundedDp11_5)
                .border(
                    width = dimensionResource(id = R.dimen.dp1),
                    color = if (!hasError) MaterialTheme.localColors.white_alpha05
                    else MaterialTheme.localColors.orange,
                    shape = MaterialTheme.localShapes.roundedDp11_5
                )
                .padding(dimensionResource(id = R.dimen.dp10))
        ) {
            Text(
                text = date.day ?: stringResource(id = R.string.day),
                style = MaterialTheme.localTextStyles.poppins12Regular,
                color = MaterialTheme.localColors.white
            )
            SimpleIcon(iconResId = R.drawable.ic_arrow_down)
            Text(
                text = date.month ?: stringResource(id = R.string.month),
                style = MaterialTheme.localTextStyles.poppins12Regular,
                color = MaterialTheme.localColors.white
            )
            SimpleIcon(iconResId = R.drawable.ic_arrow_down)
            Text(
                text = date.year ?: stringResource(id = R.string.year),
                style = MaterialTheme.localTextStyles.poppins12Regular,
                color = MaterialTheme.localColors.white
            )
            SimpleIcon(iconResId = R.drawable.ic_arrow_down)
        }
    }
}

data class SelectedDate(
    val day: String? = null,
    val month: String? = null,
    val year: String? = null
)

@Preview
@Composable
fun PreviewRegister() {
}