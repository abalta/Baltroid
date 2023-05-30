package com.baltroid.core.datastore.util

import androidx.datastore.preferences.core.stringPreferencesKey

internal object Constants {
    private const val TOKEN_NAME = "token"
    internal val TOKEN_KEY = stringPreferencesKey(TOKEN_NAME)
}
