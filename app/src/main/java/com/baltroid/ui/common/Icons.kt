package com.baltroid.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

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

@Composable
fun IconWithTextNextTo(
    @DrawableRes iconResId: Int,
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    spacedBy: Dp = 0.dp,
    tint: Color = Color.Unspecified,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        SimpleIcon(iconResId = iconResId, tint = tint)
        HorizontalSpacer(width = spacedBy)
        Text(text = text, style = textStyle)
    }
}

@Composable
fun RoundedIconCard(
    text: String,
    @DrawableRes iconResId: Int,
    modifier: Modifier = Modifier
) {
    IconWithTextBelow(
        iconResId = iconResId,
        text = text,
        spacedBy = MaterialTheme.localDimens.dp3,
        textStyle = MaterialTheme.localTextStyles.imageCardText,
        modifier = modifier
            .size(
                width = MaterialTheme.localDimens.dp76,
                height = MaterialTheme.localDimens.dp70
            )
            .border(
                width = MaterialTheme.localDimens.dp1,
                color = MaterialTheme.localColors.white,
                shape = MaterialTheme.localShapes.roundedDp16
            )
    )
}

@Composable
fun RoundedIconCard(
    @DrawableRes iconResId: Int,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(
                width = MaterialTheme.localDimens.dp76,
                height = MaterialTheme.localDimens.dp70
            )
            .border(
                width = MaterialTheme.localDimens.dp1,
                color = MaterialTheme.localColors.white,
                shape = MaterialTheme.localShapes.roundedDp16
            )
    ) {
        SimpleIcon(iconResId = iconResId)
    }
}