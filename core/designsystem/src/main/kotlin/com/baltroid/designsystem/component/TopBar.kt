package com.baltroid.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.theme.electricVioletColor

@Composable
fun TopBar(navController: NavHostController, onMenuClick: () -> Unit, onNotificationClick: () -> Unit) {
    var route by rememberSaveable { mutableStateOf("") }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        route = destination.route.orEmpty()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "menu"
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .size(56.dp, 33.dp)
                .align(Alignment.CenterVertically),
            tint = MaterialTheme.colorScheme.electricVioletColor
        )
        IconButton(onClick = onNotificationClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bell),
                contentDescription = "notification"
            )
        }
    }
}

@Preview
@Composable
fun PreviewTopBar() {
    TopBar(navController = rememberNavController(), onMenuClick = {}, onNotificationClick = {})
}