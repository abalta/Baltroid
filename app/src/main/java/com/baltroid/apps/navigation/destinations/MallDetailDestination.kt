package com.baltroid.apps.navigation.destinations

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.baltroid.apps.navigation.destinations.MallDetailDestination.startDestination
import com.baltroid.apps.navigation.sharedViewModel
import com.baltroid.apps.ui.main.MallDetailRoute
import com.baltroid.apps.ui.main.MallDetailViewModel
import com.baltroid.apps.ui.mallplan.MallPlanRoute
import com.baltroid.apps.ui.shopsearch.ShopSearchRoute

object MallDetailDestination {
    const val route = "mall_detail_route"
    const val shop_search_route = "shop_search"
    const val mall_plan_route = "mall_plan"
    const val startDestination = "mall_detail"

    const val idArgument = "id"
    val routeWithArguments = "$route/{$idArgument}"

    fun createNavigationRoute(id: String) = "$route/$id"

    fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): String {
        return checkNotNull(savedStateHandle[idArgument]) {
            MallIdNullMessage
        }
    }
}


@SuppressLint("QueryPermissionsNeeded")
fun NavGraphBuilder.mallDetailsGraph(navController: NavController) {
    navigation(
        startDestination = startDestination,
        route = MallDetailDestination.routeWithArguments
    ) {
        composable(
            route = startDestination,
            arguments = listOf(
                navArgument(MallDetailDestination.idArgument) { type = NavType.StringType }
            )
        ) { entry ->
            val viewModel = entry.sharedViewModel<MallDetailViewModel>(navController)
            MallDetailRoute(
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                }, goToShopSearch = {
                    navController.navigate(MallDetailDestination.shop_search_route)
                }, goToMallPlan = {
                    navController.navigate(MallDetailDestination.mall_plan_route)
                }
            )
        }
        composable(
            route = MallDetailDestination.shop_search_route,
        ) { entry ->
            val viewModel = entry.sharedViewModel<MallDetailViewModel>(navController)
            ShopSearchRoute(viewModel, onBack = { navController.popBackStack() })
        }
        composable(
            route = MallDetailDestination.mall_plan_route
        ) { entry ->
            val viewModel = entry.sharedViewModel<MallDetailViewModel>(navController)
            MallPlanRoute(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
    }
}

private const val MallIdNullMessage = "Mall id is null."