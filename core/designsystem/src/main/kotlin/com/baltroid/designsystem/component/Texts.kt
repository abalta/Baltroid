package com.baltroid.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.theme.badgeStyle
import com.baltroid.designsystem.theme.bodyStyle
import com.baltroid.designsystem.theme.buttonTextStyle
import com.baltroid.designsystem.theme.captionMediumStyle
import com.baltroid.designsystem.theme.captionSmallStyle
import com.baltroid.designsystem.theme.captionStyle
import com.baltroid.designsystem.theme.electricVioletColor
import com.baltroid.designsystem.theme.headerBoldStyle
import com.baltroid.designsystem.theme.headerStyle
import com.baltroid.designsystem.theme.mediumBigStyle
import com.baltroid.designsystem.theme.mediumBoldStyle
import com.baltroid.designsystem.theme.mediumSmallStyle
import com.baltroid.designsystem.theme.mediumStyle
import com.baltroid.designsystem.theme.mediumTitleStyle
import com.baltroid.designsystem.theme.menuStyle
import com.baltroid.designsystem.theme.ratingBoldStyle
import com.baltroid.designsystem.theme.rowTitleStyle
import com.baltroid.designsystem.theme.sharkColor
import com.baltroid.designsystem.theme.smallBoldStyle
import com.baltroid.designsystem.theme.smallTextStyle
import com.baltroid.designsystem.theme.subTitleTextStyle

@Composable
fun Caption(modifier: Modifier = Modifier, text: String, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.captionStyle
    )
}
@Composable
fun CaptionMedium(modifier: Modifier = Modifier, text: String, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.captionMediumStyle
    )
}
@Composable
fun CaptionSmall(modifier: Modifier = Modifier, text: String, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.captionSmallStyle
    )
}
@Composable
fun SmallBold(modifier: Modifier = Modifier, text: String, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.smallBoldStyle
    )
}
@Composable
fun MediumBold(modifier: Modifier = Modifier, text: String, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.mediumBoldStyle
    )
}
@Composable
fun Body(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyStyle
    )
}
@Composable
fun ButtonText(text: String, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.electricVioletColor, textAlign: TextAlign? = null) {
    Text(
        text = text,
        color = color,
        modifier = modifier,
        textAlign = textAlign,
        style = MaterialTheme.typography.buttonTextStyle
    )
}
@Composable
fun SubtitleText(text: String, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.sharkColor) {
    Text(
        text = text,
        color = color,
        modifier = modifier,
        style = MaterialTheme.typography.subTitleTextStyle
    )
}
@Composable
fun RowtitleText(text: String, modifier: Modifier = Modifier, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.rowTitleStyle
    )
}
@Composable
fun HeadText(text: String, modifier: Modifier = Modifier, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.headerStyle
    )
}

@Composable
fun RatingText(text: String, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.electricVioletColor) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.ratingBoldStyle
    )
}

@Composable
fun BoldHeadText(text: String, modifier: Modifier = Modifier, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.headerBoldStyle
    )
}
@Composable
fun MediumTitleText(text: String, modifier: Modifier = Modifier, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.mediumTitleStyle
    )
}
@Composable
fun SmallText(text: String, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.sharkColor, maxLines: Int = Int.MAX_VALUE) {
    Text(
        text = text,
        color = color,
        modifier = modifier,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.smallTextStyle
    )
}
@Composable
fun FilterHeadText(text: String, modifier: Modifier = Modifier, showIcon: Boolean = true, color: Color = Color.Black, onFilterClick: () -> Unit = {}) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        HeadText(
            text = text,
            color = color
        )
        if (showIcon) {
            IconButton(onClick = onFilterClick) {
                Icon(painter = painterResource(id = R.drawable.ic_filter), contentDescription = "filter")
            }
        }

    }
}
@Preview
@Composable
fun PreviewFilterHeadText() {
    FilterHeadText(text = "Eğitimler", onFilterClick = {

    })
}

@Composable
fun MenuText(text: String, modifier: Modifier = Modifier, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.menuStyle
    )
}
@Composable
fun MediumText(text: String, modifier: Modifier = Modifier, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.mediumStyle
    )
}
@Composable
fun MediumSmallText(text: String, modifier: Modifier = Modifier, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.mediumSmallStyle
    )
}
@Composable
fun MediumBigText(text: String, modifier: Modifier = Modifier, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.mediumBigStyle
    )
}
@Composable
fun BadgeText(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.electricVioletColor)
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.badgeStyle,
        )
    }
}
@Preview
@Composable
fun PreviewBadgeText() {
    BadgeText("Yeni")
}