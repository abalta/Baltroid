package com.baltroid.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconWithTextBelow(
    @DrawableRes iconResId: Int,
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    spacedBy: Dp = 0.dp,
    tint: Color = Color.Unspecified,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        SimpleIcon(iconResId = iconResId, tint = tint)
        VerticalSpacer(height = spacedBy)
        Text(text = text, style = textStyle)
    }
}
