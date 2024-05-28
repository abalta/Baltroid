package com.baltroid.apps.academy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.baltroid.apps.ext.collectAsStateLifecycleAware
import com.baltroid.apps.navigation.OnAction
import com.baltroid.apps.navigation.UiAction
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.CircularButton
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MediumText
import com.baltroid.designsystem.component.MediumTitleText
import com.baltroid.designsystem.component.MekikCard
import com.baltroid.designsystem.component.MekikCardDouble
import com.baltroid.designsystem.component.ReadMoreClickableText

@Composable
fun AcademyScreen(
    viewModel: AcademyDetailViewModel,
    onAction: OnAction
) {
    val uiState by viewModel.academyDetailState.collectAsStateLifecycleAware()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.academyDetail != null -> {
                val academyDetail = uiState.academyDetail!!
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(165.dp)
                        ) {
                            AsyncImage(
                                placeholder = painterResource(id = R.drawable.logo),
                                model = academyDetail.logo,
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = "academy",
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
                                academyDetail.name,
                                Modifier
                                    .padding(start = 24.dp, top = 16.dp, end = 24.dp),
                            )
                            MediumText(
                                text = "${academyDetail.courses.size} Eğitim",
                                color = Color.Black.copy(0.5f),
                                modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp)
                            )
                            if (academyDetail.about.isNotEmpty()) {
                                ReadMoreClickableText(
                                    text = academyDetail.about,
                                    Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp)
                                )
                            }
                        }
                    }
                    if (academyDetail.teachers.isNotEmpty()) {
                        item {
                            FilterHeadText(
                                text = "Eğitmenler",
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, top = 22.dp, end = 24.dp),
                                showIcon = false
                            )
                        }
                        items(key = {
                            it.id
                        }, items = academyDetail.teachers) {
                            MekikCardDouble(
                                captionTop = it.academyName,
                                title = it.name,
                                captionBottom = it.courses,
                                painter = it.photo
                            ) {
                                onAction(
                                    UiAction.OnInstructorClick(it.id)
                                )
                            }
                        }
                    }
                    if (academyDetail.courses.isNotEmpty()) {
                        item {
                            FilterHeadText(
                                text = "Eğitimler",
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, top = 22.dp, end = 24.dp),
                                showIcon = false
                            )
                        }
                        items(key = {
                            it.id
                        }, items = academyDetail.courses) {
                            MekikCard(
                                caption = it.author,
                                title = it.title,
                                popular = it.popular,
                                painter = it.cover
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
}

@Preview
@Composable
fun PreviewAcademyScreen() {
    AcademyScreen(hiltViewModel()) {

    }
}