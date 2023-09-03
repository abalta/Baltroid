package com.baltroid.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun MQIcon(
    resourceId: Int,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(painter = painterResource(id = resourceId), contentDescription = "MQIcon")
}