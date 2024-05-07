package com.baltroid.apps.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.baltroid.apps.AppState
import com.baltroid.apps.auth.LoginSheet
import com.baltroid.apps.navigation.BottomBar
import com.baltroid.apps.navigation.BottomNavGraph
import com.baltroid.apps.navigation.OnAction
import com.baltroid.apps.navigation.UiAction
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.ButtonText
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikCard
import com.baltroid.designsystem.component.MekikHorizontalCard
import com.baltroid.designsystem.component.MekikOutlinedButton
import com.baltroid.designsystem.component.MekikPagerItem
import com.baltroid.designsystem.component.MenuButton
import com.baltroid.designsystem.component.RowtitleText
import com.baltroid.designsystem.component.TopBar
import com.baltroid.designsystem.component.shadow
import com.baltroid.designsystem.navbar.BottomBarScreen
import com.baltroid.designsystem.theme.electricVioletColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val sheetState = rememberModalBottomSheetState(
        true
    )
    val sheetStateScope = rememberCoroutineScope()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val navController = rememberNavController()
    val appState = remember(navController) {
        AppState(navController)
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            AnimatedVisibility(
                visible = appState.shouldShowTopBar,
                enter = fadeIn(
                    animationSpec = tween(300)
                ),
                exit = fadeOut(
                    animationSpec = tween(300)
                )
            ) {
                TopBar(navController = navController, {
                    showBottomSheet = true
                }, {
                    navController.navigate(BottomBarScreen.Notifications.route)
                })
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier
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
                    Image(painter = painterResource(id = R.drawable.ic_close), contentDescription = "close")
                }
                FilterHeadText(text = stringResource(id = R.string.title_menu), showIcon = false, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 22.dp, top = 24.dp))
                MenuButton(text = stringResource(id = R.string.title_courses), icon = R.drawable.ic_bottom_play) {
                    navController.navigate(BottomBarScreen.Courses.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    sheetStateScope.hideSheetAndUpdateState(sheetState) {
                        showBottomSheet = false
                    }
                }
                MenuButton(text = stringResource(id = R.string.title_instructors), icon = R.drawable.ic_instructor) {
                    navController.navigate(BottomBarScreen.Instructors.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    sheetStateScope.hideSheetAndUpdateState(sheetState) {
                        showBottomSheet = false
                    }
                }
                MenuButton(text = stringResource(id = R.string.title_academies), icon = R.drawable.ic_academy) {
                    navController.navigate(BottomBarScreen.Academies.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    sheetStateScope.hideSheetAndUpdateState(sheetState) {
                        showBottomSheet = false
                    }
                }
                MenuButton(text = stringResource(id = R.string.title_favorites), icon = R.drawable.ic_favorite) {
                    navController.navigate(BottomBarScreen.Favorites.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    sheetStateScope.hideSheetAndUpdateState(sheetState) {
                        showBottomSheet = false
                    }
                }
                MenuButton(text = stringResource(id = R.string.title_settings), icon = R.drawable.ic_settings) {
                    navController.navigate(BottomBarScreen.Settings.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    sheetStateScope.hideSheetAndUpdateState(sheetState) {
                        showBottomSheet = false
                    }
                }
                MenuButton(text = stringResource(id = R.string.title_about_app), icon = R.drawable.ic_about) {

                }
            }
        }
        BottomNavGraph(
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun CoroutineScope.hideSheetAndUpdateState(sheetState: SheetState, showBottomSheet: () -> Unit) = launch {
    sheetState.hide()
}.invokeOnCompletion {
    if (!sheetState.isVisible) {
        showBottomSheet.invoke()
    }
}

@Composable
fun HomeContent(onAction: OnAction) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 56.dp, bottom = 64.dp), contentPadding = PaddingValues(vertical = 12.dp)) {
        item {
            HomePager()
        }
        item {
            RowtitleText(text = "Son Eklenen Eğitimler", modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 22.dp))
        }
        item {
            RecentCourses(onAction)
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(12) {
            MekikCard(caption = "Taner Özdeş", title = "Dijital Dünyanın Antidijital Nefesi", category = "Popüler") {
                onAction(
                    UiAction.OnCourseClick
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(13.dp))
            MekikOutlinedButton(text = "Tüm Eğitimler") {
                onAction(UiAction.OnAllCoursesClick)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePager() {
    val pagerState = rememberPagerState(pageCount = {
        3
    })
    var showLoginSheet by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier =
        Modifier
            .padding(start = 13.dp, end = 13.dp)
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
        HorizontalPager(state = pagerState) {
            MekikPagerItem()
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 13.dp, end = 13.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(
                Modifier.height(6.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                repeat(3) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.electricVioletColor else Color(
                            0x4DFFFFFF
                        )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(6.dp)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.electricVioletColor,
                                RoundedCornerShape(4.dp)
                            )
                            .clip(RoundedCornerShape(4.dp))
                            .background(color)

                    )
                }
            }
            TextButton(
                onClick = {
                    showLoginSheet = true
                }
            ) {
                ButtonText("Giriş Yap")
            }
            if (showLoginSheet) {
                LoginSheet {
                    showLoginSheet = false
                }
            }
        }

    }
}

@Composable
fun RecentCourses(onAction: OnAction) {
    LazyRow(contentPadding = PaddingValues(start = 14.dp, top = 14.dp), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
        items(5) {
            MekikHorizontalCard(caption = "Taner Özdeş", title = "Dijital Dünyanın Antidijital Nefesi", category = "Popüler") {
                onAction(
                    UiAction.OnCourseClick
                )
            }
        }

    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}