package com.baltroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HitReadsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val textStyles = if (darkTheme) {
        DarkThemeTextStyles
    } else {
        LightThemeTextStyles
    }

    // todo needs to be dynamic
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Transparent
    )

    val dimensions = Dimensions()
    val staticColors = Colors()
    val shapes = Shapes()

    CompositionLocalProvider(
        LocalTextStyles provides textStyles,
        LocalDimensions provides dimensions,
        LocalColors provides staticColors,
        LocalShapes provides shapes,
        content = content
    )
}