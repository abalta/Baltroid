package com.baltroid.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.paging.compose.collectAsLazyPagingItems
import com.baltroid.apps.R
import com.baltroid.presentation.screens.home.HomeViewModel
import com.baltroid.presentation.screens.home.isLoading
import com.baltroid.ui.common.CroppedImage
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.screens.home.HomeScreen
import com.baltroid.ui.screens.home.detail.HomeDetailScreen
import com.baltroid.ui.screens.home.detail.HomeDetailScreenState
import com.baltroid.ui.screens.home.detail.HomeDetailViewModel
import com.baltroid.ui.screens.home.filter.FilterScreen
import com.baltroid.ui.screens.interactive.InteractiveScreen
import com.baltroid.ui.screens.onboarding.OnboardingScreen
import com.baltroid.ui.screens.onboarding.OnboardingScreenState
import com.baltroid.ui.screens.reading.ReadingScreen
import com.baltroid.ui.screens.reading.ReadingScreenState
import com.baltroid.ui.theme.localColors
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HitReadsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
    openMenuScreen: () -> Unit = { navController.navigate(HitReadsScreens.MenuScreen.route) }
) {

    val homeDetailViewModel: HomeDetailViewModel = hiltViewModel()
    var isLoading by remember {
        mutableStateOf(false)
    }

    Box {
        AnimatedNavHost(
            navController = navController,
            startDestination = HitReadsScreens.OnboardingScreen.route,
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.localColors.black)
        ) {

            menuGraph(navController)

            composable(
                route = HitReadsScreens.OnboardingScreen.route
            ) {
                val screenState = OnboardingScreenState(
                    R.drawable.woods_image, messageText = stringResource(id = R.string.welcome)
                )
                OnboardingScreen(screenState = screenState) {
                    navController.navigate(HitReadsScreens.HomeScreen.route) {
                        popUpToInclusive(HitReadsScreens.OnboardingScreen.route)
                        launchSingleTop = true
                    }
                }
            }
            composable(
                route = HitReadsScreens.HomeScreen.route
            ) {
                val viewModel: HomeViewModel = hiltViewModel()
                val uiStates = viewModel.uiState.collectAsStateWithLifecycle()
                    .value

                isLoading = uiStates.isLoading
                HomeScreen(
                    uiStates = uiStates.originals.collectAsLazyPagingItems(), openMenuScreen = openMenuScreen
                ) { route, item ->
                    if (route == HitReadsScreens.HomeDetailScreen.route) {
                        homeDetailViewModel.setHomeDetailState(item)
                    }
                    navController.navigate(route)
                }
            }
            composable(
                route = HitReadsScreens.FilterScreen.route
            ) {
                FilterScreen(applyFilter = {
                    //navigate with item ids
                }) {
                    navController.popBackStack()
                }
            }
            composable(
                route = HitReadsScreens.HomeDetailScreen.route
            ) {
                val state = homeDetailViewModel.homeDetailState
                    .collectAsStateWithLifecycle().value
                state?.let {
                    HomeDetailScreen(
                        screenState = it,
                        openMenuScreen = openMenuScreen,
                    ) { route, itemId ->
                        //todo navArgs
                        navController.navigate(route)
                    }
                }
            }
            composable(
                route = HitReadsScreens.ReadingScreen.route
            ) {
                val screenState = ReadingScreenState(
                    bodyText = LoremIpsum(2000).values.joinToString(),
                    title = "KİMSE GERÇEK DEĞİL",
                    subtitle = "ZEYNEP SEY",
                    numberOfComments = 12,
                    numberOfViews = 4412,
                    numberOfNotification = 14
                )
                ReadingScreen(
                    screenState = screenState, openMenuScreen = openMenuScreen
                )
            }
            composable(
                route = HitReadsScreens.InteractiveScreen.route
            ) {
                InteractiveScreen(
                    openMenuScreen = openMenuScreen
                )
            }
        }
        if (isLoading) {
            HitReadsLoading()
        }
    }
}

@Composable
fun HitReadsLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CroppedImage(imgResId = R.drawable.hitreads_placeholder, Modifier.fillMaxSize())
    }
}

fun NavOptionsBuilder.popUpToInclusive(route: String) {
    popUpTo(route) {
        inclusive = true
    }
}