package com.baltroid.core.data.util

internal fun String.titlecase() = lowercase().replaceFirstChar { it.uppercase() }
