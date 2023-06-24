package com.baltroid.ui.screens.menu.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.screens.menu.login.TextBetweenDividers
import com.baltroid.ui.screens.menu.login.UserInputArea
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localTextStyles

@Composable
fun RegisterScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .systemBarsPadding()
    ) {
        VerticalSpacer(height = MaterialTheme.localDimens.dp36)
        MenuBar(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            title = stringResource(id = R.string.new_user),
            iconResId = R.drawable.ic_star,
            onBackClick = {}
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            VerticalSpacer(height = MaterialTheme.localDimens.dp18)
            UserInputArea(
                title = R.string.name_surname,
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            Text(
                text = "Bu alan boş bırakılamaz",
                style = MaterialTheme.localTextStyles.signUpTextOrangeGrotesk
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp9)
            UserInputArea(
                title = R.string.email,
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            Text(
                text = "Geçerli bir e-posta adresi giriniz.",
                style = MaterialTheme.localTextStyles.signUpTextOrangeGrotesk
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp9)
            UserInputArea(
                title = R.string.password,
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp9)
            Text(
                text = "Şifreniz 8 haneli olmalı ve büyük harf, \n" +
                        "küçük harf, sayı ve simge içermelidir.",
                style = MaterialTheme.localTextStyles.passwordInfo
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp9)
            UserInputArea(
                title = R.string.password_confirm,
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(0.65f)
            )
            Text(
                text = "Şifre eşleşmiyor.",
                style = MaterialTheme.localTextStyles.signUpTextOrangeGrotesk
            )
        }
        VerticalSpacer(height = MaterialTheme.localDimens.dp12)
        TextBetweenDividers(
            text = stringResource(id = R.string.save),
            textStyle = MaterialTheme.localTextStyles.signInTextWhite,
            onClick = { /*TODO*/ })
        TextBetweenDividers(
            text = stringResource(id = R.string.already_member),
            textStyle = MaterialTheme.localTextStyles.signUpTextOrange,
            onClick = { /*TODO*/ })

    }
}

@Preview
@Composable
fun PreviewRegister() {
    RegisterScreen()
}