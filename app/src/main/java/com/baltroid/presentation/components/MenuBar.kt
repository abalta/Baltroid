package com.baltroid.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.baltroid.apps.R
import com.baltroid.presentation.common.SimpleIcon
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localTextStyles

@Composable
fun MenuBar(
    title: String,
    @DrawableRes iconResId: Int,
    modifier: Modifier = Modifier
) {
    val localDimens = MaterialTheme.localDimens

    ConstraintLayout(modifier = modifier) {

        val (banner, text, close, divider) = createRefs()
        createHorizontalChain(banner, text, close)

        SimpleIcon(
            iconResId = iconResId,
            modifier = Modifier.constrainAs(banner) {
                top.linkTo(text.top)
                bottom.linkTo(text.bottom)
            })
        Text(
            text = title,
            style = MaterialTheme.localTextStyles.menuBarTitle,
            modifier = Modifier.constrainAs(text) {
                top.linkTo(parent.top)
            }
        )
        SimpleIcon(
            iconResId = R.drawable.ic_close,
            modifier = Modifier.constrainAs(close) {
                top.linkTo(text.top)
                bottom.linkTo(text.bottom)
            })
        Divider(
            thickness = MaterialTheme.localDimens.dp0_5,
            color = MaterialTheme.localColors.white_alpha06,
            modifier = Modifier.constrainAs(divider) {
                start.linkTo(banner.start)
                end.linkTo(close.end)
                top.linkTo(text.bottom, margin = localDimens.dp20)
                width = Dimension.fillToConstraints
            }
        )
    }
}