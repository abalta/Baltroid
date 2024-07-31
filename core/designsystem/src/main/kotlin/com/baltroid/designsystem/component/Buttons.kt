package com.baltroid.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.theme.electricVioletColor

@Composable
fun MekikOutlinedButton(text: String, modifier: Modifier = Modifier, borderColor: Color = Color.Black.copy(0.2f), isEnable: Boolean = true, onClick: () -> Unit) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 13.dp)
            .height(45.dp),
        shape = RoundedCornerShape(9.dp),
        border = BorderStroke(1.5.dp, borderColor),
        enabled = isEnable,
        onClick = { onClick() }) {
        ButtonText(text)
    }
}

@Composable
fun MekikFilledButton(text: String, modifier: Modifier = Modifier, isEnable: Boolean = true, onClick: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp),
        colors = ButtonDefaults.buttonColors()
            .copy(containerColor = MaterialTheme.colorScheme.electricVioletColor),
        shape = RoundedCornerShape(9.dp),
        enabled = isEnable,
        onClick = { onClick() }) {
        ButtonText(text, color = Color.White)
    }
}

@Composable
fun MenuButton(text: String, icon: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .height(54.dp)
            .shadow(
                color = Color.Black.copy(0.05f),
                offsetX = 2.dp,
                offsetY = 2.dp,
                borderRadius = 9.dp,
                spread = 4.dp,
                blurRadius = 6.dp
            )
            .clip(RoundedCornerShape(9.dp))
            .background(Color.White)
            .clickable(onClick = onClick, indication = rememberRipple(
                color = MaterialTheme.colorScheme.electricVioletColor
            ), interactionSource = remember {
                MutableInteractionSource()
            }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "menu_button",
            modifier = Modifier.padding(start = 24.dp)
        )
        MenuText(
            text = text, modifier = Modifier
                .padding(horizontal = 20.dp)
                .weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_menu_arrow),
            contentDescription = "menu_arrow",
            modifier = Modifier.padding(end = 32.dp)
        )
    }
}

@Composable
fun CircularButton(
    icon: Int,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.electricVioletColor,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(28.dp)
            .shadow(
                color = Color.Black.copy(0.25f),
                offsetX = 0.dp,
                offsetY = 1.dp,
                borderRadius = 14.dp,
                spread = 0.dp,
                blurRadius = 1.dp
            )
            .clip(CircleShape)
            .background(Color.White)
            .clickable(onClick = onClick, indication = rememberRipple(
                color = MaterialTheme.colorScheme.electricVioletColor
            ), interactionSource = remember {
                MutableInteractionSource()
            }),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = icon),
            contentDescription = "circular_button",
            tint = tint
        )
    }
}

@Preview
@Composable
fun PreviewCircularButton() {
    CircularButton(R.drawable.ic_fav) {

    }
}

@Preview
@Composable
fun PreviewMenuButton() {
    MenuButton(text = "Eğitimler", icon = R.drawable.ic_bottom_play) {

    }
}

@Preview
@Composable
fun PreviewOutlinedButton() {
    MekikOutlinedButton("Tüm Eğitimler") {

    }
}

@Preview
@Composable
fun PreviewFilledButton() {
    MekikFilledButton("Tüm Eğitimler") {

    }
}