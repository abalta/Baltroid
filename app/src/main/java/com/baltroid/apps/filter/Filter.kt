package com.baltroid.apps.filter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikCheckBox
import com.baltroid.designsystem.component.MekikSortButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheet(
    onDismiss: () -> Unit,
    checkLogin: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        true
    )
    val sheetStateScope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        dragHandle = null,
        sheetState = sheetState,
        containerColor = Color.White,
        onDismissRequest = {
            onDismiss()
        }) {
        FilterHeadText(
            text = stringResource(id = R.string.title_sort),
            showIcon = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, top = 36.dp)
        )
        MekikCheckBox(
            label = "Tüm Kategoriler",
            modifier = Modifier.padding(start = 42.dp, end = 42.dp, top = 32.dp)
        ) {

        }
        MekikCheckBox(
            label = "Gayrimenkul",
            modifier = Modifier.padding(start = 42.dp, end = 42.dp, top = 12.dp)
        ) {

        }
        MekikCheckBox(
            label = "Tanıtım",
            modifier = Modifier.padding(start = 42.dp, end = 42.dp, top = 12.dp)
        ) {

        }
        HorizontalDivider(modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp))
        MekikSortButton(
            false,
            "Yeniden Eskiye",
            R.drawable.sort_old_to_new,
            modifier = Modifier.padding(top = 24.dp, start = 28.dp, end = 28.dp)
        )
        MekikSortButton(
            false,
            "Eskiden Yeniye",
            R.drawable.sort_new_to_old,
            modifier = Modifier.padding(top = 12.dp, start = 28.dp, end = 28.dp)
        )
        MekikSortButton(
            false,
            "Alfabetik",
            R.drawable.sort_a_to_z,
            modifier = Modifier.padding(top = 12.dp, start = 28.dp, end = 28.dp)
        )
        MekikSortButton(
            false,
            "Popüler",
            R.drawable.sort_popular,
            modifier = Modifier.padding(top = 12.dp, start = 28.dp, end = 28.dp, bottom = 40.dp)
        )
    }
}

@Preview
@Composable
fun FilterSheetPreview() {
    FilterSheet(
        onDismiss = {},
        checkLogin = {}
    )
}