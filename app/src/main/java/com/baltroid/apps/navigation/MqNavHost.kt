package com.baltroid.apps.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.baltroid.apps.ui.main.HomeScreen
import com.baltroid.apps.ui.main.MallDetailScreen

@Composable
fun MqNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home") {
        composable("home") {
            HomeScreen(
                onMallClick = navController::navigateToMallDetail
            )
        }
        composable("mall_detail") {
            MallDetailScreen()
        }
        composable("page 3") {  }
        composable("page 4") {  }
    }
}

fun NavController.navigateToMallDetail() {
    navigate("mall_detail")
}