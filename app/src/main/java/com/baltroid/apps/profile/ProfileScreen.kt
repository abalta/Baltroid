package com.baltroid.apps.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.apps.home.hideSheetAndUpdateState
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.CircularButton
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikFilledButton
import com.baltroid.designsystem.component.MekikOutlinedButton
import com.baltroid.designsystem.component.MekikTextField
import com.baltroid.designsystem.component.ProfileInfoCard
import com.baltroid.designsystem.component.shadow
import com.baltroid.designsystem.theme.regularStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val sheetState = rememberModalBottomSheetState(
        true
    )
    val sheetStateScope = rememberCoroutineScope()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
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
            CircularButton(
                icon = R.drawable.ic_back,
                Modifier
                    .padding(top = 28.dp, start = 24.dp)
                    .align(Alignment.TopStart)
            ) {

            }
            Image(
                painter = painterResource(id = R.drawable.sample_profile_image),
                modifier = Modifier
                    .size(98.dp)
                    .clip(CircleShape),
                contentDescription = "profile"
            )
        }
        ProfileInfoCard(icon = R.drawable.ic_profile_name, info = "Ceyda Yılmaztürk")
        ProfileInfoCard(icon = R.drawable.ic_mail, info = "c******@******.***")
        ProfileInfoCard(icon = R.drawable.ic_phone, info = "05***** ** **")
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
                text = "Kurumsal satış ile bireysel satış birbirinden farklıdır. Kurumsal Satış konusunda izlememiz gereken bir süreç vardır.",
                style = MaterialTheme.typography.regularStyle,
                modifier = Modifier.padding(horizontal = 26.dp, vertical = 16.dp)
            )
        }
        MekikOutlinedButton(text = "Düzenle", modifier = Modifier.padding(top = 16.dp)) {
            showBottomSheet = true
        }
        if (showBottomSheet) {
            ModalBottomSheet(modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp),
                dragHandle = null,
                sheetState = sheetState,
                containerColor = Color.White,
                onDismissRequest = {
                    showBottomSheet = false
                }) {
                IconButton(onClick = {
                    sheetStateScope.hideSheetAndUpdateState(sheetState) {
                        showBottomSheet = false
                    }
                }, modifier = Modifier.padding(top = 30.dp, start = 24.dp)) {
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
                        painter = painterResource(id = R.drawable.sample_profile_image),
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape),
                        contentDescription = "profile"
                    )
                    IconButton(onClick = {

                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "add"
                        )
                    }
                }
                MekikTextField(label = stringResource(id = R.string.title_name)) {

                }
                MekikTextField(label = stringResource(id = R.string.title_surname)) {

                }
                MekikTextField(label = stringResource(id = R.string.title_email)) {

                }
                MekikTextField(label = stringResource(id = R.string.title_phone)) {

                }
                MekikTextField(label = stringResource(id = R.string.title_about)) {

                }
                MekikFilledButton(
                    text = "Kaydet", modifier = Modifier.padding(
                        top = 16.dp, start = 13.dp, end = 13.dp, bottom = 16.dp
                    )
                ) {

                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}