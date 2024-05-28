package com.baltroid.designsystem.navbar

sealed class DetailsScreen(
    val route: String,
    val icon: Int
) {
    data object Instructor : DetailsScreen(
        route = "instructor/{id}",
        icon = 0
    )
    data object Academy : DetailsScreen(
        route = "academy/{id}",
        icon = 0
    )
    data object Course : DetailsScreen(
        route = "course/{id}",
        icon = 0
    )
}