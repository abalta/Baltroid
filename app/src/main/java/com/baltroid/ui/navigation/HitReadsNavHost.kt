package com.baltroid.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.baltroid.ui.screens.home.HomeScreen
import com.baltroid.ui.screens.home.detail.HomeDetailScreen
import com.baltroid.ui.screens.interactive.InteractiveScreen
import com.baltroid.ui.screens.menu.login.LoginViewModel
import com.baltroid.ui.screens.onboarding.OnboardingScreen
import com.baltroid.ui.screens.onboarding.OnboardingViewModel
import com.baltroid.ui.screens.playground.PlaygroundScreen
import com.baltroid.ui.screens.reading.ReadingScreen
import com.baltroid.ui.screens.viewmodels.OriginalViewModel
import com.baltroid.ui.theme.LocalLoadingState
import com.baltroid.ui.theme.localColors
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
    }
) {

    val loginViewModel: LoginViewModel = hiltViewModel()
    val originalViewModel: OriginalViewModel = hiltViewModel()

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
                ) { route, originalId ->
                    originalViewModel.apply {
                        selectedOriginalId = originalId
                    }
                    navController.navigate(route)
                }
            }
            composable(
                route = HitReadsScreens.HomeDetailScreen.route
            ) {
                HomeDetailScreen(
                    viewModel = originalViewModel,
                    openMenuScreen = openMenuScreen,
                ) { route, episodeId ->
                    originalViewModel.selectedEpisodeId = episodeId
                    navController.navigate(route)
                }
            }
            composable(
                route = HitReadsScreens.ReadingScreen.route
            ) {
                ReadingScreen(
                    viewModel = originalViewModel,
                    navigate = {
                        navController.navigate(it)
                    }
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
            /*composable(
                route = HitReadsScreens.FilterScreen.route
            ) {
                FilterScreen(
                    originalViewModel
                ) {
                    navController.popBackStack()
                }
            }*/
        }
        if (LocalLoadingState.current.value) {
            HitReadsLoading()
        }
    }
}

@Composable
fun HitReadsLoading() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

fun NavOptionsBuilder.popUpToInclusive(route: String) {
    popUpTo(route) {
        inclusive = true
    }
}