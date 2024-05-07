package com.baltroid.apps.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.Caption
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikCheckBox
import com.baltroid.designsystem.theme.electricVioletColor
import com.baltroid.designsystem.theme.regularStyle

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 56.dp, bottom = 64.dp)) {
        FilterHeadText(text = stringResource(id = R.string.title_settings), modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp), showIcon = false)
        Caption(text = stringResource(id = R.string.notification_settings), color = MaterialTheme.colorScheme.electricVioletColor, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 8.dp))
        MekikCheckBox(label = "Yeni Eğitim Eklendiğinde", modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 14.dp)) {
            
        }
        MekikCheckBox(label = "Yeni Eğitmen Eklendiğinde", modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 12.dp)) {

        }
        MekikCheckBox(label = "Yeni Akademi Eklendiğinde", modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 12.dp)) {

        }
        MekikCheckBox(label = "Favorilerime İçerik Eklendiğinde", modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 12.dp)) {

        }
        HorizontalDivider(modifier = Modifier.padding(top = 16.dp, start = 24.dp, end = 24.dp))
        Caption(text = stringResource(id = R.string.application_settings), color = MaterialTheme.colorScheme.electricVioletColor, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 16.dp))
        val options = listOf("Açık Mod", "Koyu Mod")
        val selectedOption = remember { mutableStateOf(options[0]) }
        options.forEach { option ->
            Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp).clickable {
                selectedOption.value = option
            }, verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedOption.value == option,
                    onClick = { selectedOption.value = option }
                )
                Text(
                    text = option,
                    style = MaterialTheme.typography.regularStyle
                )
            }
        }
        HorizontalDivider(modifier = Modifier.padding(top = 16.dp, start = 24.dp, end = 24.dp))
    }
}

@Composable
@Preview
fun SettingsScreenPreview() {
    SettingsScreen()
}