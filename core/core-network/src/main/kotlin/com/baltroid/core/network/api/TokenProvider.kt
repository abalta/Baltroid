package com.baltroid.core.network.api

import javax.inject.Inject

interface TokenProvider {
    val token: String?
}

class TokenProvideImpl @Inject constructor(preferencesDataStoreDataSource: PreferencesDataStoreDataSource): TokenProvider {
    override val token: String? = preferencesDataStoreDataSource.getToken().first()
}
