package com.baltroid.apps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.baltroid.designsystem.navbar.BottomBarScreen

@Stable
class AppState(
    val navController: NavController
) {
    private val bottomBarTabs = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Search,
        BottomBarScreen.Play,
        BottomBarScreen.Courses,
        BottomBarScreen.Academies,
        BottomBarScreen.Instructors
    )
    private val bottomBarRoutes = bottomBarTabs.map { it.route }

    val shouldShowTopBar: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes
}