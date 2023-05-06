package com.baltroid.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun HitReadsTopBar(
    @DrawableRes iconResId: Int,
    numberOfNotification: Int,
    modifier: Modifier = Modifier,
    iconTint: Color = Color.Unspecified,
    onIconClick: () -> Unit = {},
    onMenuClick: () -> Unit,
    onNotificationClick: () -> Unit
) {


    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                start = MaterialTheme.localDimens.dp36,
                end = MaterialTheme.localDimens.dp32,
                top = MaterialTheme.localDimens.dp12
            )
    ) {
        SimpleImage(
            imgResId = R.drawable.ic_hitreads,
            modifier = Modifier.clickable(
            ) { onIconClick.invoke() })
        MenuAndNotification(
            modifier = Modifier.align(Alignment.CenterEnd),
            iconTint = iconTint,
            iconResId = iconResId,
            onMenuClick = onMenuClick,
            numberOfNotification = numberOfNotification,
            onNotificationClick = onNotificationClick
        )
    }
}

@Composable
fun MenuAndNotification(
    @DrawableRes iconResId: Int,
    numberOfNotification: Int,
    modifier: Modifier = Modifier,
    iconTint: Color = Color.Unspecified,
    onMenuClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    val maxNotificationNumber = 99
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        MenuButton(onMenuClick)
        HorizontalSpacer(width = MaterialTheme.localDimens.dp13_5)
        IconWithBadge(
            iconResId = iconResId,
            iconTint = iconTint,
            numberOfNotification = if (numberOfNotification <= maxNotificationNumber) numberOfNotification
            else maxNotificationNumber,
            modifier = Modifier
                .padding(bottom = MaterialTheme.localDimens.dp11)
                .clickable { onNotificationClick.invoke() }
        )
    }
}

@Composable
fun MenuButton(
    onClick: () -> Unit
) {
    Text(
        text = stringResource(id = R.string.menu),
        style = MaterialTheme.localTextStyles.menuButtonText,
        modifier = Modifier
            .clip(MaterialTheme.localShapes.roundedDp4)
            .border(
                width = MaterialTheme.localDimens.dp1,
                color = Color.White,
                shape = MaterialTheme.localShapes.roundedDp4
            )
            .background(Color.Black)
            .clickable { onClick.invoke() }
            .padding(
                top = MaterialTheme.localDimens.dp4_5,
                start = MaterialTheme.localDimens.dp12_5,
                end = MaterialTheme.localDimens.dp11_5,
                bottom = MaterialTheme.localDimens.dp3_5
            )
    )
}

@Composable
fun IconWithBadge(
    @DrawableRes iconResId: Int,
    numberOfNotification: Int,
    modifier: Modifier = Modifier,
    iconTint: Color = Color.Unspecified
) {
    val measurePolicy = iconWithBadgeMeasurePolicy()

    Layout(
        measurePolicy = measurePolicy,
        modifier = modifier,
        content = {
            SimpleIcon(
                iconResId = iconResId,
                tint = iconTint,
                modifier = Modifier.layoutId("icon")
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .layoutId("badge")
                    .size(MaterialTheme.localDimens.dp22)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .padding(MaterialTheme.localDimens.dp1_5)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Text(
                    text = numberOfNotification.toString(),
                    style = MaterialTheme.localTextStyles.topBarIconText,
                    modifier = Modifier.padding(top = MaterialTheme.localDimens.dp1_5)
                )
            }
        }
    )
}

fun iconWithBadgeMeasurePolicy() = MeasurePolicy { measurable, constraints ->
    val iconPlaceable = measurable.first {
        it.layoutId == "icon"
    }.measure(
        constraints.copy(
            minWidth = 0,
            minHeight = 0
        )
    )

    val badgePlaceable = measurable.first {
        it.layoutId == "badge"
    }.measure(
        constraints.copy(
            minWidth = 0,
            minHeight = 0
        )
    )

    val horizontalOffset = 10
    val verticalOffset = 15

    layout(
        badgePlaceable.width + horizontalOffset,
        badgePlaceable.height + iconPlaceable.height - verticalOffset
    ) {
        iconPlaceable.placeRelative(
            0,
            badgePlaceable.height - verticalOffset
        )
        badgePlaceable.placeRelative(
            x = horizontalOffset,
            y = 0
        )
    }
}

@Preview
@Composable
fun HitReadsTopBarPreview() {
    HitReadsTopBar(
        onNotificationClick = {},
        iconResId = R.drawable.ic_bell,
        numberOfNotification = 12,
        onIconClick = {},
        onMenuClick = {}
    )
}