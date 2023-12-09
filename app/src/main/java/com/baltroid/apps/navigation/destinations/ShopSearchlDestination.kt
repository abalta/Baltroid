package com.baltroid.apps.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.baltroid.apps.ui.shopsearch.ShopSearchRoute

object ShopSearchDestination {
    const val route = "shop_search_route"
    fun createNavigationRoute() = route

}

fun NavGraphBuilder.shopSearchGraph(
    onNavigateUp: () -> Unit
) = composable(
    route = ShopSearchDestination.route,
) {
    ShopSearchRoute(onBack = onNavigateUp)
}
