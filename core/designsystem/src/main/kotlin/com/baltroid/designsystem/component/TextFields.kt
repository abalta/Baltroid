package com.baltroid.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.baltroid.designsystem.theme.mediumBigStyle
import com.baltroid.designsystem.theme.woodsmokeColor

@Composable
fun MekikTextField(label: String) {
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(top = 16.dp)) {
        Caption(text = label, modifier = Modifier.padding(horizontal = 42.dp), color = MaterialTheme.colorScheme.woodsmokeColor.copy(alpha = 0.7f))
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 13.dp, end = 13.dp)
                .shadow(
                    color = Color.Black.copy(0.05f),
                    offsetX = 2.dp,
                    offsetY = 2.dp,
                    borderRadius = 9.dp,
                    spread = 4.dp,
                    blurRadius = 6.dp
                )
                .clip(RoundedCornerShape(9.dp))
                .background(Color.White),
            textStyle = MaterialTheme.typography.mediumBigStyle,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedPlaceholderColor = Color.Transparent,
                focusedPlaceholderColor = Color.Transparent
            ),
        )
    }
}

@Preview
@Composable
fun MekikTextFieldPreview() {
    MekikTextField(label = "Label")
}