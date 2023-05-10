package com.baltroid.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

data class Shapes(
    val circleShape: RoundedCornerShape = CircleShape,
    val roundedDp2: RoundedCornerShape = RoundedCornerShape(2.dp),
    val roundedDp3: RoundedCornerShape = RoundedCornerShape(3.dp),
    val roundedDp4: RoundedCornerShape = RoundedCornerShape(4.dp),
    val roundedDp8_5: RoundedCornerShape = RoundedCornerShape(8.5.dp),
    val roundedDp10: RoundedCornerShape = RoundedCornerShape(10.dp),
    val roundedDp11_5: RoundedCornerShape = RoundedCornerShape(11.5.dp),
    val roundedDp16: RoundedCornerShape = RoundedCornerShape(16.dp),
    val roundedDp18: RoundedCornerShape = RoundedCornerShape(18.dp),
    val roundedDp20: RoundedCornerShape = RoundedCornerShape(20.dp),
    val roundedDp24: RoundedCornerShape = RoundedCornerShape(24.dp)
)

val LocalShapes = staticCompositionLocalOf { Shapes() }

val MaterialTheme.localShapes
    @Composable
    @ReadOnlyComposable
    get() = LocalShapes.current