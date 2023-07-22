package com.baltroid.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.baltroid.apps.R
import com.baltroid.presentation.screens.menu.login.LoginViewModel
import com.baltroid.ui.common.CroppedImage
import com.baltroid.ui.screens.home.HomeScreen
import com.baltroid.ui.screens.home.detail.HomeDetailScreen
import com.baltroid.ui.screens.home.filter.FilterScreen
import com.baltroid.ui.screens.interactive.InteractiveScreen
import com.baltroid.ui.screens.onboarding.OnboardingScreen
import com.baltroid.ui.screens.onboarding.OnboardingViewModel
import com.baltroid.ui.screens.playground.PlaygroundScreen
import com.baltroid.ui.screens.reading.ReadingScreen
import com.baltroid.ui.screens.viewmodels.OriginalViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.util.orZero
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HitReadsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
    openMenuScreen: () -> Unit = {
        navController.navigate(HitReadsScreens.MenuScreen.route)
        /* navController.navigate(HitReadsScreens.PlaygroundScreen.route)*/
    }
) {

    val loginViewModel: LoginViewModel = hiltViewModel()
    val originalViewModel: OriginalViewModel = hiltViewModel()
    val isLoading = remember {
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

            menuGraph(navController, loginViewModel)

            composable(
                route = HitReadsScreens.OnboardingScreen.route
            ) {
                val viewModel: OnboardingViewModel = hiltViewModel()
                val onboardingState = viewModel.uiStateOnboarding
                    .collectAsStateWithLifecycle()
                    .value

                OnboardingScreen(screenState = onboardingState) {
                    navController.navigate(HitReadsScreens.HomeScreen.route) {
                        popUpToInclusive(HitReadsScreens.OnboardingScreen.route)
                        launchSingleTop = true
                    }
                }
            }
            composable(
                route = HitReadsScreens.HomeScreen.route
            ) {
                HomeScreen(
                    viewModel = originalViewModel,
                    openMenuScreen = openMenuScreen,
                ) { route, item ->
                    if (route == HitReadsScreens.HomeDetailScreen.route) {
                        originalViewModel.setSharedUIState(item)
                    }
                    navController.navigate(route)
                }
            }
            composable(
                route = HitReadsScreens.FilterScreen.route
            ) {
                FilterScreen(
                    applyFilter = {
                        //homeViewModel.loadFilteredOriginals(it)
                        navController.popBackStack()
                    }) {
                    navController.popBackStack()
                }
            }
            composable(
                route = HitReadsScreens.HomeDetailScreen.route
            ) {
                HomeDetailScreen(
                    viewModel = originalViewModel,
                    openMenuScreen = openMenuScreen,
                ) { route ->
                    navController.navigate(route)
                }
            }
            composable(
                route = HitReadsScreens.ReadingScreen.route.plus("/{id}"),
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { bacStackEntry ->
                ReadingScreen(
                    viewModel = originalViewModel,
                    originalId = bacStackEntry.arguments?.getInt("id").orZero(),
                    openMenuScreen = openMenuScreen
                )
            }
            composable(
                route = HitReadsScreens.InteractiveScreen.route
            ) {
                InteractiveScreen(
                    viewModel = originalViewModel,
                    openMenuScreen = openMenuScreen
                )
            }
            composable(
                route = HitReadsScreens.PlaygroundScreen.route
            ) {
                PlaygroundScreen()
            }
        }
        if (isLoading.value) {
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