package com.baltroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HitReadsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val textStyles = HitReadsTextStyles

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Transparent
    )

    val staticColors = Colors()
    val shapes = Shapes()

    val loadingState = remember {
        mutableStateOf(false)
    }

    CompositionLocalProvider(
        LocalTextStyles provides textStyles,
        LocalColors provides staticColors,
        LocalShapes provides shapes,
        LocalLoadingState provides loadingState,
        content = content
    )
}

val LocalLoadingState = compositionLocalOf { mutableStateOf(false) }