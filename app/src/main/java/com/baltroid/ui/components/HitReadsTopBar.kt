package com.baltroid.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.apps.R
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun HitReadsTopBar(
    @DrawableRes iconResId: Int,
    numberOfNotification: Int,
    modifier: Modifier = Modifier,
    isUserLoggedIn: Boolean = false,
    iconTint: Color = Color.Unspecified,
    gemCount: Int = 0,
    isGemEnabled: Boolean = false,
    onIconClick: () -> Unit = {},
    onMenuClick: () -> Unit,
    onNotificationClick: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                start = dimensionResource(id = R.dimen.dp36),
                end = dimensionResource(id = R.dimen.dp32),
                top = dimensionResource(id = R.dimen.dp12)
            )
    ) {
        SimpleImage(
            imgResId = R.drawable.ic_hitreads,
            modifier = Modifier.clickable { onIconClick.invoke() }
        )
        MenuAndNotification(
            modifier = Modifier.align(Alignment.CenterEnd),
            iconTint = iconTint,
            iconResId = iconResId,
            onMenuClick = onMenuClick,
            isUserLoggedIn = isUserLoggedIn,
            numberOfNotification = 0,
            gemCount = gemCount,
            isGemEnabled = isGemEnabled,
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
    gemCount: Int,
    isGemEnabled: Boolean = false,
    isUserLoggedIn: Boolean = false,
    onMenuClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    val maxNotificationNumber = 99
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        if (isGemEnabled) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp3)),
                modifier = Modifier
                    .border(
                        dimensionResource(id = R.dimen.dp1),
                        MaterialTheme.localColors.white,
                        MaterialTheme.localShapes.roundedDp30
                    )
                    .padding(
                        vertical = 5.dp,
                        horizontal = dimensionResource(id = R.dimen.dp15)
                    )
            ) {
                if (!isUserLoggedIn) {
                    Text(
                        text = stringResource(id = R.string.member_login),
                        style = MaterialTheme.localTextStyles.episodeText,
                        color = MaterialTheme.localColors.white
                    )
                } else {
                    Text(
                        text = gemCount.toString(),
                        style = MaterialTheme.localTextStyles.episodeText,
                        color = MaterialTheme.localColors.white
                    )
                    SimpleIcon(iconResId = R.drawable.ic_diamond)
                }
            }
            HorizontalSpacer(width = dimensionResource(id = R.dimen.dp26))
        }
        MenuButton(onMenuClick)
        HorizontalSpacer(width = dimensionResource(id = R.dimen.dp14))
        IconWithBadge(
            iconResId = iconResId,
            iconTint = iconTint,
            numberOfNotification = if (numberOfNotification <= maxNotificationNumber) numberOfNotification
            else maxNotificationNumber,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.dp11))
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
        color = MaterialTheme.localColors.white,
        modifier = Modifier
            .clip(MaterialTheme.localShapes.roundedDp4)
            .border(
                width = dimensionResource(id = R.dimen.dp1),
                color = Color.White,
                shape = MaterialTheme.localShapes.roundedDp4
            )
            .background(Color.Black)
            .clickable { onClick.invoke() }
            .padding(
                top = 5.dp,
                start = dimensionResource(id = R.dimen.dp13),
                end = dimensionResource(id = R.dimen.dp12),
                bottom = dimensionResource(id = R.dimen.dp4)
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
                    .size(dimensionResource(id = R.dimen.dp22))
                    .clip(CircleShape)
                    .background(Color.Black)
                    .padding(dimensionResource(id = R.dimen.dp2))
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Text(
                    text = numberOfNotification.toString(),
                    style = MaterialTheme.localTextStyles.topBarIconText,
                    color = MaterialTheme.localColors.black,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dp2))
                )
            }
        }
    )
}

private fun iconWithBadgeMeasurePolicy() = MeasurePolicy { measurable, constraints ->
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
        gemCount = 4500,
        isGemEnabled = true,
        isUserLoggedIn = true,
        onIconClick = {},
        onMenuClick = {}
    )
}