package com.baltroid.apps.ui.shopsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.model.Shop
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ShopSearchViewModel @Inject constructor(): ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _shops = MutableStateFlow(listOf<Shop>())
    val shops = searchText.combine(_shops) { text, shops ->
        if (text.isBlank()) {
            shops
        } else {
            shops.filter {
                it.name.contains(text, ignoreCase = true)
            }
        }
    }.stateIn(
        scope =viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _shops.value
    )

    fun updateShops(list: List<Shop>) {
        _shops.update {
            list
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

}