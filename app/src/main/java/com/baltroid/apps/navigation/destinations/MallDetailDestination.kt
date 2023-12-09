package com.baltroid.apps.navigation.destinations

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.baltroid.apps.ui.main.MallDetailRoute

object MallDetailDestination {
    private val route = "mall_detail_route"

    const val idArgument = "id"
    val routeWithArguments = "$route/{$idArgument}"

    fun createNavigationRoute(id: String) = "$route/$id"

    fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): String {
        return checkNotNull(savedStateHandle[idArgument]) {
            MallIdNullMessage
        }
    }
}

fun NavGraphBuilder.mallDetailsGraph(
    onNavigateUp: () -> Unit,
    goToShopSearch: () -> Unit
) = composable(
    route = MallDetailDestination.routeWithArguments,
    arguments = listOf(
        navArgument(MallDetailDestination.idArgument) { type = NavType.StringType }
    )
) {
    MallDetailRoute(onBack = onNavigateUp, goToShopSearch = goToShopSearch)
}

private const val MallIdNullMessage = "Mall id is null."