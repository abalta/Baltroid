package com.baltroid.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.designsystem.theme.bodyStyle
import com.baltroid.designsystem.theme.captionStyle
import com.baltroid.designsystem.theme.hollyColor
import com.baltroid.designsystem.theme.h1TitleStyle
import com.baltroid.designsystem.theme.h2TitleStyle
import com.baltroid.designsystem.theme.h3TitleStyle
import com.baltroid.designsystem.theme.headlineStyle
import com.baltroid.designsystem.theme.subheadStyle

@Composable
fun H3Title(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.hollyColor,
        modifier = modifier,
        style = MaterialTheme.typography.h3TitleStyle
    )
}

@Preview
@Composable
fun previewH3Title() = H3Title(text = "H3 - Title")

@Composable
fun H2Title(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.hollyColor,
        modifier = modifier,
        style = MaterialTheme.typography.h2TitleStyle
    )
}

@Preview
@Composable
fun previewH2Title() = H2Title(text = "H2 - Title")

@Composable
fun H1Title(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.hollyColor,
        modifier = modifier,
        style = MaterialTheme.typography.h1TitleStyle
    )
}

@Preview
@Composable
fun previewH1Title() = H1Title(text = "H1 - Title")

@Composable
fun Headline(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.hollyColor,
        modifier = modifier,
        style = MaterialTheme.typography.headlineStyle
    )
}

@Preview
@Composable
fun previewHeadline() = Headline(text = "Headline")

@Composable
fun Body(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.hollyColor,
        modifier = modifier,
        style = MaterialTheme.typography.bodyStyle
    )
}

@Preview
@Composable
fun previewBody() = Body(text = "Body")

@Composable
fun Subhead(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.hollyColor,
        modifier = modifier,
        style = MaterialTheme.typography.subheadStyle
    )
}

@Preview
@Composable
fun previewSubhead() = Subhead(text = "Subhead")

@Composable
fun Caption(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.hollyColor,
        modifier = modifier,
        style = MaterialTheme.typography.captionStyle
    )
}

@Preview
@Composable
fun previewCaption() = Caption(text = "Caption")