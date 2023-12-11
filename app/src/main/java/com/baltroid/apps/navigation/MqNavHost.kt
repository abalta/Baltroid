package com.baltroid.apps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.baltroid.apps.navigation.destinations.HomeDestination
import com.baltroid.apps.navigation.destinations.MallDetailDestination
import com.baltroid.apps.navigation.destinations.homeGraph
import com.baltroid.apps.navigation.destinations.mallDetailsGraph

@Composable
fun MqNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = HomeDestination.route
    ) {
        homeGraph {
            navController.navigate(MallDetailDestination.createNavigationRoute(it))
        }
        mallDetailsGraph(navController)
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}