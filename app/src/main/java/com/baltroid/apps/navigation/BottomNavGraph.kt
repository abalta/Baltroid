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
import com.baltroid.apps.course.PlayerScreen
import com.baltroid.apps.course.PlayerViewModel
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
                it.navigate(navController)
            }
        }
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen {
                it.navigate(navController)
            }
        }
        composable(route = BottomBarScreen.Play.route) {
            MyCoursesScreen {
                it.navigate(navController)
            }
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomBarScreen.Courses.route) {
            CoursesScreen {
                it.navigate(navController)
            }
        }
        composable(route = BottomBarScreen.Academies.route) {
            AcademiesScreen {
                it.navigate(navController)
            }
        }
        composable(route = BottomBarScreen.Instructors.route) {
            InstructorsScreen {
                it.navigate(navController)
            }
        }
        composable(route = BottomBarScreen.Favorites.route) {
            FavoritesScreen {
                it.navigate(navController)
            }
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
                it.navigate(navController)
            }
        }
        composable(DetailsScreen.Academy.route, arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            val viewModel = hiltViewModel<AcademyDetailViewModel>()
            AcademyScreen(viewModel) {
                it.navigate(navController)
            }
        }
        composable(DetailsScreen.Course.route, arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            val viewModel = hiltViewModel<CourseDetailViewModel>()
            CourseScreen(viewModel) {
                it.navigate(navController)
            }
        }
        composable(DetailsScreen.Player.route, arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )) {
            val viewModel = hiltViewModel<PlayerViewModel>()
            PlayerScreen(viewModel)
        }
    }
}

fun UiAction.navigate(navController: NavHostController) {
    when (this) {
        is UiAction.OnBackClick -> navController.popBackStack()
        is UiAction.OnCourseClick -> {
            navController.navigate("course/${id}")
        }
        is UiAction.OnInstructorClick -> {
            navController.navigate("instructor/${id}")
        }
        is UiAction.OnPlayerClick -> {
            navController.navigate("player/${id}")
        }
        is UiAction.OnAcademyClick -> {
            navController.navigate("academy/${id}")
        }
        is UiAction.OnAllCoursesClick -> {
            navController.navigate(BottomBarScreen.Courses.route)
        }
        else -> {

        }
    }
}

typealias OnAction = (UiAction) -> Unit