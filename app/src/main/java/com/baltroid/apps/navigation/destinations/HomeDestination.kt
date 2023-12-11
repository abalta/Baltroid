package com.baltroid.apps.navigation.destinations

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.baltroid.apps.ui.main.HomeRoute

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