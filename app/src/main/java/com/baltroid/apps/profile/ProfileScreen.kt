package com.baltroid.apps.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.baltroid.apps.auth.LoginSheet
import com.baltroid.apps.ext.collectAsStateLifecycleAware
import com.baltroid.apps.home.hideSheetAndUpdateState
import com.baltroid.apps.navigation.OnAction
import com.baltroid.apps.navigation.UiAction
import com.baltroid.core.common.ErrorModel
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.ErrorCard
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikFilledButton
import com.baltroid.designsystem.component.MekikOutlinedButton
import com.baltroid.designsystem.component.MekikTextField
import com.baltroid.designsystem.component.ProfileInfoCard
import com.baltroid.designsystem.component.shadow
import com.baltroid.designsystem.theme.regularStyle
import de.palm.composestateevents.EventEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onAction: OnAction
) {
    val profileState by viewModel.profileState.collectAsStateLifecycleAware()
    val sheetState = rememberModalBottomSheetState(
        true
    )
    val sheetStateScope = rememberCoroutineScope()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    var email by rememberSaveable { mutableStateOf("") }
    var firstname by rememberSaveable { mutableStateOf("") }
    var lastname by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var about by rememberSaveable { mutableStateOf("") }

    var error: ErrorModel? by remember { mutableStateOf(null) }
    var showLoginSheet by rememberSaveable { mutableStateOf(false) }

    EventEffect(event = profileState.error, onConsumed = viewModel::onConsumedFailedEvent) {
        error = it
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            profileState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            profileState.profile != null -> {
                showLoginSheet = false
                val profile = profileState.profile!!
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(138.dp), contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.sample_profile_background),
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = "profile_background",
                            contentScale = ContentScale.FillWidth
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_auth),
                            modifier = Modifier
                                .size(98.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .padding(16.dp),
                            contentDescription = "profile"
                        )
                    }
                    ProfileInfoCard(icon = R.drawable.ic_profile_name, info = profile.name)
                    ProfileInfoCard(icon = R.drawable.ic_mail, info = profile.email)
                    ProfileInfoCard(
                        icon = R.drawable.ic_phone,
                        info = profile.phone.ifEmpty { "Telefon numarası girebilirsiniz" })
                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 13.dp, end = 13.dp)
                            .fillMaxWidth()
                            .height(94.dp)
                            .shadow(
                                color = Color.Black.copy(0.05f),
                                offsetX = 2.dp,
                                offsetY = 2.dp,
                                borderRadius = 9.dp,
                                spread = 4.dp,
                                blurRadius = 6.dp
                            )
                            .clip(RoundedCornerShape(9.dp))
                            .background(Color.White)
                    ) {
                        Text(
                            text = profile.about.ifEmpty { "Hakkımda kısmını doldurabilirsiniz" },
                            style = MaterialTheme.typography.regularStyle,
                            modifier = Modifier.padding(horizontal = 26.dp, vertical = 16.dp)
                        )
                    }
                    MekikOutlinedButton(
                        text = "Düzenle",
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        showBottomSheet = true
                    }
                    MekikOutlinedButton(
                        text = "Çıkış", modifier = Modifier.padding(
                            top = 4.dp,
                            bottom = 16.dp
                        )
                    ) {
                        viewModel.logout()
                        onAction(UiAction.OnLogoutClick)
                    }
                }
            }

        }
        error?.let {
            if (error?.code == 401) {
                ErrorCard(
                    message = stringResource(id = R.string.error_unauthorized),
                    buttonText = stringResource(
                        id = R.string.text_btn_login
                    )
                ) {
                    showLoginSheet = true
                }
            }
        }
    }
    if (showLoginSheet) {
        LoginSheet(onDismiss = {
            showLoginSheet = false
        }, checkLogin = {
            viewModel.getProfile()
        })
    }
    if (showBottomSheet) {
        val profile = profileState.profile!!
        ModalBottomSheet(modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
            dragHandle = null,
            sheetState = sheetState,
            containerColor = Color.White,
            onDismissRequest = {
                showBottomSheet = false
            }) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .verticalScroll(rememberScrollState())
            ) {
                IconButton(
                    onClick = {
                        sheetStateScope.hideSheetAndUpdateState(sheetState) {
                            showBottomSheet = false
                        }
                    },
                    modifier = Modifier.padding(top = 30.dp, start = 24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "close"
                    )
                }
                FilterHeadText(
                    text = stringResource(id = R.string.title_edit_profile),
                    showIcon = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 22.dp, top = 24.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        (-24).dp
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_auth),
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .padding(8.dp),
                        contentDescription = "profile"
                    )
                    /*IconButton(onClick = {

                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "add"
                        )
                    }*/
                }
                MekikTextField(
                    label = stringResource(id = R.string.title_name),
                    typedText = profile.firstname
                ) {
                    firstname = it
                }
                MekikTextField(
                    label = stringResource(id = R.string.title_surname),
                    typedText = profile.lastname
                ) {
                    lastname = it
                }
                MekikTextField(
                    label = stringResource(id = R.string.title_email),
                    typedText = profile.email,
                    keyboardType = KeyboardType.Email
                ) {
                    email = it
                }
                MekikTextField(
                    label = stringResource(id = R.string.title_phone),
                    typedText = profile.phone,
                    keyboardType = KeyboardType.Phone
                ) {
                    phone = it
                }
                MekikTextField(
                    label = stringResource(id = R.string.title_about),
                    typedText = profile.about
                ) {
                    about = it
                }
                MekikFilledButton(
                    text = "Kaydet", modifier = Modifier.padding(
                        top = 16.dp,
                        start = 13.dp,
                        end = 13.dp,
                        bottom = 16.dp
                    )
                ) {
                    viewModel.updateProfile(
                        email = email,
                        firstname = firstname,
                        lastname = lastname,
                        phone = phone,
                        about = about
                    )
                    showBottomSheet = false
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen {

    }
}