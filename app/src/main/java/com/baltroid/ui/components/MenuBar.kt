package com.baltroid.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.baltroid.apps.R
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localTextStyles

@Composable
fun MenuBar(
    title: String,
    @DrawableRes iconResId: Int,
    modifier: Modifier = Modifier,
    widthFraction: Float = 0.85f,
    closeBottomDivider: Boolean = false,
    onBackClick: () -> Unit
) {

    ConstraintLayout(modifier = modifier.fillMaxWidth(widthFraction)) {

        val (banner, text, close, divider) = createRefs()
        createHorizontalChain(banner, text, close, chainStyle = ChainStyle.SpreadInside)

        SimpleIcon(
            iconResId = iconResId,
            modifier = Modifier.constrainAs(banner) {
                top.linkTo(text.top)
                bottom.linkTo(text.bottom)
            }
        )
        Text(
            text = title,
            style = MaterialTheme.localTextStyles.spaceGrotesk24Medium,
            color = MaterialTheme.localColors.white_alpha09,
            modifier = Modifier.constrainAs(text) {
                top.linkTo(parent.top)
            }
        )
        SimpleIcon(
            iconResId = R.drawable.ic_close,
            modifier = Modifier
                .constrainAs(close) {
                    top.linkTo(text.top)
                    bottom.linkTo(text.bottom)
                }
                .clickable { onBackClick.invoke() }
        )
        if (!closeBottomDivider) {
            Divider(
                thickness = dimensionResource(id = R.dimen.dp1),
                color = MaterialTheme.localColors.white_alpha06,
                modifier = Modifier.constrainAs(divider) {
                    start.linkTo(banner.start)
                    end.linkTo(close.end)
                    top.linkTo(text.bottom, margin = 20.dp)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}

@Composable
fun IconlessMenuBar(
    title: String,
    modifier: Modifier = Modifier,
    closeBottomDivider: Boolean = false,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(0.85f)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.localTextStyles.spaceGrotesk24Medium,
                color = MaterialTheme.localColors.white_alpha09,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
            SimpleIcon(
                iconResId = R.drawable.ic_close,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { onBackClick.invoke() }
            )

        }
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp20))
        Divider(
            thickness = dimensionResource(id = R.dimen.dp1),
            color = MaterialTheme.localColors.white_alpha06
        )
    }
}