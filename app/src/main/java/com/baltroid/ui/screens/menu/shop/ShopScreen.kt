package com.baltroid.ui.screens.menu.shop

import android.app.Activity
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.R
import com.baltroid.ui.common.SetLoadingState
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.screens.viewmodels.AuthenticationViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.findActivity
import com.baltroid.util.orZero
import com.hitreads.core.model.Profile
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Offerings
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import com.revenuecat.purchases.getOfferingsWith
import com.revenuecat.purchases.models.StoreProduct
import com.revenuecat.purchases.purchaseWith

@Composable
fun ShopScreen(
    viewModel: AuthenticationViewModel,
    screenState: ShopScreenState, onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var offerings by remember {
        mutableStateOf<Offerings?>(null)
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    SetLoadingState(isLoading = isLoading)
    LaunchedEffect(Unit) {
        isLoading = true
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(
            PurchasesConfiguration.Builder(
                context, "goog_kKwtmaaaOdafXasuQbnILKcfOxN"
            ).build()
        )
        Purchases.sharedInstance.getOfferingsWith(onError = { error ->
            isLoading = false
        }, onSuccess = {
            offerings = it
            isLoading = false
        })
    }
    if (!isLoading) {
        ShopScreenContent(
            profile = viewModel.profileState.collectAsStateWithLifecycle().value.profile,
            currentBalance = screenState.currentBalance,
            currentPoint = screenState.currentPoint,
            onBackClick = onBackClick,
            offerings = offerings,
            onBuyClicked = {
                purchase(context.findActivity(), it)
            }
        )
    }
}

fun purchase(activity: Activity, product: StoreProduct) {
    Purchases.sharedInstance.purchaseWith(
        PurchaseParams.Builder(activity, product).build(),
        onError = { error, userCancelled -> /* No purchase */
            println("")
        },
        onSuccess = { storeTransaction, customerInfo ->
            print("")
        }
    )
}

@Composable
private fun ShopScreenContent(
    profile: Profile?,
    offerings: Offerings?,
    currentBalance: Float,
    currentPoint: Int,
    onBackClick: () -> Unit,
    onBuyClicked: (StoreProduct) -> Unit
) {
    var selectedProduct by remember(offerings) {
        mutableStateOf(offerings?.get("kk_clubgems")?.availablePackages?.firstOrNull()?.product)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .padding(top = dimensionResource(id = R.dimen.dp36))
            .statusBarsPadding()
    ) {
        MenuBar(
            title = stringResource(id = R.string.point, profile?.gem.orZero()),
            iconResId = R.drawable.ic_diamond,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onBackClick = onBackClick
        )
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp40))
        Column(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.dp32))
        ) {
            DiamondsGrid(
                selectedProduct = selectedProduct,
                packages = offerings?.all?.get("kk_clubgems")?.availablePackages.orEmpty()
            ) {
                selectedProduct = it
            }
            VerticalSpacer(height = R.dimen.dp24)
            Text(
                text = selectedProduct?.price?.formatted.orEmpty(),
                style = MaterialTheme.localTextStyles.spaceGrotesk24Medium,
                color = MaterialTheme.localColors.white_alpha09,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.localShapes.roundedDp12)
                    .background(MaterialTheme.localColors.white_alpha07)
                    .clickable { }
                    .padding(vertical = dimensionResource(id = R.dimen.dp12))
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp24))
            BuyButton {
                selectedProduct?.let { onBuyClicked.invoke(it) }
            }
        }
    }
}

@Composable
fun DiamondsGrid(
    packages: List<Package>,
    selectedProduct: StoreProduct?,
    modifier: Modifier = Modifier,
    onItemSelected: (StoreProduct) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp24)),
    ) {
        items(packages) {
            OfferItem(
                isSelected = it.product.id == selectedProduct?.id,
                amount = it.product.description.trim().substringAfter("ClubGem")
            ) {
                onItemSelected.invoke(it.product)
            }
        }
    }
}

@Composable
fun OfferItem(
    isSelected: Boolean, amount: String, onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(MaterialTheme.localShapes.roundedDp16)
            .border(
                dimensionResource(id = if (isSelected) R.dimen.dp3 else R.dimen.dp0_5),
                if (isSelected) MaterialTheme.localColors.orange else MaterialTheme.localColors.white,
                MaterialTheme.localShapes.roundedDp16
            )
            .clickable(onClick = onClick)
            .padding(vertical = dimensionResource(id = R.dimen.dp14))
    ) {
        SimpleIcon(iconResId = R.drawable.ic_diamond)
        VerticalSpacer(height = R.dimen.dp3)
        Text(
            text = amount,
            style = MaterialTheme.localTextStyles.poppins14Medium,
            color = MaterialTheme.localColors.white
        )
    }
}

@Composable
fun BuyButton(
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
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
    ) {
        Text(
            text = stringResource(id = R.string.buy),
            style = MaterialTheme.localTextStyles.spaceGrotesk24Medium,
            color = MaterialTheme.localColors.white_alpha09,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
        SimpleIcon(
            iconResId = R.drawable.ic_shopping_cart, Modifier.padding(
                start = dimensionResource(
                    id = R.dimen.dp36
                )
            )
        )
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun ShopScreenPreview() {

}