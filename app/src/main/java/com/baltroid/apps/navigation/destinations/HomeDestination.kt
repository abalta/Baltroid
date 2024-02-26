package com.baltroid.apps.navigation.destinations

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.baltroid.apps.navigation.destinations.HomeDestination.graphRoute
import com.baltroid.apps.navigation.destinations.HomeDestination.routeWithArguments
import com.baltroid.apps.navigation.destinations.HomeDestination.startDestination
import com.baltroid.apps.navigation.sharedViewModel
import com.baltroid.apps.ui.main.HomeRoute
import com.baltroid.apps.ui.main.MainViewModel
import com.baltroid.apps.ui.malls.MallListRoute
import com.baltroid.apps.ui.malls.MallListViewModel

object HomeDestination {
    const val graphRoute = "home"
    const val startDestination = "home_route"
    private const val mallListRoute = "mall_list"

    const val idArgument = "id"
    const val routeWithArguments = "$mallListRoute/{$idArgument}"

    fun createNavigationRoute(id: Int) = "$mallListRoute/$id"

    fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): Int {
        return checkNotNull(savedStateHandle[idArgument]) {
            CityIdNullMessage
        }
    }

}

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(
        startDestination = startDestination,
        route = graphRoute
    ) {
        composable(
            route = startDestination
        ) { entry ->
            val viewModel = entry.sharedViewModel<MainViewModel>(navController)
            HomeRoute(viewModel, onMallClick = {
                navController.navigate(MallDetailDestination.createNavigationRoute(it))
            }, onMallList = {
                navController.navigate(HomeDestination.createNavigationRoute(it))
            })
        }
        composable(
            route = routeWithArguments,
            arguments = listOf(
                navArgument(HomeDestination.idArgument) { type = NavType.IntType }
            )
        ) {
            val viewModel = hiltViewModel<MallListViewModel>()
            MallListRoute(viewModel) {
                navController.popBackStack()
            }
        }
    }
}

private const val CityIdNullMessage = "City id is null."