package com.baltroid.apps.courses

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.apps.home.RecentCourses
import com.baltroid.apps.navigation.OnAction
import com.baltroid.apps.navigation.UiAction
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikCard
import com.baltroid.designsystem.component.MekikOutlinedButton
import com.baltroid.designsystem.component.RowtitleText
import com.baltroid.designsystem.theme.electricVioletColor

@Composable
fun MyCoursesScreen(onAction: OnAction) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 56.dp, bottom = 64.dp), contentPadding = PaddingValues(vertical = 12.dp)) {
        item {
            FilterHeadText(text = stringResource(id = R.string.title_my_courses), modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp))
        }
        items(12) {
            MekikCard(caption = "Taner Özdeş", title = "Dijital Dünyanın Antidijital Nefesi", category = "Popüler") {
                onAction(
                    UiAction.OnCourseClick
                )
            }
        }
        item {
            MekikOutlinedButton(text = stringResource(id = R.string.text_btn_all_courses), modifier = Modifier.padding(vertical = 16.dp), onClick = {

            })
        }
    }
}

@Preview
@Composable
fun PreviewMyCoursesScreen() {
    MyCoursesScreen {

    }
}