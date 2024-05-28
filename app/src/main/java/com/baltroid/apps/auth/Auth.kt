package com.baltroid.apps.auth

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.ext.collectAsStateLifecycleAware
import com.baltroid.apps.ext.isValidEmail
import com.baltroid.apps.ext.isValidPassword
import com.baltroid.apps.home.hideSheetAndUpdateState
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.Caption
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikCheckBox
import com.baltroid.designsystem.component.MekikFilledButton
import com.baltroid.designsystem.component.MekikOutlinedButton
import com.baltroid.designsystem.component.MekikTextField
import de.palm.composestateevents.EventEffect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginSheet(
    viewModel: AuthViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    checkLogin: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        true
    )
    var showRegisterSheet by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    val sheetStateScope = rememberCoroutineScope()
    val snackbarStateScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val uiState by viewModel.authState.collectAsStateLifecycleAware()

    EventEffect(event = uiState.error, onConsumed = viewModel::onConsumedFailedEvent) {
        snackbarStateScope.launch {
            snackbarHostState.showSnackbar(message = it)
        }
    }

    if (uiState.isLoading) {
        Log.d("LoginSheet", "Loading")
    } else if (uiState.loginModel != null) {
        checkLogin()
    } else if (uiState.error.toString().isNotEmpty()) {
        Log.d("LoginSheet", "Error: ${uiState.error}")
    }

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        dragHandle = null,
        sheetState = sheetState,
        containerColor = Color.White,
        onDismissRequest = {
            onDismiss()
        }) {
        Box {
            Scaffold(
                containerColor = Color.White,
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.padding(bottom = 48.dp)
                    )
                }
            ) { padding ->
                Column {
                    IconButton(enabled = uiState.isLoading.not(), onClick = {
                        sheetStateScope.hideSheetAndUpdateState(sheetState) {
                            onDismiss()
                        }
                    }, modifier = Modifier.padding(top = 30.dp, start = 24.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "close"
                        )
                    }
                    FilterHeadText(
                        text = stringResource(id = R.string.title_login),
                        showIcon = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 22.dp, top = 24.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_auth), modifier = Modifier
                            .padding(top = 24.dp)
                            .align(Alignment.CenterHorizontally), contentDescription = "auth"
                    )
                    MekikTextField(
                        label = stringResource(id = R.string.title_email),
                        error = emailError,
                        errorMessage = "Hatalı E-posta Girişi",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, top = 24.dp),
                        keyboardType = KeyboardType.Email
                    ) {
                        emailError = false
                        email = it
                    }
                    MekikTextField(
                        label = stringResource(id = R.string.text_password),
                        error = passwordError,
                        errorMessage = "Hatalı Şifre Girişi",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp),
                        keyboardType = KeyboardType.Password,
                        visualTransformation = PasswordVisualTransformation()
                    ) {
                        passwordError = false
                        password = it
                    }
                    Caption(
                        text = stringResource(id = R.string.text_btn_forgot_password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                    )
                    MekikFilledButton(
                        text = stringResource(id = R.string.text_btn_login),
                        isEnable = uiState.isLoading.not(),
                        modifier = Modifier.padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    ) {
                        if (email.isValidEmail().not()) {
                            emailError = true
                            return@MekikFilledButton
                        } else if (password.isValidPassword().not()) {
                            passwordError = true
                            return@MekikFilledButton
                        } else {
                            viewModel.login(email, password)
                        }
                    }
                    Caption(
                        text = stringResource(id = R.string.text_no_register), modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                    )
                    MekikOutlinedButton(
                        text = stringResource(id = R.string.text_btn_free_register),
                        isEnable = uiState.isLoading.not(),
                        modifier = Modifier.padding(top = 16.dp, start = 11.dp, end = 11.dp)
                    ) {
                        showRegisterSheet = true
                    }
                }
            }
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        if (showRegisterSheet) {
            RegisterSheet(viewModel, onDismiss = {
                showRegisterSheet = false
            }, checkLogin = checkLogin)
        }
    }
}

