package com.baltroid.apps.instructor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.items
import com.baltroid.apps.ext.collectAsStateLifecycleAware
import com.baltroid.apps.navigation.OnAction
import com.baltroid.apps.navigation.UiAction
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.CircularButton
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MediumBold
import com.baltroid.designsystem.component.MediumTitleText
import com.baltroid.designsystem.component.MekikCard
import com.baltroid.designsystem.component.ReadMoreClickableText
import com.baltroid.designsystem.theme.electricVioletColor

@Composable
fun InstructorScreen(
    viewModel: TeacherDetailViewModel,
    onAction: OnAction
) {
    val uiState by viewModel.teacherDetailState.collectAsStateLifecycleAware()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.teacherDetail != null -> {
                val teacherDetail = uiState.teacherDetail!!
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = teacherDetail.photo,
                                placeholder = painterResource(id = R.drawable.logo),
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = "instructor",
                                contentScale = ContentScale.FillWidth
                            )
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                CircularButton(
                                    icon = R.drawable.ic_back,
                                    Modifier.padding(top = 28.dp, start = 24.dp)
                                ) {
                                    onAction(UiAction.OnBackClick)
                                }
                            }
                        }
                    }
                    item {
                        Column(
                            Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            MediumTitleText(
                                teacherDetail.name,
                                Modifier
                                    .padding(start = 24.dp, top = 16.dp, end = 24.dp),
                            )
                            if (teacherDetail.academyName.isNotEmpty()) {
                                MediumBold(
                                    text = teacherDetail.academyName,
                                    color = MaterialTheme.colorScheme.electricVioletColor,
                                    modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp)
                                )
                            }
                            if (teacherDetail.biography.isNotEmpty()) {
                                ReadMoreClickableText(
                                    text = teacherDetail.biography,
                                    Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp)
                                )
                            }
                        }
                    }
                    item {
                        FilterHeadText(
                            text = "Eğitimler (${teacherDetail.coursesCount})",
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, top = 22.dp, end = 24.dp)
                        )
                    }
                    items(key = {
                        it.id
                    }, items = teacherDetail.courses) {
                        MekikCard(
                            caption = it.author,
                            title = it.title,
                            painter = it.cover,
                            popular = it.popular
                        ) {
                            onAction(
                                UiAction.OnCourseClick(it.id)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewInstructorScreen() {
    InstructorScreen(hiltViewModel()) {

    }
}