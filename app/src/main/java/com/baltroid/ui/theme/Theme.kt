package com.baltroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

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