@Composable
@Preview
fun LoginSheetPreview() {
    LoginSheet(onDismiss = {

    }, checkLogin = {

    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterSheet(
    viewModel: AuthViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    checkLogin: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        true
    )
    val sheetStateScope = rememberCoroutineScope()
    var nameError by rememberSaveable { mutableStateOf(false) }
    var surnameError by rememberSaveable { mutableStateOf(false) }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var surname by rememberSaveable { mutableStateOf("") }
    var agreement by rememberSaveable { mutableStateOf(false) }
    val snackbarStateScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val uiState by viewModel.authState.collectAsStateLifecycleAware()

    EventEffect(event = uiState.error, onConsumed = viewModel::onConsumedFailedEvent) {
        snackbarStateScope.launch {
            snackbarHostState.showSnackbar(message = it)
        }
    }

    if (uiState.isLoading) {
        Log.d("LoginSheet", "Loading")
    } else if (uiState.loginModel != null) {
        Log.d("LoginSheet", "LoginModel: ${uiState.loginModel}")
        checkLogin()
    } else if (uiState.error.toString().isNotEmpty()) {
        Log.d("LoginSheet", "Error: ${uiState.error}")
    }

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        dragHandle = null,
        sheetState = sheetState,
        containerColor = Color.White,
        onDismissRequest = {
            onDismiss()
        }) {
        Box {
            Scaffold(
                containerColor = Color.White,
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.padding(bottom = 48.dp)
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                        .verticalScroll(rememberScrollState())
                ) {
                    IconButton(onClick = {
                        sheetStateScope.hideSheetAndUpdateState(sheetState) {
                            onDismiss()
                        }
                    }, modifier = Modifier.padding(top = 30.dp, start = 24.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "close"
                        )
                    }
                    FilterHeadText(
                        text = stringResource(id = R.string.title_register),
                        showIcon = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 22.dp, top = 24.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_auth), modifier = Modifier
                            .padding(top = 24.dp)
                            .align(Alignment.CenterHorizontally), contentDescription = "auth"
                    )
                    MekikTextField(
                        label = stringResource(id = R.string.title_name), modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, top = 24.dp),
                        error = nameError,
                        errorMessage = "Hatalı İsim Girişi"
                    ) {
                        nameError = false
                        name = it
                    }
                    MekikTextField(
                        label = stringResource(id = R.string.title_surname), modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp),
                        error = surnameError,
                        errorMessage = "Hatalı Soyisim Girişi"
                    ) {
                        surnameError = false
                        surname = it
                    }
                    MekikTextField(
                        label = stringResource(id = R.string.title_email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp),
                        error = emailError,
                        errorMessage = "Hatalı E-posta Girişi",
                        keyboardType = KeyboardType.Email
                    ) {
                        emailError = false
                        email = it
                    }
                    MekikTextField(
                        label = stringResource(id = R.string.text_password), modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp),
                        error = passwordError,
                        errorMessage = "Hatalı Şifre Girişi",
                        keyboardType = KeyboardType.Password,
                        visualTransformation = PasswordVisualTransformation()
                    ) {
                        passwordError = false
                        password = it
                    }
                    MekikCheckBox(
                        label = "Mekik'in bana yenilikler ve güncellemeler ile ilgili eposta göndermesini onaylıyorum.",
                        modifier = Modifier.padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    ) {
                        agreement = true
                    }
                    MekikFilledButton(
                        text = stringResource(id = R.string.title_register),
                        modifier = Modifier.padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    ) {
                        if (name.isEmpty()) {
                            nameError = true
                            return@MekikFilledButton
                        } else if (surname.isEmpty()) {
                            surnameError = true
                            return@MekikFilledButton
                        } else if (email.isValidEmail().not()) {
                            emailError = true
                            return@MekikFilledButton
                        } else if (password.isValidPassword().not()) {
                            passwordError = true
                            return@MekikFilledButton
                        } else {
                            viewModel.register(email, password, name, surname, agreement)
                        }
                    }
                    Caption(
                        text = stringResource(id = R.string.text_privacy), modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                    )
                    MekikOutlinedButton(
                        text = stringResource(id = R.string.text_btn_already_register),
                        modifier = Modifier.padding(top = 16.dp, start = 11.dp, end = 11.dp)
                    ) {
                        onDismiss()
                    }
                }
            }
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
@Preview
fun RegisterSheetPreview() {
    RegisterSheet(
        onDismiss = {

        },
        checkLogin = {

        }
    )
}