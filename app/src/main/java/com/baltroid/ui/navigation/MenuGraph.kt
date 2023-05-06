package com.baltroid.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.baltroid.ui.screens.menu.MenuScreen
import com.baltroid.ui.screens.menu.author.AuthorScreen
import com.baltroid.ui.screens.menu.favorites.FavoritesScreen
import com.baltroid.ui.screens.menu.place_marks.PlaceMarksScreen
import com.baltroid.ui.screens.menu.profile.ProfileScreen
import com.baltroid.ui.screens.menu.settings.SettingsScreen
import com.baltroid.ui.screens.menu.shop.ShopScreen
import com.google.accompanist.navigation.animation.composable


const val ANIMATION_DURATION = 700

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.menuGraph(navController: NavController) {
    navigation(
        startDestination = HitReadsScreens.MenuScreen.route,
        route = HitReadsGraphs.Menu.route
    ) {
        composable(
            HitReadsScreens.MenuScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    tween(ANIMATION_DURATION)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    tween(ANIMATION_DURATION)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down,
                    tween(ANIMATION_DURATION)
                )
            }
        ) {
            MenuScreen(
                diamondValue = 15,
                currentUserName = "SELEN PEKMEZCİ",
                onBackClick = { navController.popBackStack() }
            ) { route ->
                navController.navigate(route)
            }
        }
        composable(route = HitReadsScreens.PlaceMarksScreen.route) {
            PlaceMarksScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(route = HitReadsScreens.FavoritesScreen.route) {
            FavoritesScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(route = HitReadsScreens.SettingsScreen.route) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(route = HitReadsScreens.ProfileScreen.route) {
            ProfileScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(route = HitReadsScreens.AuthorScreen.route) {
            AuthorScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(route = HitReadsScreens.ShopScreen.route) {
            ShopScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}