package com.baltroid.core.data.extension

fun String.emptyToNull(): String? {
    return this.ifEmpty { null }
}