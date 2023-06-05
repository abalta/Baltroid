package com.baltroid.core.network.api

import com.baltroid.core.datastore.PreferencesDataStoreDataSource
import com.baltroid.core.network.api.service.HitReadsService
import com.baltroid.core.network.util.retrofit
import retrofit2.create
import javax.inject.Inject

class BaltroidApi @Inject constructor(
    private val dataStoreDataSource: PreferencesDataStoreDataSource
) {
    val hitReadsService: HitReadsService by lazy { retrofit(dataStoreDataSource).create() }
}