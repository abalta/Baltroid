package com.baltroid.apps.ui.shopsearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.designsystem.component.H3Title
import com.baltroid.designsystem.theme.h3TitleStyle
import com.baltroid.designsystem.theme.hollyColor
import com.baltroid.designsystem.theme.hollyColor54

@Composable
internal fun ShopSearchRoute(
    onBack: () -> Unit
) {
    ShopSearchScreen(onBack)
}

@Composable
internal fun ShopSearchScreen(onBack: () -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.hollyColor
                )
            }
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                singleLine = true,
                trailingIcon = {
                    when {
                        text.text.isNotEmpty() -> IconButton(onClick = { text = TextFieldValue("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }

                },
                onValueChange = {
                    text = it
                },
                placeholder = {
                    H3Title(
                        text = "Mağaza, Restoran Ara",
                        color = MaterialTheme.colorScheme.hollyColor54
                    )
                },
                textStyle = MaterialTheme.typography.h3TitleStyle,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.hollyColor54,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.hollyColor54
                )
            )
        }
    }
}

@Composable
@Preview
fun PreviewShopSearchScreen() {
    ShopSearchScreen {

    }
}
