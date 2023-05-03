package com.baltroid.presentation.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R
import com.baltroid.presentation.common.RoundedIconCard
import com.baltroid.presentation.common.VerticalSpacer
import com.baltroid.presentation.components.MenuBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun ShopScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .padding(top = MaterialTheme.localDimens.dp36)
            .statusBarsPadding()
    ) {
        MenuBar(
            title = "4500 PUAN",
            iconResId = R.drawable.ic_diamond,
            modifier = Modifier.fillMaxWidth()
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp40)
        ShopScreenContent(currentBalance = 3.67f, modifier = Modifier.width(IntrinsicSize.Min))
    }
}

@Composable
fun ShopScreenContent(
    currentBalance: Float,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp26),
        modifier = modifier
    ) {
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
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp24),
        modifier = modifier
    ) {
        val horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp24)

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
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = MaterialTheme.localDimens.dp1,
                color = MaterialTheme.localColors.white_alpha05,
                shape = MaterialTheme.localShapes.roundedDp24
            )
            .clip(MaterialTheme.localShapes.roundedDp24)
            .clickable { onClick.invoke() }
            .padding(vertical = MaterialTheme.localDimens.dp12)
    )
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun ShopScreenPreview() {
    ShopScreen()
}