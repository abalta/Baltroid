package com.baltroid.apps.ui.malls

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baltroid.designsystem.component.MallBigCard
import com.baltroid.designsystem.theme.hollyColor
import com.baltroid.designsystem.theme.sfProFamily


@Composable
internal fun MallListRoute(
    viewModel: MallListViewModel,
    onBack: () -> Unit
) {
    val mallListUiState by viewModel.uiState.collectAsStateWithLifecycle()
    MallListScreen(
        onBack,
        mallListUiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MallListScreen(
    onBack: () -> Unit,
    mallListUiState: MallListUiState
) {
    when (mallListUiState) {
        is MallListUiState.Success -> {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = mallListUiState.city.name,
                                style = TextStyle(
                                    fontFamily = sfProFamily,
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = MaterialTheme.colorScheme.hollyColor
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.hollyColor
                                )
                            }
                        }
                    )
                }
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier.padding(innerPadding),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    items(key = {
                        it.id
                    }, items = mallListUiState.city.malls) {
                        MallBigCard(mall = it, onMallClick = {})
                    }
                }
            }
        }

        MallListUiState.Loading -> {

        }
    }
}