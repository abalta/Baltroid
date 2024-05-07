package com.baltroid.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.error
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.designsystem.theme.electricVioletColor
import com.baltroid.designsystem.theme.mediumBigStyle
import com.baltroid.designsystem.theme.woodsmokeColor

@Composable
fun MekikTextField(
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    error: Boolean = false,
    errorMessage: String = "",
    onValue: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(top = 16.dp)) {
        Caption(
            text = label,
            modifier = Modifier.padding(horizontal = 42.dp),
            color = MaterialTheme.colorScheme.woodsmokeColor.copy(alpha = 0.7f)
        )
        TextField(
            value = text,
            leadingIcon = leadingIcon,
            onValueChange = {
                text = it
                onValue(it)
            },
            isError = error,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 13.dp, end = 13.dp, bottom = 4.dp)
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
                focusedPlaceholderColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
        )
        if (error) {
            Caption(
                text = errorMessage,
                modifier = Modifier.padding(horizontal = 42.dp),
                color = MaterialTheme.colorScheme.electricVioletColor
            )
        }
    }
}

@Preview
@Composable
fun MekikTextFieldPreview() {
    MekikTextField(label = "Label") {

    }
}