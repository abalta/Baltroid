package com.baltroid.ui.common

import android.content.Context
import android.widget.Toast
import com.baltroid.apps.R

fun Context.showLoginToast() {
    Toast.makeText(this, getString(R.string.login_needed), Toast.LENGTH_SHORT).show()
}