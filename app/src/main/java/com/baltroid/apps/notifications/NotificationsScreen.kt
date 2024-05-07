package com.baltroid.apps.notifications

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.Caption
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikCard
import com.baltroid.designsystem.component.MekikCardDouble
import com.baltroid.designsystem.theme.electricVioletColor

@Composable
fun NotificationsScreen() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 56.dp, bottom = 64.dp), contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            FilterHeadText(text = stringResource(id = R.string.title_notification), modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp))
        }
        item {
            Caption(text = stringResource(id = R.string.title_courses), color = MaterialTheme.colorScheme.electricVioletColor, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 12.dp))
        }
        items(2) {
            MekikCard(caption = "Taner Özdeş", title = "Dijital Dünyanın Antidijital Nefesi", category = "") {

            }
        }
        item {
            Caption(text = stringResource(id = R.string.title_instructors), color = MaterialTheme.colorScheme.electricVioletColor, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 12.dp))
        }
        items(2) {
            MekikCard(caption = "CCIM Institute", title = "Zeynep Begüm Kocaçal", category = "") {

            }
        }
        item {
            Caption(text = stringResource(id = R.string.title_academies), color = MaterialTheme.colorScheme.electricVioletColor, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 12.dp))
        }
        items(2) {
            MekikCardDouble(captionTop = "1 Eğitmen", title = "Barış Kılıçarslan Akademi", captionBottom = "3 Eğitim") {

            }
        }
    }
}

@Composable
@Preview
fun PreviewNotificationsScreen() {
    NotificationsScreen()
}