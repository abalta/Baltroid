package com.baltroid.designsystem.navbar

sealed class DetailsScreen(
    val route: String,
    val icon: Int
) {
    data object Instructor : DetailsScreen(
        route = "instructor",
        icon = 0
    )
    data object Academy : DetailsScreen(
        route = "academy",
        icon = 0
    )
    data object Course : DetailsScreen(
        route = "course",
        icon = 0
    )
}