package com.baltroid.apps.academies

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.apps.navigation.OnAction
import com.baltroid.apps.navigation.UiAction
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikCardDouble

@Composable
fun AcademiesScreen(onAction: OnAction) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = 56.dp, bottom = 64.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            FilterHeadText(
                text = stringResource(id = R.string.title_academies),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp)
            )
        }
        items(12) {
            MekikCardDouble(
                captionTop = "CCIM Institute",
                title = "Zeynep Begüm Kocaçal",
                captionBottom = "0 Eğitim"
            ) {
                onAction(UiAction.AcademyClick)
            }
        }
    }
}

@Preview
@Composable
fun PreviewInstructorsScreen() {
    AcademiesScreen {}
}