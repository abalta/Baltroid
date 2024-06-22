package com.baltroid.apps.filter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.baltroid.apps.ext.collectAsStateLifecycleAware
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.component.FilterHeadText
import com.baltroid.designsystem.component.MekikCheckBox
import com.baltroid.designsystem.component.MekikFilledButton
import com.baltroid.designsystem.component.MekikSortButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheet(
    filterViewModel: FilterViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onSubmit: (Pair<String?, Int?>) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        true
    )
    val sheetStateScope = rememberCoroutineScope()
    val uiState by filterViewModel.categoryState.collectAsStateLifecycleAware()

    var sortFilter: String? by remember { mutableStateOf(null) }
    var categoryFilter: Int? by remember { mutableStateOf(null) }

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
        uiState.categories?.let {
            val selectedOption = remember { mutableStateOf("") }
            MekikCheckBox(
                label = "Tüm Kategoriler",
                checked = selectedOption.value == "Tüm Kategoriler",
                modifier = Modifier.padding(start = 42.dp, end = 42.dp, top = 32.dp)
            ) {
                selectedOption.value = "Tüm Kategoriler"
                categoryFilter = null
            }
            it.forEach { category ->
                MekikCheckBox(
                    label = category.name,
                    checked = selectedOption.value == category.name,
                    modifier = Modifier.padding(start = 42.dp, end = 42.dp, top = 12.dp)
                ) {
                    selectedOption.value = category.name
                    categoryFilter = category.id
                }
            }
        }
        HorizontalDivider(modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp))
        uiState.sortOptions?.let {
            val selectedSortOption = remember { mutableStateOf("") }
            it.forEach { option ->
                MekikSortButton(
                    selected = selectedSortOption.value == option.name,
                    label = option.name,
                    icon = option.icon,
                    onClick = {
                        selectedSortOption.value = option.name
                        sortFilter = option.key
                    },
                    modifier = Modifier.padding(top = 24.dp, start = 28.dp, end = 28.dp)
                )
            }
        }
        MekikFilledButton(text = "Uygula", modifier = Modifier.padding(24.dp)) {
            onSubmit(Pair(sortFilter, categoryFilter))
        }
    }
}

@Preview
@Composable
fun FilterSheetPreview() {
    FilterSheet(
        onDismiss = {}, onSubmit = {}
    )
}