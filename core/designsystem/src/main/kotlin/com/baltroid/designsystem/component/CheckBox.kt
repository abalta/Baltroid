package com.baltroid.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.theme.electricVioletColor
import com.baltroid.designsystem.theme.mediumBoldStyle
import com.baltroid.designsystem.theme.regularStyle

@Composable
fun MekikCheckBox(
    label: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    var checkedState by rememberSaveable {
        mutableStateOf(checked)
    }

    Row(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = {
                checkedState = !checkedState
                onClick(checkedState)
            }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .border(BorderStroke(0.5.dp, Color.Black), RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "check",
                    tint = MaterialTheme.colorScheme.electricVioletColor
                )
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.regularStyle,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
@Preview
fun MekikCheckBoxPreview() {
    MekikCheckBox("Yeni Eğitim Eklendiğinde", false, onClick = {

    })
}

@Composable
fun MekikRadioButton(
    selected: Boolean,
    modifier: Modifier = Modifier,
    label: String,
    onClick: (Boolean) -> Unit
) {

    var checked by rememberSaveable {
        mutableStateOf(selected)
    }

    Row(
        modifier = modifier.clickable(
            onClick = {
                checked = !checked
            }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = checked, onClick = {
            checked = !checked
            onClick(checked)
        })
        Text(
            text = label,
            style = MaterialTheme.typography.regularStyle
        )
    }
}

@Composable
@Preview
fun MekikRadioButtonPreview() {
    MekikRadioButton(false, label = "Yeni Eğitim Eklendiğinde", onClick = {

    })
}

@Composable
fun MekikSortButton(
    selected: Boolean,
    label: String,
    icon: Int,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    var checked by rememberSaveable {
        mutableStateOf(selected)
    }

    Row(
        modifier = modifier.fillMaxWidth().clickable(
            onClick = {
                checked = !checked
                onClick(checked)
            }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .size(48.dp, 32.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(Color(0xFFD9D9D9)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "check",
                tint = if(selected) MaterialTheme.colorScheme.electricVioletColor else Color.Black
            )
        }
        Text(
            text = label,
            style = if(selected) MaterialTheme.typography.mediumBoldStyle else MaterialTheme.typography.regularStyle,
            modifier = Modifier.padding(start = 16.dp),
            color = if(selected) MaterialTheme.colorScheme.electricVioletColor else Color.Black
        )
    }
}

@Preview
@Composable
fun MekikSortButtonPreview() {
    MekikSortButton(false, "Yeni Eğitim Eklendiğinde", R.drawable.sort_popular, {

    })
}