package com.baltroid.util

fun String?.orEmpty(): String {
    return this ?: ""
}