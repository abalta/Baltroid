package com.baltroid.apps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.baltroid.apps.ui.main.HomeDestination
import com.baltroid.apps.ui.main.HomeScreen
import com.baltroid.apps.ui.main.MallDetailDestination
import com.baltroid.apps.ui.main.MallDetailRoute
import com.baltroid.apps.ui.main.MallDetailScreen
import com.baltroid.apps.ui.main.homeGraph
import com.baltroid.apps.ui.main.mallDetailsGraph

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
        mallDetailsGraph {
            navController.popBackStack()
        }
    }
}