package com.baltroid.util

import androidx.compose.runtime.MutableState

fun MutableState<Boolean>.reverse() {
    value = !value
}