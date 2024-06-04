package com.baltroid.apps.course

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.baltroid.apps.comment.CommentsSheet
import com.baltroid.apps.ext.collectAsStateLifecycleAware
import com.baltroid.apps.navigation.OnAction
import com.baltroid.apps.navigation.UiAction
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.BoldHeadText
import com.baltroid.designsystem.component.Caption
import com.baltroid.designsystem.component.CaptionMedium
import com.baltroid.designsystem.component.CircularButton
import com.baltroid.designsystem.component.ExpandableCard
import com.baltroid.designsystem.component.MediumSmallText
import com.baltroid.designsystem.component.ReadMoreClickableText
import com.baltroid.designsystem.theme.electricVioletColor
import com.baltroid.designsystem.theme.goldenTainoiColor

@Composable
fun CourseScreen(
    viewModel: CourseDetailViewModel,
    onAction: OnAction
) {
    val uiState by viewModel.courseDetailState.collectAsStateLifecycleAware()
    var showCommentSheet by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.courseDetail != null -> {
                val courseDetail = uiState.courseDetail!!
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(211.dp)
                        ) {
                            AsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                model = courseDetail.cover,
                                placeholder = painterResource(id = R.drawable.logo),
                                contentDescription = "Image",
                                contentScale = ContentScale.FillBounds
                            )
                            Row(Modifier.fillMaxWidth()) {
                                CircularButton(
                                    icon = R.drawable.ic_back,
                                    Modifier.padding(top = 28.dp, start = 24.dp)
                                ) {
                                    onAction(
                                        UiAction.OnBackClick
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                CircularButton(
                                    icon = R.drawable.ic_share,
                                    Modifier.padding(top = 28.dp, end = 6.dp)
                                ) {

                                }
                                CircularButton(
                                    icon = R.drawable.ic_fav,
                                    Modifier.padding(top = 28.dp, end = 24.dp)
                                ) {

                                }
                            }
                        }
                    }
                    item {
                        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                            BoldHeadText(
                                courseDetail.title,
                                Modifier
                                    .padding(start = 20.dp, top = 8.dp, end = 20.dp),
                            )
                            CaptionMedium(
                                text = courseDetail.author,
                                color = MaterialTheme.colorScheme.electricVioletColor,
                                modifier = Modifier.padding(start = 20.dp, top = 12.dp, end = 20.dp)
                            )
                            Caption(
                                text = courseDetail.academy,
                                color = Color.Black.copy(0.5f),
                                modifier = Modifier.padding(start = 20.dp, top = 12.dp, end = 20.dp)
                            )
                            Row(
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    top = 12.dp,
                                    end = 20.dp
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_duration),
                                    modifier = Modifier.size(15.dp),
                                    contentDescription = "time"
                                )
                                MediumSmallText(
                                    text = "17dk",
                                    Modifier.padding(start = 8.dp, end = 8.dp)
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_level),
                                    modifier = Modifier.size(15.dp),
                                    contentDescription = "level"
                                )
                                MediumSmallText(
                                    text = courseDetail.level,
                                    Modifier.padding(start = 8.dp, end = 8.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    top = 12.dp,
                                    end = 20.dp
                                ).clickable {
                                    showCommentSheet = true
                                },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_comment),
                                    modifier = Modifier.size(15.dp),
                                    contentDescription = "comment",
                                    tint = MaterialTheme.colorScheme.electricVioletColor
                                )
                                MediumSmallText(
                                    text = "Yorumlar (${courseDetail.commentCount})",
                                    Modifier.padding(start = 8.dp, end = 8.dp)
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_star),
                                    modifier = Modifier.size(15.dp),
                                    contentDescription = "star",
                                    tint = MaterialTheme.colorScheme.goldenTainoiColor
                                )
                                MediumSmallText(
                                    text = courseDetail.ratingAvg.toString(),
                                    Modifier.padding(start = 8.dp, end = 8.dp)
                                )
                            }
                            ReadMoreClickableText(
                                text = courseDetail.description,
                                Modifier.padding(start = 20.dp, top = 16.dp, end = 20.dp)
                            )
                        }
                    }
                    items(courseDetail.chapters.size) {
                        ExpandableCard(title = courseDetail.chapters[it].name, courseDetail.chapters[it].lessons)
                    }
                }
            }
        }
    }
    if (showCommentSheet) {
        CommentsSheet(viewModel) {
            showCommentSheet = false
        }
    }
}

@Preview
@Composable
fun PreviewCourseScreen() {
    CourseScreen(hiltViewModel()) {

    }
}