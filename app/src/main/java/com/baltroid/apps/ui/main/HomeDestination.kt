package com.baltroid.apps.ui.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object HomeDestination {
    val route = "home_route"
}

fun NavGraphBuilder.homeGraph(
    onNavigateToDetailsDestination: (String) -> Unit
) = composable(
    route = HomeDestination.route
) {
    HomeRoute(onMallClick = { onNavigateToDetailsDestination(it) })
}