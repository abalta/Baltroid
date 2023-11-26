package com.baltroid.apps.ext

fun Int.floor(): String {
    return if (this == 0) {
        "Zemin Kat"
    } else {
        "$this. Kat"
    }
}