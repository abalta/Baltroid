package com.baltroid.apps.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.baltroid.apps.academies.AcademiesScreen
import com.baltroid.apps.academy.AcademyScreen
import com.baltroid.apps.course.CourseScreen
import com.baltroid.apps.courses.CoursesScreen
import com.baltroid.apps.courses.MyCoursesScreen
import com.baltroid.apps.favorites.FavoritesScreen
import com.baltroid.apps.home.HomeContent
import com.baltroid.apps.instructor.InstructorScreen
import com.baltroid.apps.instructors.InstructorsScreen
import com.baltroid.apps.notifications.NotificationsScreen
import com.baltroid.apps.profile.ProfileScreen
import com.baltroid.apps.search.SearchScreen
import com.baltroid.apps.settings.SettingsScreen
import com.baltroid.designsystem.navbar.BottomBarScreen
import com.baltroid.designsystem.navbar.DetailsScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route,
        enterTransition = {
            fadeIn(animationSpec = tween(600))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(600))
        }
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeContent {
                when (it) {
                    is UiAction.OnAllCoursesClick -> navController.navigate(BottomBarScreen.Courses.route)
                    is UiAction.OnCourseClick -> navController.navigate(DetailsScreen.Course.route)
                    else -> {

                    }
                }
            }
        }
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen()
        }
        composable(route = BottomBarScreen.Play.route) {
            MyCoursesScreen {

            }
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomBarScreen.Courses.route) {
            CoursesScreen {

            }
        }
        composable(route = BottomBarScreen.Academies.route) {
            AcademiesScreen {
                navController.navigate(DetailsScreen.Academy.route)
            }
        }
        composable(route = BottomBarScreen.Instructors.route) {
            InstructorsScreen {
                navController.navigate(Graph.DETAIL)
            }
        }
        composable(route = BottomBarScreen.Favorites.route) {
            FavoritesScreen()
        }
        composable(route = BottomBarScreen.Notifications.route) {
            NotificationsScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        startDestination = DetailsScreen.Instructor.route,
        route = Graph.DETAIL
    ) {
        composable(DetailsScreen.Instructor.route) {
            InstructorScreen {

            }
        }
        composable(DetailsScreen.Academy.route) {
            AcademyScreen {

            }
        }
        composable(DetailsScreen.Course.route) {
            CourseScreen()
        }
    }
}

typealias OnAction = (UiAction) -> Unit