package com.baltroid.apps.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.Caption
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.shadow
import com.baltroid.designsystem.theme.mediumBigStyle
import com.baltroid.designsystem.theme.woodsmokeColor

@Composable
fun SearchScreen() {

    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(top = 56.dp, bottom = 64.dp)) {
        FilterHeadText(text = stringResource(id = R.string.title_search), modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp), showIcon = false)
        Caption(
            text = "84 eğitim, 18 eğitmen ve 12 akademi",
            color = MaterialTheme.colorScheme.woodsmokeColor.copy(0.3f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 20.dp))
        TextField(
            value = text,
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_bottom_search), contentDescription = null) },
            onValueChange = {
                text = it
            },
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(60.dp)
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
fun PreviewSearchScreen() {
    SearchScreen()
}