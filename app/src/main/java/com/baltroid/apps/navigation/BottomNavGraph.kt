package com.baltroid.apps.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.baltroid.apps.about.AboutScreen
import com.baltroid.apps.academies.AcademiesScreen
import com.baltroid.apps.academy.AcademyDetailViewModel
import com.baltroid.apps.academy.AcademyScreen
import com.baltroid.apps.course.CourseDetailViewModel
import com.baltroid.apps.course.CourseScreen
import com.baltroid.apps.courses.CoursesScreen
import com.baltroid.apps.courses.MyCoursesScreen
import com.baltroid.apps.favorites.FavoritesScreen
import com.baltroid.apps.home.HomeContent
import com.baltroid.apps.instructor.InstructorScreen
import com.baltroid.apps.instructor.TeacherDetailViewModel
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
                    is UiAction.OnCourseClick -> {
                        navController.navigate("course/${it.id}")
                    }

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
                when (it) {
                    is UiAction.OnAcademyClick -> {
                        navController.navigate("academy/${it.id}")
                    }
                    else -> {

                    }
                }
            }
        }
        composable(route = BottomBarScreen.Instructors.route) {
            InstructorsScreen {
                when (it) {
                    is UiAction.OnInstructorClick -> {
                        navController.navigate("instructor/${it.id}")
                    }

                    else -> {

                    }
                }
            }
        }
        composable(route = BottomBarScreen.Favorites.route) {
            FavoritesScreen()
        }
        composable(route = BottomBarScreen.About.route) {
            AboutScreen {
                navController.popBackStack()
            }
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        startDestination = DetailsScreen.Instructor.route,
        route = Graph.DETAIL
    ) {
        composable(DetailsScreen.Instructor.route, arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            val viewModel = hiltViewModel<TeacherDetailViewModel>()
            InstructorScreen(viewModel) {
                when (it) {
                    is UiAction.OnBackClick -> navController.popBackStack()
                    is UiAction.OnCourseClick -> {
                        navController.navigate("course/${it.id}")
                    }

                    else -> {

                    }
                }
            }
        }
        composable(DetailsScreen.Academy.route, arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            val viewModel = hiltViewModel<AcademyDetailViewModel>()
            AcademyScreen(viewModel) {
                when (it) {
                    is UiAction.OnBackClick -> navController.popBackStack()
                    is UiAction.OnCourseClick -> {
                        navController.navigate("course/${it.id}")
                    }
                    is UiAction.OnInstructorClick -> {
                        navController.navigate("instructor/${it.id}")
                    }
                    else -> {

                    }
                }
            }
        }
        composable(DetailsScreen.Course.route, arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            val viewModel = hiltViewModel<CourseDetailViewModel>()
            CourseScreen(viewModel) {
                navController.popBackStack()
            }
        }
    }
}

typealias OnAction = (UiAction) -> Unit