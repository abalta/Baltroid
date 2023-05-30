package com.baltroid.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.baltroid.core.datastore.util.Constants.TOKEN_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun getToken(): Flow<String> = dataStore.data.map { preferences ->
        preferences[TOKEN_KEY].orEmpty()
    }

    suspend fun setToken(token: String) = dataStore.edit { preferences ->
        preferences[TOKEN_KEY] = token
    }
}
