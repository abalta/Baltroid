package com.baltroid.apps.ext

import android.util.Patterns

fun String.isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String.isValidPassword(): Boolean = this.length >= 6