package com.baltroid.designsystem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.baltroid.designsystem.dialog.MqDialog
import com.baltroid.designsystem.extension.showLocationOnMap
import com.baltroid.model.Mall
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MallDetailTopAppBar(
    mall: Mall,
    onBack: () -> Unit,
    goToShopSearch: () -> Unit,
    goToMallPlan: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    var showInfo by remember { mutableStateOf(false) }
    val context = LocalContext.current

    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = {
                goToShopSearch()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
            IconButton(onClick = {
                context.showLocationOnMap(mall.location, mall.name)
            }) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.White
                )
            }
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = Color.White
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(text = { Subhead(text = "İletişim Bilgileri") }, onClick = {
                    showInfo = !showInfo
                    showMenu = false
                })
                DropdownMenuItem(text = { Subhead(text = "Kat Planları") }, onClick = {
                    goToMallPlan()
                    showMenu = false
                })
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )

    if (showInfo) {
        MqDialog(mall, onDismiss = { showInfo = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MallDetailMediumTopAppBar(
    toolbarHeight: Dp,
    toolbarOffsetHeightPx: Float,
    mall: Mall,
    onBack: () -> Unit,
    goToShopSearch: () -> Unit,
    goToMallPlan: () -> Unit
) {
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }
    var showInfo by remember { mutableStateOf(false) }

    MediumTopAppBar(
        modifier = Modifier
            .height(toolbarHeight)
            .offset {
                IntOffset(
                    x = 0,
                    y = toolbarOffsetHeightPx.roundToInt()
                )
            }
            .shadow(
                elevation = 4.dp
            ),
        title = { H3Title(mall.name) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = goToShopSearch) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            IconButton(onClick = {
                context.showLocationOnMap(mall.location, mall.name)
            }) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location"
                )
            }
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(text = { Subhead(text = "İletişim Bilgileri") }, onClick = {
                    showInfo = !showInfo
                    showMenu = false
                })
                DropdownMenuItem(text = { Subhead(text = "Kat Planları") }, onClick = {
                    goToMallPlan()
                    showMenu = false
                })
            }
        }
    )

    if (showInfo) {
        MqDialog(mall, onDismiss = { showInfo = false })
    }
}