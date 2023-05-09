package com.baltroid.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.baltroid.apps.R
import com.baltroid.ui.screens.home.HomeScreen
import com.baltroid.ui.screens.home.HomeScreenState
import com.baltroid.ui.screens.home.detail.HomeDetailScreen
import com.baltroid.ui.screens.home.detail.HomeDetailScreenState
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
    AnimatedNavHost(
        navController = navController,
        startDestination = HitReadsScreens.OnboardingScreen.route,
        modifier = modifier.background(MaterialTheme.localColors.black)
    ) {

        menuGraph(navController)

        composable(
            route = HitReadsScreens.OnboardingScreen.route
        ) {
            val screenState = OnboardingScreenState(
                R.drawable.woods_image,
                messageText = stringResource(id = R.string.welcome)
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
            val screenState = HomeScreenState(
                author = "ZEYNEP SEY",
                firstName = "KİMSE GERÇEK DEĞİL",
                secondName = "Araf, Aydınlık Ve Aşık",
                genres = listOf("ROMANTİK", "GENÇLİK"),
                numberOfNotification = 12,
                numberOfStory = 12,
                numberOfViews = 1002,
                numberOfComments = 142,
                numberOfFavorites = 5,
                episodeSize = 35,
                summary = LoremIpsum(16).values.joinToString(),
                imgUrls = listOf(
                    "https://www.figma.com/file/MYxJBHOTh2JfbmrYbojuxc/image/1d56515ab14098684701024283a07d386bbb94e7?fuid=1097272770330818914",
                    "https://www.figma.com/file/MYxJBHOTh2JfbmrYbojuxc/image/1d56515ab14098684701024283a07d386bbb94e7?fuid=1097272770330818914",
                )
            )
            HomeScreen(
                screenState = screenState,
                openMenuScreen = openMenuScreen
            ) { route, itemId ->
                // todo navArgs
                navController.navigate(route)
            }
        }
        composable(
            route = HitReadsScreens.HomeDetailScreen.route
        ) {
            val screenState = HomeDetailScreenState(
                id = 0,
                author = "ZEYNEP SEY",
                firstName = "KİMSE GERÇEK DEĞİL",
                secondName = "Araf, Aydınlık Ve Aşık",
                genres = listOf("ROMANTİK", "GENÇLİK"),
                numberOfNotification = 12,
                numberOfViews = 1002,
                numberOfComments = 142,
                episodeSize = 35,
                summary = "Kim olduğunu sorguladıkça dünyasının sahtelikten İbaret olduğunu anlamaya başlayan Işıl Özsoydan, öğrendiği gerçekleri..."
            )
            HomeDetailScreen(
                screenState = screenState,
                openMenuScreen = openMenuScreen,
            ) { route, itemId ->
                //todo navArgs
                navController.navigate(route)
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
                screenState = screenState,
                openMenuScreen = openMenuScreen
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
}

fun NavOptionsBuilder.popUpToInclusive(route: String) {
    popUpTo(route) {
        inclusive = true
    }
}