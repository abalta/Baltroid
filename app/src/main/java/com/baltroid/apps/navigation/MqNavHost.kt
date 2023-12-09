package com.baltroid.apps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.baltroid.apps.navigation.destinations.HomeDestination
import com.baltroid.apps.navigation.destinations.MallDetailDestination
import com.baltroid.apps.navigation.destinations.ShopSearchDestination
import com.baltroid.apps.navigation.destinations.homeGraph
import com.baltroid.apps.navigation.destinations.mallDetailsGraph
import com.baltroid.apps.navigation.destinations.shopSearchGraph

@Composable
fun MqNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route) {
        homeGraph {
           navController.navigate(MallDetailDestination.createNavigationRoute(it))
        }
        mallDetailsGraph({
            navController.popBackStack()
        }, {
            navController.navigate(ShopSearchDestination.createNavigationRoute())
        })
        shopSearchGraph {
            navController.popBackStack()
        }
    }
}