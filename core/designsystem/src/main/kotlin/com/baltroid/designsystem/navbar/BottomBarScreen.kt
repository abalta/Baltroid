package com.baltroid.designsystem.navbar

import com.baltroid.core.designsystem.R

sealed class BottomBarScreen(
    val route: String,
    val icon: Int
) {
    data object Home : BottomBarScreen(
        route = "home",
        icon = R.drawable.ic_bottom_home
    )
    data object Search : BottomBarScreen(
        route = "search",
        icon = R.drawable.ic_bottom_search
    )
    data object Play : BottomBarScreen(
        route = "play",
        icon = R.drawable.ic_bottom_play
    )
    data object Profile : BottomBarScreen(
        route = "profile",
        icon = R.drawable.ic_bottom_user
    )
    data object Courses : BottomBarScreen(
        route = "courses",
        icon = 0
    )
    data object Academies : BottomBarScreen(
        route = "academies",
        icon = 0
    )
    data object Instructors : BottomBarScreen(
        route = "instructors",
        icon = 0
    )
    data object Menu : BottomBarScreen(
        route = "menu",
        icon = 0
    )
}