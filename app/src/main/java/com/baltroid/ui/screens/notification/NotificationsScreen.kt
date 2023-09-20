package com.baltroid.ui.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.R
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.screens.viewmodels.OriginalViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.orZero
import com.hitreads.core.domain.model.NotificationModel

@Composable
fun NotificationsScreen(
    viewModel: OriginalViewModel,
    onBackPressed: () -> Unit
) {
    val notifications by viewModel.uiStateNotifications.collectAsStateWithLifecycle()

    NotificationsScreenContent(
        notifications = notifications,
        onBackPressed = onBackPressed
    )
}

@Composable
fun NotificationsScreenContent(
    notifications: List<NotificationModel>,
    onBackPressed: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .systemBarsPadding()
    ) {
        VerticalSpacer(height = R.dimen.dp36)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SimpleIcon(
                iconResId = R.drawable.ic_bell_outlined,
                Modifier.size(dimensionResource(id = R.dimen.dp26))
            )
            Text(
                text = stringResource(id = R.string.notifications),
                style = MaterialTheme.localTextStyles.spaceGrotesk24Medium,
                color = MaterialTheme.localColors.white_alpha09,
            )
            SimpleIcon(
                iconResId = R.drawable.ic_close,
                Modifier.clickable {
                    onBackPressed.invoke()
                },
            )
        }
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
        Divider(
            thickness = dimensionResource(id = R.dimen.dp0_5),
            color = MaterialTheme.localColors.white_alpha06
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.dp24))
        ) {
            items(
                notifications,
                key = { it.id.orZero() }
            ) { item ->
                NotificationItem(item)
            }
        }
    }
}

@Composable
private fun NotificationItem(
    notification: NotificationModel
) {
    Column {
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp12))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SimpleIcon(
                iconResId = when (notification.type) {
                    "favorite" -> R.drawable.ic_star
                    "comment" -> R.drawable.ic_chat_filled
                    "like" -> R.drawable.ic_heart_filled
                    else -> R.drawable.ic_star
                },
                modifier = Modifier.weight(1f)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = notification.message.orEmpty(),
                    maxLines = 1,
                    modifier = Modifier,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.localColors.white,
                    style = MaterialTheme.localTextStyles.poppins12Regular
                )
                Text(
                    text = notification.detail.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.localColors.white,
                    style = MaterialTheme.localTextStyles.poppins12Bold
                )
            }
            SimpleIcon(
                iconResId = R.drawable.ic_arrow_forward,
                Modifier
                    .size(dimensionResource(id = R.dimen.dp13))
                    .weight(1f)
            )
        }
        VerticalSpacer(height = R.dimen.dp10)
        Divider(
            thickness = dimensionResource(id = R.dimen.dp0_5),
            color = MaterialTheme.localColors.white_alpha06
        )
    }
}