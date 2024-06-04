package com.baltroid.apps.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.baltroid.apps.ext.collectAsStateLifecycleAware
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.Caption
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikCard
import com.baltroid.designsystem.component.shadow
import com.baltroid.designsystem.theme.electricVioletColor
import com.baltroid.designsystem.theme.mediumBigStyle
import com.baltroid.designsystem.theme.woodsmokeColor
import kotlinx.coroutines.Job
import androidx.compose.foundation.lazy.items
import com.baltroid.apps.navigation.OnAction
import com.baltroid.apps.navigation.UiAction
import com.baltroid.designsystem.component.MekikCardDouble
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onAction: OnAction
) {

    val searchState by searchViewModel.searchState.collectAsStateLifecycleAware()

    var text by remember { mutableStateOf("") }

    val searchStateScope = rememberCoroutineScope()
    var job by remember {
        mutableStateOf<Job?>(null)
    }

    Column(modifier = Modifier.padding(top = 56.dp, bottom = 64.dp)) {
        FilterHeadText(
            text = stringResource(id = R.string.title_search), modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp), showIcon = false
        )
        searchState.totalModel?.let {
            Caption(
                text = it.total,
                color = MaterialTheme.colorScheme.woodsmokeColor.copy(0.3f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 20.dp)
            )
        }
        TextField(
            value = text,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bottom_search),
                    contentDescription = null
                )
            },
            onValueChange = {
                text = it
                job?.cancel()
                if (it.isEmpty()) return@TextField
                job = searchStateScope.launch {
                    delay(500)
                    searchViewModel.search(it)
                }
            },
            singleLine = true,
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(60.dp)
                .shadow(
                    color = Color.Black.copy(0.05f),
                    offsetX = 2.dp,
                    offsetY = 2.dp,
                    borderRadius = 9.dp,
                    spread = 4.dp,
                    blurRadius = 6.dp
                )
                .clip(RoundedCornerShape(9.dp))
                .background(Color.White),
            placeholder = {
                Caption(
                    text = "Aranacak Kelime",
                    color = MaterialTheme.colorScheme.woodsmokeColor.copy(0.3f)
                )
            },
            textStyle = MaterialTheme.typography.mediumBigStyle,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedPlaceholderColor = Color.Transparent,
                focusedPlaceholderColor = Color.Transparent
            ),
        )
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
            searchState.searchModel?.let { searchModel ->
                if (searchModel.courses.isNotEmpty()) {
                    item {
                        Caption(
                            text = "Eğitim",
                            color = MaterialTheme.colorScheme.electricVioletColor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 24.dp, top = 20.dp)
                        )
                    }
                    items(searchModel.courses) {
                        MekikCard(caption =it.author, title = it.title, popular = it.popular, painter = it.cover) {
                            onAction(UiAction.OnCourseClick(it.id))
                        }
                    }
                }
                if (searchModel.teachers.isNotEmpty()) {
                    item {
                        Caption(
                            text = "Eğitmen",
                            color = MaterialTheme.colorScheme.electricVioletColor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 24.dp, top = 20.dp)
                        )
                    }
                    items(searchModel.teachers) {
                        MekikCardDouble(captionTop = it.academyName,
                            title = it.name,
                            captionBottom = it.courses,
                            painter = it.photo,
                            onClick = {
                                onAction(UiAction.OnInstructorClick(it.id))
                            })
                    }
                }
                if (searchModel.academies.isNotEmpty()) {
                    item {
                        Caption(
                            text = "Akademi",
                            color = MaterialTheme.colorScheme.electricVioletColor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 24.dp, top = 20.dp)
                        )
                    }
                    items(searchModel.academies) {
                        MekikCardDouble(
                            captionTop = it.teacherCount,
                            title = it.name,
                            captionBottom = it.courseCount,
                            painter = it.logo,
                        ) {
                            onAction(UiAction.OnAcademyClick(it.id))
                        }
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewSearchScreen() {
    SearchScreen{

    }
}