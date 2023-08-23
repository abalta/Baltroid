package com.baltroid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R

@Composable
fun ShopScreen(
    screenState: ShopScreenState,
    onBackClick: () -> Unit
) {
    ShopScreenContent(
        currentBalance = screenState.currentBalance,
        currentPoint = screenState.currentPoint,
        onBackClick = onBackClick
    )
}

@Composable
private fun ShopScreenContent(
    currentBalance: Float,
    currentPoint: Int,
    onBackClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .padding(top = dimensionResource(id = R.dimen.dp36))
            .statusBarsPadding()
    ) {
        MenuBar(
            title = stringResource(id = R.string.point, currentPoint),
            iconResId = R.drawable.ic_diamond,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onBackClick = onBackClick
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp40))
        DiamondsGrid()
        Text(
            text = stringResource(id = R.string.balance, currentBalance),
            style = MaterialTheme.localTextStyles.profileScreenUserInfo
        )
        BuyButton {}
    }
}

@Composable
fun DiamondsGrid(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp24)),
        modifier = modifier
    ) {
        val horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp24))

        Row(
            horizontalArrangement = horizontalArrangement
        ) {
            RoundedIconCard(text = "100", iconResId = R.drawable.ic_diamond)
            RoundedIconCard(text = "100", iconResId = R.drawable.ic_diamond)
            RoundedIconCard(text = "100", iconResId = R.drawable.ic_diamond)
        }
        Row(
            horizontalArrangement = horizontalArrangement
        ) {
            RoundedIconCard(text = "100", iconResId = R.drawable.ic_diamond)
            RoundedIconCard(text = "100", iconResId = R.drawable.ic_diamond)
            RoundedIconCard(text = "100", iconResId = R.drawable.ic_diamond)
        }
        Row(
            horizontalArrangement = horizontalArrangement
        ) {
            RoundedIconCard(text = "100", iconResId = R.drawable.ic_diamond)
            RoundedIconCard(text = "100", iconResId = R.drawable.ic_diamond)
            RoundedIconCard(text = "100", iconResId = R.drawable.ic_diamond)
        }
    }
}

@Composable
fun BuyButton(
    onClick: () -> Unit
) {
    Text(
        text = stringResource(id = R.string.buy),
        style = MaterialTheme.localTextStyles.menuBarTitle,
        color = MaterialTheme.localColors.white_alpha09,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = dimensionResource(id = R.dimen.dp1),
                color = MaterialTheme.localColors.white_alpha05,
                shape = MaterialTheme.localShapes.roundedDp24
            )
            .clip(MaterialTheme.localShapes.roundedDp24)
            .clickable { onClick.invoke() }
            .padding(vertical = dimensionResource(id = R.dimen.dp12))
    )
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun ShopScreenPreview() {
    ShopScreen(
        screenState = ShopScreenState(
            currentBalance = 20f,
            currentPoint = 4500
        )
    ) {}
}