package com.baltroid.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MekikTextField(label: String) {
    var text by remember { mutableStateOf("Hello") }

    Column {
        Caption(text = label, modifier = Modifier.padding(horizontal = 42.dp))
        TextField(value = text, onValueChange = {
            text = it
        }, modifier = Modifier.padding(top = 8.dp, start = 13.dp, end = 13.dp).shadow(
            color = Color.Black.copy(0.05f),
            offsetX = 2.dp,
            offsetY = 2.dp,
            borderRadius = 9.dp,
            spread = 4.dp,
            blurRadius = 6.dp
        )
            .clip(RoundedCornerShape(9.dp))
            .background(Color.White), )
    }
}

@Preview
@Composable
fun MekikTextFieldPreview() {
    MekikTextField(label = "Label")
}