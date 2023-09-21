package com.baltroid.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.baltroid.ui.screens.home.HomeScreen
import com.baltroid.ui.screens.home.detail.HomeDetailScreen
import com.baltroid.ui.screens.interactive.InteractiveScreen
import com.baltroid.ui.screens.menu.MenuScreen
import com.baltroid.ui.screens.menu.author.AuthorScreen
import com.baltroid.ui.screens.menu.comments.CommentsScreen
import com.baltroid.ui.screens.menu.favorites.FavoritesScreen
import com.baltroid.ui.screens.menu.login.LoginScreen
import com.baltroid.ui.screens.menu.login.LoginViewModel
import com.baltroid.ui.screens.menu.place_marks.PlaceMarksScreen
import com.baltroid.ui.screens.menu.profile.AvatarsScreen
import com.baltroid.ui.screens.menu.profile.ProfileScreen
import com.baltroid.ui.screens.menu.register.RegisterScreen
import com.baltroid.ui.screens.menu.settings.SettingsScreen
import com.baltroid.ui.screens.menu.shop.ShopScreen
import com.baltroid.ui.screens.menu.shop.ShopScreenState
import com.baltroid.ui.screens.notification.NotificationsScreen
import com.baltroid.ui.screens.onboarding.AnnouncementScreen
import com.baltroid.ui.screens.onboarding.OnboardingScreen
import com.baltroid.ui.screens.onboarding.OnboardingViewModel
import com.baltroid.ui.screens.playground.PlaygroundScreen
import com.baltroid.ui.screens.reading.ReadingScreen
import com.baltroid.ui.screens.viewmodels.AuthenticationViewModel
import com.baltroid.ui.screens.viewmodels.OriginalViewModel
import com.baltroid.ui.theme.LocalLoadingState
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
    }
) {

    val loginViewModel: LoginViewModel = hiltViewModel()
    val originalViewModel: OriginalViewModel = hiltViewModel()
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
    val onboardingViewModel: OnboardingViewModel = hiltViewModel()

    Box {
        AnimatedNavHost(
            navController = navController,
            startDestination = HitReadsScreens.OnboardingScreen.route,
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.localColors.black)
        ) {

            composable(
                HitReadsScreens.MenuScreen.route
            ) {
                MenuScreen(
                    isLoggedIn = loginViewModel.uiStateIsLogged.collectAsStateWithLifecycle().value,
                    onBackClick = { navController.popBackStack() }
                ) { route ->
                    navController.navigate(route) {
                        if (route == HitReadsScreens.HomeScreen.route) {
                            popUpToInclusive(HitReadsScreens.HomeScreen.route)
                        }
                    }
                }
            }
            composable(route = HitReadsScreens.PlaceMarksScreen.route) {
                PlaceMarksScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(route = HitReadsScreens.FavoritesScreen.route) {
                FavoritesScreen(
                    onBackClick = { navController.popBackStack() },
                    navigate = { route, id ->
                        if (route == HitReadsScreens.HomeDetailScreen.route) {
                            originalViewModel.apply {
                                selectedOriginalId = id
                            }
                        }
                        navController.navigate(route)
                    }
                )
            }
            composable(route = HitReadsScreens.SettingsScreen.route) {
                SettingsScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(route = HitReadsScreens.ProfileScreen.route) {
                ProfileScreen(
                    viewModel = authenticationViewModel,
                    onBackClick = { navController.popBackStack() },
                    navigate = {
                        navController.navigate(it)
                    }
                )
            }
            composable(route = HitReadsScreens.AvatarsScreen.route) {
                AvatarsScreen(
                    viewModel = authenticationViewModel
                ) {
                    navController.popBackStack()
                }
            }
            composable(
                route = HitReadsScreens.AuthorScreen.route + "/{authorId}",
                arguments = listOf(navArgument("authorId") { type = NavType.IntType })
            ) { backStackEntry ->
                AuthorScreen(
                    id = backStackEntry.arguments?.getInt("authorId") ?: -1,
                    onBackClick = { navController.popBackStack() },
                    navigate = { route, id ->
                        originalViewModel.selectedOriginalId = id
                        navController.navigate(route)
                    }
                )
            }
            composable(route = HitReadsScreens.ShopScreen.route) {
                val screenState = ShopScreenState(
                    currentPoint = 4500,
                    currentBalance = 15f
                )
                ShopScreen(
                    screenState = screenState,
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(route = HitReadsScreens.LoginScreen.route) {
                LoginScreen(
                    loginViewModel,
                    navigate = { navController.navigate(it) },
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(route = HitReadsScreens.RegisterScreen.route) {
                RegisterScreen(
                    navigate = {
                        navController.navigate(it)
                    }
                ) {
                    navController.popBackStack()
                }
            }
            composable(route = HitReadsScreens.CommentsScreen.route) {
                CommentsScreen(
                    viewModel = hiltViewModel(),
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(
                route = HitReadsScreens.OnboardingScreen.route
            ) {

                val onboardingState = onboardingViewModel.uiStateOnboarding
                    .collectAsStateWithLifecycle()
                    .value

                OnboardingScreen(screenState = onboardingState) {
                    if (onboardingState.announcementModel == null && !onboardingState.isLoading) {
                        navController.navigate(HitReadsScreens.HomeScreen.route) {
                            popUpToInclusive(HitReadsScreens.OnboardingScreen.route)
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(HitReadsScreens.AnnouncementScreen.route)
                    }
                }
            }
            composable(route = HitReadsScreens.AnnouncementScreen.route) {
                AnnouncementScreen(viewModel = onboardingViewModel) {
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
                    navigateContinueReading = { originalId, episodeId, route ->
                        originalViewModel.setSelectedOriginalId(originalId)
                        originalViewModel.setSelectedEpisodeId(episodeId)
                        navController.navigate(route)
                    },
                    openMenuScreen = openMenuScreen,
                ) { route, originalId ->
                    originalId?.let {
                        originalViewModel.selectedOriginalId = it
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
                    episodeId?.let {
                        originalViewModel.setSelectedEpisodeId(episodeId.orZero())
                    }
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
                    viewModel = originalViewModel
                )
            }
            composable(
                route = HitReadsScreens.PlaygroundScreen.route
            ) {
                PlaygroundScreen()
            }
            composable(
                route = HitReadsScreens.NotificationsScreen.route
            ) {
                NotificationsScreen(
                    viewModel = originalViewModel,
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
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