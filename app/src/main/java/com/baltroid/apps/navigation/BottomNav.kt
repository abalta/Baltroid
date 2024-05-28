package com.baltroid.apps.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.baltroid.designsystem.component.shadow
import com.baltroid.designsystem.navbar.BottomBarScreen
import com.baltroid.designsystem.theme.electricVioletColor

@Composable
fun BottomNav() {
    val navController = rememberNavController()
    BottomBar(navController = navController)
    BottomNavGraph(
        navController = navController
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home, BottomBarScreen.Search, BottomBarScreen.Play, BottomBarScreen.Profile
    )

    val bottomBarRoute = listOf(
        BottomBarScreen.Instructors.route,
        BottomBarScreen.Courses.route,
        BottomBarScreen.Academies.route,
        BottomBarScreen.Favorites.route,
        BottomBarScreen.Profile.route,
        BottomBarScreen.Search.route
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    val currentRoute = bottomBarRoute.any {
        it == currentDestination?.route
    }

    if (bottomBarDestination || currentRoute) {
        Row(
            modifier = Modifier
                .padding(start = 13.dp, end = 13.dp, top = 8.dp, bottom = 27.dp)
                .fillMaxWidth()
                .height(44.dp)
                .shadow(
                    color = Color.Black.copy(0.05f),
                    offsetX = 2.dp,
                    offsetY = 2.dp,
                    borderRadius = 9.dp,
                    spread = 4.dp,
                    blurRadius = 6.dp
                )
                .clip(RoundedCornerShape(9.dp))
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen, currentDestination: NavDestination?, navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val contentColor = if (selected) MaterialTheme.colorScheme.electricVioletColor else Color.Black

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }, indication = rememberRipple(
                radius = 40.dp, color = MaterialTheme.colorScheme.electricVioletColor
            ), interactionSource = remember {
                MutableInteractionSource()
            }),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = screen.icon),
            contentDescription = "icon",
            tint = contentColor,
        )
    }
}

@Composable
@Preview
fun BottomNavPreview() {
    BottomNav()
}