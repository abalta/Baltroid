package com.baltroid.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localTextStyles
import com.hitreads.core.domain.model.WelcomeModel

@Composable
fun OnboardingScreen(
    screenState: WelcomeModel?,
    onClick: () -> Unit
) {
    OnboardingScreenContent(screenState = screenState, onClick)
}

@Composable
fun OnboardingScreenContent(
    screenState: WelcomeModel?,
    onClick: () -> Unit
) {
    Box {
        AsyncImage(
            model = screenState?.path,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        SimpleImage(
            imgResId = R.drawable.ic_hitreads,
            modifier = Modifier
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(
                            x = (constraints.maxWidth / 2) - (placeable.width / 2),
                            y = (constraints.maxHeight / 5) - (placeable.height / 2)
                        )
                    }
                }
                .size(
                    MaterialTheme.localDimens.dp107,
                    MaterialTheme.localDimens.dp102
                )
        )
        OnboardingScreenBottomSection(
            text = screenState?.message ?: stringResource(id = R.string.welcome),
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = onClick
        )
    }
}

@Composable
fun OnboardingScreenBottomSection(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.localColors.black_alpha02)
    ) {
        Divider(
            color = MaterialTheme.localColors.white,
            thickness = MaterialTheme.localDimens.dp0_5,
        )
        Text(
            text = text,
            style = MaterialTheme.localTextStyles.splashScreenText,
            modifier = Modifier
                .padding(
                    top = MaterialTheme.localDimens.dp24,
                    bottom = MaterialTheme.localDimens.dp22
                )
                .align(Alignment.CenterHorizontally)
        )
        Divider(
            color = MaterialTheme.localColors.white,
            thickness = MaterialTheme.localDimens.dp0_5,
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp28)
        SimpleImage(
            imgResId = R.drawable.ic_arrow_right,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .navigationBarsPadding()
                .clickable { onClick.invoke() }
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp29)
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun OnboardingPreview() {

}